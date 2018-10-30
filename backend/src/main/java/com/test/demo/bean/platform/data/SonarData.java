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
    String name ="Sonar";
    @Autowired
    DatasBeanDao datasBeanDao;
    @Autowired
    SonarDao sonarDao;
    Sonar sonar;
    SonarPlatformHttpClientHelper sonarHttpClientHelper = new SonarPlatformHttpClientHelper();
    private final String COMPONENTS ="components";
    final private String COVERAGE="coverage";
    final private String BUGS="bugs";
    final private String RELIABILITY_RATING="reliability_rating";
    final private String CODE_SMELLS="code_smells";
    final private String DUPLICATED_LINES_DENSITY="duplicated_lines_density";
    final private String SQALE_RATING="sqale_rating";
    final private String SECURITY_RATING="security_rating";
    final private String VULNERABILITIES="vulnerabilities";
    final private String MYPROJECTNAME="projectName";
    final private String FROMPROJECTNAME="name";
    final private String ID="id";
    final private String KEY="key";
    public boolean getPlatformFromDb(){
         Sonar sonar = sonarDao.byName(getName());
         if(sonar!=null){
             this.sonar = sonar;
             return true;
         }else{
             return false;
         }
    }
    public int getInterval(){
        if(sonar==null){
            return 0;
        }
        return sonar.getInterval();
    }
    public boolean setInterval(int interval){
        if(interval!=getInterval()) {
            this.sonar.setInterval(interval);
            sonarDao.saveAndUpdate(this.sonar);
        }
        return true;
    }
    public boolean calcTimeForInterval(int now) {
        if(beforeTime==0||timeHelper.calcMinDiff(now,beforeTime) ==this.sonar.getInterval()){

            this.beforeTime = now;
            return true;
        }
        return false;
    }

    public JSONArray comfirmBaseAuthorityAndGetCheckBoxData(String sonarHost) throws IOException {
        return projects(dataOfAllProject(sonarHost));
    }


    public String dataOfAllProject(String sonarHost) throws IOException {
        String data = sonarHttpClientHelper.httpClientGetUilt(sonarHttpClientHelper.dealUrl("ALLPROJECTS",sonarHost,""));
        return data;
    }
    public JSONArray projects(String data)  {
        JSONObject projects = JSONObject.fromObject(data);
        JSONArray components = projects.getJSONArray(COMPONENTS);
        JSONArray nameAndKeyArray = new JSONArray();
        for(int i=0;i<components.size();i++){
            JSONObject oneComponents = components.getJSONObject(i);
            JSONObject nameAndKeyObject = new JSONObject();
            nameAndKeyObject.put(MYPROJECTNAME,oneComponents.getString(FROMPROJECTNAME));
            nameAndKeyObject.put(KEY,oneComponents.getString(KEY));
            nameAndKeyObject.put(ID,oneComponents.getString(ID));
            nameAndKeyArray.element(nameAndKeyObject);
        }
        return nameAndKeyArray;
    }
    public JSONObject dealWithData(int i,String sonarHost,JSONArray dataArray,JSONArray projectArray) throws ParseException {
        JSONObject returnDatas = new JSONObject();
//        returnDatas.element("projectName",projectArray.getJSONObject(i).getString("projectName"));
        returnDatas.element("projectName",projectArray.getJSONObject(i).getString(getFROMPROJECTNAME()));
        returnDatas.element("id",projectArray.getJSONObject(i).getString("id"));
        returnDatas.element("key",projectArray.getJSONObject(i).getString("key"));
        returnDatas.element("url", sonarHttpClientHelper.dealUrl("ONEPROJECT",sonarHost,projectArray.getJSONObject(i).getString("key")));
        for(int j=i;j<dataArray.size();j+=projectArray.size()){
            JSONObject dataObject = dataArray.getJSONObject(j);
            if(dataObject.getString("metric").equals("ncloc_language_distribution")||dataObject.getString("metric").equals("ncloc")){
                continue;
            }
            if(dataObject.getString("metric").equals("alert_status")){
                returnDatas.element(dataObject.getString("metric"),dataObject.getString("value"));
                continue;
            }
            returnDatas.element(dataObject.getString("metric"),dataObject.getDouble("value"));
        }

        return returnDatas;
    }
    //需要测试
    public JSONArray changeParams(JSONObject setting) throws IOException, ParseException {
//        Sonar sonar2 = sonarDao.byName(setting.getString("name"));
//        if(sonar2==null){
//            sonar2 = new Sonar();
//            sonar2.setName(getName());
//        }
//        JSONArray allProjects = setting.getJSONArray("allProjects");
//        JSONArray chosenProject = setting.getJSONArray("chosenProjects");
//        String host = setting.getString("host");
//        sonar2.setAllProjects(allProjects);
//        sonar2.setChosenProjects(chosenProject);
//        sonar2.setHost(host);
//        sonar2.setInterval(setting.getInt("interval"));
//        sonar2 = sonarDao.saveAndUpdate(sonar2);
        return dataOfChosenProject(sonarDao.platformFindAndUpdate(getName(),setting));
    }
    public JSONObject callDataAndSave2DataBean(String name) throws IOException, ParseException {
        Sonar sonar = sonarDao.byName(name);
        DatasBean datasBean = datasBeanDao.datasBeanByName(getName());
        if(datasBean==null){
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
        JSONArray projectArray=idOfChosenProject(idsOfAllProject,sonar.getChosenProjects());
//        JSONArray projectArray = dealChosenProjectId(chosenProjects,allProjects);
        JSONObject dataObject = JSONObject.fromObject(sonarHttpClientHelper.httpClientGetUilt(sonarHttpClientHelper.dealUrl("ALLDATA",sonar.getHost(),projectArray.toString())));
        JSONArray datas=  dataObject.getJSONArray("measures");
        for(int i=0;i<projectArray.size();i++){
            returnArray.element(dealWithData(i,sonar.getHost(),datas,projectArray));
        }
        return returnArray;
    }
    //这里的代码是处理，trello一对多sonar的
//    public boolean comfirmIsOne(int number,int count){
//        return (number&(1<<count))>0;
//    }

//    public int calcBinary(int size){
//        int number =0;
//        for(int i=0;i<size;i++){
//            number+=(1<<i);
//        }
//        return number;
//    }

//        public JSONArray connectDataByMapping(Sonar sonar,JSONArray trellos,JSONArray sonars){
//        JSONArray sonarTrelloMapping = sonar.getSonarTrelloMapping();
//        for(int i=0;i<sonarTrelloMapping.size();i++){
//            for(int j=0;j<trellos.size();j++){
//                if(sonarTrelloMapping.getJSONObject(i).getString("trello").equals(trellos.getJSONObject(j).getString("id"))){
//                    trellos.element(j,calcSonarsAvargByMapping(user,trellos.getJSONObject(j),sonars,sonarTrelloMapping.getJSONObject(i)));
//                    break;
//                }else if(!trellos.getJSONObject(j).containsKey("sonarData")){
//                    trellos.element(j,trellos.getJSONObject(j).element("sonarData",new JSONArray()));
//                }
//            }
//        }
//        return trellos;
//    }
//    public JSONObject calcSonarsAvargByMapping(User user, JSONObject trello, JSONArray sonars, JSONObject mapping){
//        JSONArray sonarMappings  =mapping.getJSONArray("sonar");
//        if(user.getChoiceSonarShow2Trello().isEmpty()||!user.getChoiceSonarShow2Trello().containsKey(trello.getString("id"))){
//            user.getChoiceSonarShow2Trello().element(trello.getString("id"), calcBinary(sonarMappings.size()));
//        }
//        int binaryStateNum = user.getChoiceSonarShow2Trello().getInt(trello.getString("id"));
//        int totalCount = 0;
//        double realAvargCount = 0;
//        JSONObject now = new JSONObject();
//        for(int k=0;k<sonarMappings.size();k++){
//            for(int m=0;m<sonars.size();m++){
//                if(sonarMappings.getJSONObject(k).getString("id").equals(sonars.getJSONObject(m).getString("id"))){
//                    if(realAvargCount==0&&comfirmIsOne(binaryStateNum,totalCount)){
//                        realAvargCount++;
//                        now = sonars.getJSONObject(m);
//                    }
//                    if(realAvargCount!=0&&comfirmIsOne(binaryStateNum,totalCount)){
//                        realAvargCount++;
//                        now = calcSonarsTotalDetail(now,sonars.getJSONObject(m));
//                    }
//                    totalCount++;
//                }
//            }
//        }
//        trello.element("trelloAndSonarMappingNum",binaryStateNum);
//        return trello.element("sonarData",calcSonarsAvargDetail(now,realAvargCount));
//    }
//    public JSONObject calcSonarsTotalDetail(JSONObject now,JSONObject before){
//        JSONObject returnObject = new JSONObject();
//        returnObject.element("coverage",now.getDouble("coverage")+before.getDouble("coverage"));
//        returnObject.element("bugs",now.getDouble("bugs")+before.getDouble("bugs"));
//        returnObject.element("reliability_rating",now.getDouble("reliability_rating")+before.getDouble("reliability_rating"));
//        returnObject.element("code_smells",now.getDouble("code_smells")+before.getDouble("code_smells"));
//        returnObject.element("duplicated_lines_density",now.getDouble("duplicated_lines_density")+before.getDouble("duplicated_lines_density"));
//        returnObject.element("sqale_rating",now.getDouble("sqale_rating")+before.getDouble("sqale_rating"));
//        returnObject.element("security_rating",now.getDouble("security_rating")+before.getDouble("security_rating"));
//        returnObject.element("vulnerabilities",now.getDouble("vulnerabilities")+before.getDouble("vulnerabilities"));
//        return returnObject;
//    }
//    public JSONObject calcSonarsAvargDetail(JSONObject total,double count){
//        if(!total.isEmpty()){
//            total.element("coverage",(int)(total.getDouble("coverage")/count));
//            total.element("bugs",dealPointNumber(total.getDouble("bugs")/count));
//            total.element("reliability_rating",dealPointNumber(total.getDouble("reliability_rating")/count));
//            total.element("code_smells",dealPointNumber(total.getDouble("code_smells")/count));
//            total.element("duplicated_lines_density",(int)(total.getDouble("duplicated_lines_density")/count));
//            total.element("sqale_rating",dealPointNumber(total.getDouble("sqale_rating")/count));
//            total.element("security_rating",dealPointNumber(total.getDouble("security_rating")/count));
//            total.element("vulnerabilities",dealPointNumber(total.getDouble("vulnerabilities")/count));
//        }
//        return total;
//    }
//    public String dealPointNumber(double number){
//        if(number<1000){
//            return String.valueOf((int) number);
//        }
//        DecimalFormat df = new DecimalFormat("#.0");
//        return df.format(number/1000)+"K";
//    }
//
//
//
//    public JSONArray dealChosenProjectId(JSONArray projectIdAndName, JSONArray allProjectIdAndName){
//        JSONArray nameAndKey = new JSONArray();
//        for(int i=0;i<allProjectIdAndName.size();i++){
//            JSONObject oneProject = allProjectIdAndName.getJSONObject(i);
//            for(int j=0;j<projectIdAndName.size();j++){
//                if(projectIdAndName.getString(j).equals(oneProject.getString("id"))){
//                    JSONObject one = new JSONObject();
//                    one.element("id",oneProject.getString("id"));
//                    one.element("key",oneProject.getString("key"));
//                    one.element("projectName",oneProject.getString("projectName"));
//                    nameAndKey.element(one);
//
//                }
//            }
//        }
//        return nameAndKey;
//    }
}
