package com.test.demo.bean.platform.data;

import com.test.demo.DataBean.DataBeanDao.DatasBean;
import com.test.demo.DataBean.Platform.bean.Flurry;
import com.test.demo.common.httpHelper.FlurryPlatformHttpClientHelper;
import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;

@Component
@Setter
@Getter
public class NewDevicesOfTechnical extends FlurryData{
    private String name="Technical DashBoard";
    private FlurryPlatformHttpClientHelper flurryHttpClientHelper = new FlurryPlatformHttpClientHelper();
//    Flurry flurry;
//    public boolean getPlatformFromDb(){
//        Flurry flurry = flurryDao.byName(getName());
//        if(flurry!=null){
//            this.flurry = flurry;
//            return true;
//        }else{
//            return false;
//        }
//    }
//    public int getInterval(){
//        return flurry.getInterval();
//    }
//    public boolean setInterval(int interval){
//        if(interval!=getInterval()) {
//            flurry.setInterval(interval);
//            flurryDao.saveAndUpdate(flurry);
//        }
//        return true;
//    }
//
//    public boolean calcTimeForInterval(int now) {
//        if(beforeTime==0||timeHelper.calcMinDiff(now,beforeTime) ==flurry.getInterval()){
//            beforeTime = now;
//            return true;
//        }
//        return false;
//    }

    public JSONObject bodyObject(String projectID, String companyID){
        JSONObject bodyObject = new JSONObject();
        bodyObject.element("sortBy","newDevices");
        bodyObject.element("topN","10");
        bodyObject.element("metricsSource","topNBreakdown");
        bodyObject.element("table","technical");
        JSONObject localization = new JSONObject();
        localization.element("timezone","");
        localization.element("language","");
        bodyObject.element("localization",localization);
        JSONArray metrics = new JSONArray();
        metrics.element("newDevices");
        bodyObject.element("metrics",metrics);
        bodyObject.element("grain","all");
        JSONObject timeRange = new JSONObject();
        timeRange.element("end","2018-09-18T15:59:59.000Z");
        timeRange.element("start","2018-08-19T16:00:00.000Z");
        bodyObject.element("timeRange",timeRange);


        JSONArray dimensions = new JSONArray();
        dimensions.element("deviceModel");
        bodyObject.element("dimensions",dimensions);

        JSONObject filters = new JSONObject();
        JSONArray projects = new JSONArray();
        projects.element(projectID);
        filters.element("projects",projects);
        bodyObject.element("filters",filters);


        JSONArray projectsTypeMap = new JSONArray();
        projectsTypeMap.element("app");
        bodyObject.element("projectsTypeMap",projectsTypeMap);

        bodyObject.element("companyId",companyID);
        bodyObject.element("groups",new JSONArray());
//        JSONArray aa= JSONArray.fromObject("      \n" +
//                "{\n" +
//                "    \"sortBy\":\"newDevices\",\n" +
//                "    \"topN\":10,\n" +
//                "    \"metricsSource\":\"topNBreakdown\",\n" +
//                "    \"table\":\"technical\",\n" +
//                "   \n" +
//                "    \"localization\":{\n" +
//                "        \"timezone\":\"\",\n" +
//                "        \"language\":\"\"\n" +
//                "    },\n" +
//                "  \n" +
//                "    \"metrics\":[\n" +
//                "        \"newDevices\"\n" +
//                "    ],\n" +
//                "    \"grain\":\"all\",\n" +
//                "    \"timeRange\":{\n" +
//                "        \"end\":\"2018-09-18T15:59:59.000Z\",\n" +
//                "        \"start\":\"2018-08-19T16:00:00.000Z\"\n" +
//                "    },\n" +
//                "    \"dimensions\":[\n" +
//                "        \"deviceModel\"\n" +
//                "    ],\n" +
//                "    \"filters\":{\n" +
//                "        \"projects\":[\n" +
//                "            722085\n" +
//                "        ]\n" +
//                "    },\n" +
//                "    \"projectsTypeMap\":[\n" +
//                "        \"app\"\n" +
//                "    ],\n" +
//                "    \"companyId\":209825,\n" +
//                "    \"groups\":[\n" +
//                "\n" +
//                "    ]\n" +
//                "}\n" +
//                "\n");
        return bodyObject;
    }

    public JSONObject socketGate(String name) throws IOException, ParseException {
        return callDataAndSave2DataBean(name);
    }
    public JSONArray changeParams(JSONObject setting) throws IOException, ParseException {
        return dataOfChosenProject((Flurry)flurryDao.platformFindAndUpdate(getName(),setting));
    }

