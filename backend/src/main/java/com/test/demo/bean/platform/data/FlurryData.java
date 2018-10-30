package com.test.demo.bean.platform.data;

import com.test.demo.DataBean.DataBeanDao.DatasBeanDao;
import com.test.demo.DataBean.Platform.Interface.Platforms;
import com.test.demo.DataBean.Platform.dao.FlurryDao;
import com.test.demo.DataBean.Platform.bean.Flurry;
import com.test.demo.common.httpHelper.FlurryPlatformHttpClientHelper;
import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;

@Component
@Getter
@Setter
public class FlurryData extends Data{
    private String name ="flurry";
    @Autowired
    DatasBeanDao datasBeanDao;
    @Autowired
    FlurryDao flurryDao;
    Flurry flurry;
    FlurryPlatformHttpClientHelper flurryHttpClientHelper = new FlurryPlatformHttpClientHelper();
    public boolean getPlatformFromDb(){
        Flurry flurry = flurryDao.byName(getName());
        if(flurry!=null){
            this.flurry = flurry;
            return true;
        }else{
            return false;
        }
    }
    public int getInterval(){
        if(flurry==null){
            return 0;
        }
        return flurry.getInterval();
    }
    public boolean setInterval(int interval){
        if(interval!=getInterval()) {
            flurry.setInterval(interval);
            flurryDao.saveAndUpdate(flurry);
        }
        return true;
    }
    public boolean calcTimeForInterval(int now) {
        if(beforeTime==0||timeHelper.calcMinDiff(now,beforeTime) ==flurry.getInterval()){
            beforeTime = now;
            return true;
        }
        return false;
    }


    public JSONArray comfirmBaseAuthorityAndGetCheckBoxData(String flurryToken) throws IOException {
        return projects(dataOfAllProject(flurryToken));
    }

    public String dataOfAllProject(String flurryToken) throws IOException {
        return flurryHttpClientHelper.httpClientGetUilt(flurryHttpClientHelper.dealUrl("USER",""), flurryToken);
    }

    @Override
    public JSONArray changeParams(JSONObject setting) throws IOException, ParseException {
        return null;
    }

    @Override
    public JSONObject callDataAndSave2DataBean(String name) throws IOException, ParseException {
        return null;
    }

    @Override
    public JSONObject socketGate(String name) throws IOException, ParseException {
        return new JSONObject();
    }


    public JSONArray dataOfChosenProject(Platforms platforms) throws IOException,ParseException {
        return new JSONArray();
    }
    public JSONArray projects(String data) {
        JSONArray returnArray = new JSONArray();
        JSONObject user = JSONObject.fromObject(data);
        if(user.has("apps")){
            JSONArray projects = JSONArray.fromObject(user.getJSONArray("apps"));
            for(int i=0;i<projects.size();i++){
                JSONObject project = projects.getJSONObject(i);
                JSONObject nameAndIdObject = new JSONObject();
                nameAndIdObject.element(getID(),project.getString(getID()));
                nameAndIdObject.element(getMYPROJECTNAME(),project.getString(getFROMPROJECTNAME()));
                String platform= project.getString("platformName");
                returnArray = dealPlatformData(nameAndIdObject,platform,returnArray);
            }
        }
        return returnArray;
    }

    public JSONArray dealPlatformData(JSONObject nowData,String platformName,JSONArray returnArray){
        for(int i=0;i<returnArray.size();i++){
            JSONObject object = returnArray.getJSONObject(i);
            if(object.getString("platform").equals(platformName)){
                object.getJSONArray("apps").element(nowData);
                returnArray.element(i,object);
                return returnArray;
            }
        }
        JSONObject objectTemp = new JSONObject();
        objectTemp.element("platform",platformName);
        objectTemp.element("apps",new JSONArray());
        objectTemp.getJSONArray("apps").element(nowData);
        returnArray.element(objectTemp);
        return returnArray;
    }

}
