package com.test.demo.bean.platform.data;

import com.test.demo.DataBean.DataBeanDao.DatasBean;
import com.test.demo.DataBean.Platform.Interface.Platforms;
import com.test.demo.DataBean.Platform.bean.Flurry;
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
public class EventLogData extends FlurryData {
    private String name = "Event Logs";
    public JSONArray changeParams(JSONObject setting) throws IOException {
        return dataOfChosenProject((Flurry)flurryDao.platformFindAndUpdate(getName(),setting));
    }
    public JSONObject socketGate(String name) throws IOException, ParseException {
        return callDataAndSave2DataBean(name);
    }
    public JSONObject callDataAndSave2DataBean(String name) throws IOException {
        Flurry flurry = flurryDao.byName(name);
        DatasBean datasBean = datasBeanDao.datasBeanByName(name);
        if(datasBean == null){
            datasBean = new DatasBean();
            datasBean.setName(name);
        }
        JSONArray returnArray = dataOfChosenProject(flurry);
        datasBean.setDatas(returnArray.toString());
        datasBeanDao.datasBeanSaveAndUpdate(datasBean);
        return statusHelper.jsonArray2Success(returnArray);
    }
    public  JSONArray dataOfChosenProject(Platforms platforms) throws IOException {
        Flurry flurry = (Flurry) platforms;
        JSONArray returnArray = new JSONArray();
        JSONObject flurryOfUserData = JSONObject.fromObject(dataOfAllProject(flurry.getPrivateToken()));
        JSONArray apps = flurryOfUserData.getJSONArray("apps");
        JSONArray idOfChosenProject = idOfChosenProject(apps,flurry.getChosenProjects());
        for(int i=0;i<idOfChosenProject.size();i++){
            JSONObject aDataOfID = idOfChosenProject.getJSONObject(i);
            JSONObject object = new JSONObject();
            String projectName = aDataOfID.getString("name");
            JSONArray aProjectData = JSONArray.fromObject(flurryHttpClientHelper.httpClientGetUilt(flurryHttpClientHelper.dealUrl("EVENTLOG",aDataOfID.getString("id")), flurry.getPrivateToken()));
            object.element("projectName",projectName);
            object.element("devices",dealWithData(aProjectData));
            returnArray.element(object);
        }
        return returnArray;
    }
    public JSONArray dealWithData(JSONArray aProjectData){
        JSONArray returnArray = new JSONArray();
        for (int i = 0; i < aProjectData.size(); i++){
            JSONObject object = new JSONObject();
            JSONObject aDevice = aProjectData.getJSONObject(i);
            object.element("deviceModel",aDevice.getString("deviceModel"));
            object.element("countryISO",aDevice.getString("countryISO"));
//            object.element("appVersion",changeDot2Line(aDevice.getString("appVersion")));
            object.element("appVersion",aDevice.getString("appVersion"));
            object.element("events",dealDofOfEvent(aDevice.getJSONArray("events")));
            object.element("eventsLength",aDevice.getJSONArray("events").size());
            object.element("time",timeHelper.timeStamp2Date(aDevice.getString("sessionTimestamp"),"yyyy-MM-dd HH:mm"));
            returnArray.element(object);
        }
        return returnArray;
    }
    public JSONArray dealDofOfEvent(JSONArray events){
        JSONArray returnArray = new JSONArray();
        for(int i = 0; i < events.size(); i++){
            JSONObject aEvent = events.getJSONObject(i);
            JSONObject returnObject = new JSONObject();
            returnObject.element("name",aEvent.getString("name"));
            returnObject.element("id",aEvent.getString("id"));
            returnObject.element("parameters",aEvent.getJSONObject("parameters"));
            returnArray.element(returnObject);
        }
        return returnArray;
    }
}