    public JSONObject callDataAndSave2DataBean(String name) throws IOException {
        System.out.println("callAndSaveDBName:"+name);
        Flurry flurry = flurryDao.byName(name);

        DatasBean datasBean = datasBeanDao.datasBeanByName(name);

        if(datasBean==null){
            datasBean = new DatasBean();
            datasBean.setName(name);
        }
        JSONArray returnArray = dataOfChosenProject(flurry);
//        datasBean.setDatas(returnArray);
        datasBean.setDatas(returnArray.toString());
        datasBeanDao.datasBeanSaveAndUpdate(datasBean);
        return statusHelper.jsonArray2Success(returnArray);
    }
    public JSONArray dataOfChosenProject(Flurry flurry)throws IOException {
        System.out.println("3");
        JSONArray seriesArray = new JSONArray();

        JSONObject flurryOfUserData = JSONObject.fromObject(dataOfAllProject(flurry.getPrivateToken()));
        JSONArray idsOfAllProject = flurryOfUserData.getJSONArray("apps");
        JSONArray idOfChosenProject = idOfChosenProject(idsOfAllProject,flurry.getChosenProjects());
        for(int i=0;i<idOfChosenProject.size();i++){
            JSONObject aDataOfID = idOfChosenProject.getJSONObject(i);
            JSONObject object = new JSONObject();
            JSONObject bodyObject = bodyObject(aDataOfID.getString("id"),"209825");
            String series = flurryHttpClientHelper.httpClientPostUilt(flurryHttpClientHelper.dealUrl("NEWDEVICESOFTECHNICAL",""), flurry.getPrivateToken(),bodyObject);
            JSONObject seriesObject = JSONObject.fromObject(series);
            object.element("projectName",aDataOfID.getString("name"));
            object.element("id",aDataOfID.getString("id"));
            object.element("series",seriesObject.getJSONArray("series"));
            seriesArray.element(object);
        }
        return seriesArray;
    }
//    public JSONObject callDataAndSave2DataBean(String name) throws IOException {
//        Flurry flurry = flurryDao.byName(name);
//        System.out.println("name:"+name);
//        DatasBean datasBean = datasBeanDao.datasBeanByName(name);
//
//        if(datasBean==null){
//            datasBean = new DatasBean();
//            datasBean.setName(name);
//        }
//        JSONArray returnArray = dataOfChosenProject(flurry);
//        datasBean.setDatas(returnArray.toString());
////        datasBean.setDatas(returnArray);
//        datasBeanDao.datasBeanSaveAndUpdate(datasBean);
//        return statusHelper.jsonArray2Success(returnArray);
//    }
//    public JSONArray callDataAndSave2DataBean(String name) throws IOException {
//        Flurry flurry = flurryDao.byName(name);
//        System.out.println("name:"+name);
//        DatasBean datasBean = datasBeanDao.datasBeanByName(getName());
//
//        if(datasBean==null){
//            datasBean = new DatasBean();
//            datasBean.setName(getName());
//        }
//        JSONArray returnArray = dataOfChosenProject(flurry);
//        datasBean.setDatas(returnArray);
//        datasBeanDao.datasBeanSaveAndUpdate(datasBean);
//        return returnArray;
//    }
//    synchronized public JSONArray changeParams(JSONObject setting) throws IOException {
//        Flurry flurry = flurryDao.byName(getName());
//        if(flurry==null){
//            flurry = new Flurry();
//            flurry.setName(getName());
//        }
//        JSONArray allProjects = setting.getJSONArray("allProjects");
//        JSONArray chosenProject = setting.getJSONArray("chosenProjects");
//        String privateToken = setting.getString("privateToken");
//        flurry.setAllProjects(allProjects);
//        flurry.setChosenProjects(chosenProject);
//        flurry.setPrivateToken(privateToken);
//        flurry.setInterval(setting.getInt("interval"));
//        flurryDao.saveAndUpdate(flurry);
//        return dataOfChosenProject(flurry);
//    }

//    public JSONArray majarProjectId(String flurryToken) throws IOException {
//        String data = dataOfAllProject(flurryToken);
//        JSONArray returnArray = new JSONArray();
//        JSONObject user = JSONObject.fromObject(data);
//        if(user.has("apps")){
//            returnArray = JSONArray.fromObject(user.getJSONArray("apps"));
//        }
//        return returnArray;
//    }
}

