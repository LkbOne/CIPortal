package com.test.demo.bean.platform.data;
import com.test.demo.DataBean.DataBeanDao.DatasBean;
import com.test.demo.DataBean.Platform.bean.Flurry;
import com.test.demo.common.httpHelper.FlurryPlatformHttpClientHelper;
import com.test.demo.common.time.TimeHelper;
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
public class NewDevicesOfRegion extends FlurryData{
    String name ="Geography DashBoard";
    TimeHelper timeHelp = new TimeHelper();
    FlurryPlatformHttpClientHelper flurryHttpClientHelper = new FlurryPlatformHttpClientHelper();
    public JSONObject bodyObject(String projectID,String companyID){
        JSONObject bodyObject = new JSONObject();
        bodyObject.element("sortBy","newDevices");
        bodyObject.element("topN","10");
        bodyObject.element("table","audience");
        JSONObject localization = new JSONObject();
        localization.element("timezone","");
        localization.element("language","");
        bodyObject.element("localization",localization);
        JSONArray metrics = new JSONArray();
        metrics.element("newDevices");
        bodyObject.element("metrics",metrics);
        bodyObject.element("grain","day");
        JSONObject timeRange = new JSONObject();
        timeRange.element("end","2018-09-18T15:59:59.000Z");
        timeRange.element("start","2018-08-19T16:00:00.000Z");
        bodyObject.element("timeRange",timeRange);
        JSONArray dimensions = new JSONArray();
        dimensions.element("region");
        dimensions.element("app");
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
//        JSONObject object = JSONObject.fromObject("{\n" +
//                "    \"sortBy\":\"newDevices\",\n" +
//                "    \"topN\":10,\n" +
//                "\n" +
//                "    \"table\":\"audience\",\n" +
//                "    \"localization\":{\n" +
//                "        \"timezone\":\"\",\n" +
//                "        \"language\":\"\"\n" +
//                "    },\n" +
//                " \n" +
//                "    \"metrics\":[\n" +
//                "        \"newDevices\"\n" +
//                "    ],\n" +
//                "    \"grain\":\"day\",\n" +
//                "    \"timeRange\":{\n" +
//                "        \"end\":\"2018-09-18T15:59:59.000Z\",\n" +
//                "        \"start\":\"2018-08-19T16:00:00.000Z\"\n" +
//                "    },\n" +
//                "    \"dimensions\":[\n" +
//                "        \"region\",\n" +
//                "        \"app\"\n" +
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
//                "}");
        return bodyObject;
    }
    public JSONObject socketGate(String name) throws IOException, ParseException {
        return callDataAndSave2DataBean(name);
    }
    //需要测试，还没有做测试
    public JSONArray changeParams(JSONObject setting) throws IOException, ParseException {
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
        System.out.println("2");
        JSONArray seriesArray = new JSONArray();
        //这几行代码可以重载 idOfChosenProject 函数
        JSONObject flurryOfUserData = JSONObject.fromObject(dataOfAllProject(flurry.getPrivateToken()));
        JSONArray apps = flurryOfUserData.getJSONArray("apps");
        JSONArray idOfChosenProject = idOfChosenProject(apps,flurry.getChosenProjects());
        //
        for(int i=0;i<idOfChosenProject.size();i++){
            JSONObject aDataOfID = idOfChosenProject.getJSONObject(i);
            JSONObject object = new JSONObject();
            JSONObject bodyObject = bodyObject(aDataOfID.getString("id"),"209825");
            String series = flurryHttpClientHelper.httpClientPostUilt(flurryHttpClientHelper.dealUrl("NEWDEVICESOFREGION",""), flurry.getPrivateToken(),bodyObject);
            JSONObject seriesObject = JSONObject.fromObject(series);
            object.element("projectName",aDataOfID.getString("name"));
            object.element("id",aDataOfID.getString("id"));
            object.element("series",dealWithData(seriesObject.getJSONArray("series")));
            seriesArray.element(object);
        }
        return seriesArray;
    }

    public JSONArray dealWithData(JSONArray aPeriod){
        JSONArray returnArray = new JSONArray();
        for(int i=0;i<aPeriod.size();i++){
            JSONObject onePeriods = new JSONObject();
            JSONObject aData = aPeriod.getJSONObject(i);
            JSONArray date = new JSONArray();
            JSONArray deviceNumber = new JSONArray();
            JSONArray data=aData.getJSONArray("data");
            for(int j=0;j<data.size();j++){
                JSONArray aDay =data.getJSONArray(j);
                String dateString = TimeHelper.timeStamp2Date(aDay.getString(0),"yyyy-MM-dd");
                String month = dateString.substring(5,7);
                String day = dateString.substring(dateString.length()-2,dateString.length());
                date.element(TimeHelper.month2AbbreviateEnglish(month)+" "+day);
                deviceNumber.element(aDay.getString(1));
            }
            onePeriods.element("date",date);
            onePeriods.element("deviceNumber",deviceNumber);
            JSONObject desc = aData.getJSONObject("desc");
            String appLabel =desc.getString("app|label");
            desc.remove("app|label");
            desc.element("appLabel",appLabel);
            String region = desc.getString("region|label");
            desc.remove("region|label");
            desc.element("regionLabel",region);
            onePeriods.element("desc",aData.getJSONObject("desc"));
            returnArray.element(onePeriods);
        }
        return returnArray;
    }
}
