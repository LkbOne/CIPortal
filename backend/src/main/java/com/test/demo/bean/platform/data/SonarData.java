package com.test.demo.bean.platform.data;

import com.test.demo.DataBean.DataBeanDao.DatasBean;
import com.test.demo.DataBean.DataBeanDao.DatasBeanDao;
import com.test.demo.DataBean.Platform.Interface.Platforms;
import com.test.demo.DataBean.Platform.dao.SonarDao;
import com.test.demo.DataBean.Platform.bean.Sonar;
import com.test.demo.common.httpHelper.SonarPlatformHttpClientHelper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.text.ParseException;

@Setter
@Getter
@ToString
@Component
public class SonarData extends Data {
    String name = "Sonar";
    @Autowired
    DatasBeanDao datasBeanDao;
    @Autowired
    SonarDao sonarDao;
    Sonar sonar;
    SonarPlatformHttpClientHelper sonarHttpClientHelper = new SonarPlatformHttpClientHelper();
    private final String COMPONENTS = "components";
    final private String COVERAGE = "coverage";
    final private String BUGS = "bugs";
    final private String RELIABILITY_RATING = "reliability_rating";
    final private String CODE_SMELLS = "code_smells";
    final private String DUPLICATED_LINES_DENSITY = "duplicated_lines_density";
    final private String SQALE_RATING = "sqale_rating";
    final private String SECURITY_RATING = "security_rating";
    final private String VULNERABILITIES = "vulnerabilities";
    final private String MYPROJECTNAME = "projectName";
    final private String FROMPROJECTNAME = "name";
    final private String ID = "id";
    final private String KEY = "key";
    public boolean getPlatformFromDb(){
         Sonar sonar = sonarDao.byName(getName());
         if(sonar != null){
             this.sonar = sonar;
             return true;
         }else{
             return false;
         }
    }
    public int getInterval(){
        if(sonar == null){
            return 0;
        }
        return sonar.getInterval();
    }
    public boolean setInterval(int interval){
        if(interval != getInterval()) {
            this.sonar.setInterval(interval);
            sonarDao.saveAndUpdate(this.sonar);
        }
        return true;
    }
    public boolean calcTimeForInterval(int now) {
        if(beforeTime == 0 || timeHelper.calcMinDiff(now,beforeTime) == this.sonar.getInterval()){
            this.beforeTime = now;
            return true;
        }
        return false;
    }
    public JSONArray comfirmBaseAuthorityAndGetCheckBoxData(String sonarHost) throws IOException {
        return projects(dataOfAllProject(sonarHost));
    }
    public String dataOfAllProject(String sonarHost) throws IOException {
        String data = sonarHttpClientHelper.httpClientGetUilt(sonarHttpClientHelper.dealUrl("ALLPROJECTS", sonarHost,""));
        return data;
    }
    public JSONArray projects(String data)  {
        JSONObject projects = JSONObject.fromObject(data);
        JSONArray components = projects.getJSONArray(COMPONENTS);
        JSONArray nameAndKeyArray = new JSONArray();
        for(int i = 0; i < components.size(); i++){
            JSONObject oneComponents = components.getJSONObject(i);
            JSONObject nameAndKeyObject = new JSONObject();
            nameAndKeyObject.put(MYPROJECTNAME,oneComponents.getString(FROMPROJECTNAME));
            nameAndKeyObject.put(KEY,oneComponents.getString(KEY));
            nameAndKeyObject.put(ID,oneComponents.getString(ID));
            nameAndKeyArray.element(nameAndKeyObject);
        }
        return nameAndKeyArray;
    }
    public JSONObject dealWithData(int i, String sonarHost, JSONArray dataArray, JSONArray projectArray) throws ParseException {
        JSONObject returnDatas = new JSONObject();
        returnDatas.element("projectName", projectArray.getJSONObject(i).getString(getFROMPROJECTNAME()));
        returnDatas.element("id", projectArray.getJSONObject(i).getString("id"));
        returnDatas.element("key", projectArray.getJSONObject(i).getString("key"));
        returnDatas.element("url", sonarHttpClientHelper.dealUrl("ONEPROJECT", sonarHost,projectArray.getJSONObject(i).getString("key")));
        for(int j = i; j < dataArray.size(); j += projectArray.size()){
            JSONObject dataObject = dataArray.getJSONObject(j);
            if(dataObject.getString("metric").equals("ncloc_language_distribution") || dataObject.getString("metric").equals("ncloc")){
                continue;
            }
            if(dataObject.getString("metric").equals("alert_status")){
                returnDatas.element(dataObject.getString("metric"), dataObject.getString("value"));
                continue;
            }
            returnDatas.element(dataObject.getString("metric"), dataObject.getDouble("value"));
        }

        return returnDatas;
    }
    public JSONArray changeParams(JSONObject setting) throws IOException, ParseException {
       return dataOfChosenProject(sonarDao.platformFindAndUpdate(getName(), setting));
    }
    public JSONObject callDataAndSave2DataBean(String name) throws IOException, ParseException {
        Sonar sonar = sonarDao.byName(name);
        DatasBean datasBean = datasBeanDao.datasBeanByName(getName());
        if(datasBean == null){
            datasBean = new DatasBean();
            datasBean.setName(getName());
        }
        JSONArray returnArray = dataOfChosenProject(sonar);
        datasBean.setDatas(returnArray.toString());
        datasBeanDao.datasBeanSaveAndUpdate(datasBean);
        return statusHelper.jsonArray2Success(returnArray);
    }
    public JSONObject socketGate(String name) throws IOException, ParseException {
        return callDataAndSave2DataBean(name);
    }
    public JSONArray dataOfChosenProject(Platforms platforms) throws IOException, ParseException {
        Sonar sonar = (Sonar) platforms;
        JSONArray returnArray = new JSONArray();
        JSONArray idsOfAllProject = JSONObject.fromObject(dataOfAllProject(sonar.getHost())).getJSONArray(COMPONENTS);
        JSONArray projectArray = idOfChosenProject(idsOfAllProject,sonar.getChosenProjects());
        JSONObject dataObject = JSONObject.fromObject(sonarHttpClientHelper.httpClientGetUilt(sonarHttpClientHelper.dealUrl("ALLDATA",sonar.getHost(),projectArray.toString())));
        JSONArray datas = dataObject.getJSONArray("measures");
        for(int i = 0; i < projectArray.size(); i++){
            returnArray.element(dealWithData(i, sonar.getHost(), datas,projectArray));
        }
        return returnArray;
    }
}
