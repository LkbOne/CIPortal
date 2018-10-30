package com.test.demo.bean.platform.data;

import com.test.demo.DataBean.DataBeanDao.DatasBeanDao;
import com.test.demo.DataBean.Platform.Interface.PlatformDao;
import com.test.demo.DataBean.Platform.Interface.Platforms;
import com.test.demo.common.httpHelper.StatusHelper;
import com.test.demo.common.time.TimeHelper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

@Setter
@Getter
@ToString
@Component
@Service
public abstract class Data{
    int beforeTime;
    StatusHelper statusHelper = new StatusHelper();
    final private String ID="id";
    final private String MYPROJECTNAME="projectName";
    final private String FROMPROJECTNAME="name";
    protected TimeHelper timeHelper = new TimeHelper();
    private Logger logger = Logger.getLogger(Data.class);
    String name=null;
    DatasBeanDao datasBeanDao = null;
    PlatformDao platformDao = null;
    Platforms platforms=null;
    abstract public boolean getPlatformFromDb();
    abstract public int getInterval();
    abstract public boolean setInterval(int interval);
    abstract public boolean calcTimeForInterval(int now);

    abstract public JSONArray comfirmBaseAuthorityAndGetCheckBoxData(String token) throws IOException;
    public JSONArray projects(String data) {
        JSONArray projects = JSONArray.fromObject(data);
        JSONArray nameAndIdArray = new JSONArray();
        for(int i=0;i<projects.size();i++){
            JSONObject project = projects.getJSONObject(i);
            JSONObject nameAndIdObject = new JSONObject();
            nameAndIdObject.element(getID(),project.getString(getID()));
            nameAndIdObject.element(getMYPROJECTNAME(),project.getString(getFROMPROJECTNAME()));
            nameAndIdArray.element(nameAndIdObject);
        }
        return nameAndIdArray;
    }
    public abstract String dataOfAllProject(String code) throws IOException;
    public abstract JSONArray changeParams(JSONObject setting) throws IOException, ParseException;
    public abstract JSONObject callDataAndSave2DataBean(String name) throws IOException, ParseException;
    public abstract  JSONObject socketGate(String name) throws IOException, ParseException;
//    public abstract  JSONObject Gate();  //通过http请求的 不知道叫什么Gate
    public abstract JSONArray dataOfChosenProject(Platforms platforms) throws IOException, ParseException;

    public HashMap<String,String> dealWithChosen(int choice, HashMap<String,String> chosenMap, int choiceNumber, String projectName, int boundary, int boundaryEnd){
        for(int i=boundary;i<boundaryEnd;i++){
            if((choiceNumber&(1<<i))!=0){
                if(choice==1) {
                    MergeRequestData mergeRequestData= new MergeRequestData();
                    chosenMap = mergeRequestData.choiceField(i, chosenMap, projectName);
                }
                if(choice ==0){
                    CIData ciData = new CIData();
                    chosenMap = ciData.choiceField(i, chosenMap, projectName);
                }
                if(choice ==2){
                    SentryData sentryData = new SentryData();
                    chosenMap = sentryData.choiceField(i, chosenMap, projectName);
                }
            }
        }
        return chosenMap;
    }
    public JSONArray idOfChosenProject(JSONArray dataOfAllProjects,JSONArray chosenProjectID){
        JSONArray idsAndDatas = new JSONArray();
        for(int i=0;i<chosenProjectID.size();i++){
            for(int j=0;j<dataOfAllProjects.size();j++){
                JSONObject object = dataOfAllProjects.getJSONObject(j);
                if(object.getString("id").equals(chosenProjectID.getString(i))){
                    idsAndDatas.element(object);
                }
            }
        }
        return idsAndDatas;
    }
    private HashMap<String,String> choiceField(int i, HashMap<String,String> chosenMap, String projectName) {
        return null;
    }

    public HashMap<String,String> dealWithChosenField(int choice,int choiceNumber,String projectName,int boundary,int boundaryEnd){
        HashMap<String,String> chosenMap = new HashMap<>();
        dealWithChosen(choice,chosenMap,choiceNumber,projectName,boundary,boundaryEnd);
        return chosenMap;
    }
    public HashMap<String,String>dealWithChosenFunction(int choice,int chosenNumber,String projectName,int boundary,int boundaryEnd){
        HashMap<String,String> chosenMap = new HashMap<>();
        dealWithChosen(choice,chosenMap,chosenNumber,projectName,boundary,boundaryEnd);
        return chosenMap;
    }


}
