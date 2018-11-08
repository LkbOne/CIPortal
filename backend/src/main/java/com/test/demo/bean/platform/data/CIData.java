package com.test.demo.bean.platform.data;


import com.test.demo.DataBean.DataBeanDao.DatasBean;
import com.test.demo.DataBean.DataBeanDao.DatasBeanDao;
import com.test.demo.DataBean.Platform.dao.GitLabDao;
import com.test.demo.DataBean.Platform.bean.GitLab;
import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Getter
@Component
@Setter
@Service
public class CIData extends GitData {
    String name = "CI Testing";
    @Autowired
    DatasBeanDao datasBeanDao;
    @Autowired
    GitLabDao gitLabDao;
    GitLab gitLab;
    private Logger logger = Logger.getLogger(CIData.class);
    private  int FIELDLENGTH =100;

    private final String PIPELINEID= "pipelineId";
    private final String STATUS = "status";
    private final String FAILED= "failed";
    private final String SUCCESS= "success";
    private final String RUNNING= "running";
    private final String BUILDING= "building";
    private final String MYBRANCH= "branch";
    private final String FROMBRANCH= "ref";
    private final String FROMMESSAGEBRACH ="default_branch";
    private final String EVENTTYPE= "eventType";
    private final String OTHER ="other";
    private final String PROJECT ="project";
    private final String PIPELINE ="pipeline";
    private final String PROJECTID ="projectId";
    private final String BJTIMEUTC = "beijingUTC";

    synchronized public JSONArray changeParams(JSONObject setting) throws IOException, ParseException {
        GitLab ciData =(GitLab)gitLabDao.platformFindAndUpdate(getName(),setting);
        JSONArray newHooks= dataOfChosenProject2Hook(ciData);
        JSONArray projectImages= allProjectImage(ciData);
        ciData.setImageInSetting(projectImages);
        ciData = gitLabDao.saveAndUpdate(ciData);

        if(newHooks.size()>ciData.getHooksInSetting().size()){
            ciData.setHooksInSetting(newHooks);
            ciData = gitLabDao.saveAndUpdate(ciData);
        }
        return dataOfChosenProject(ciData);
    }
    public JSONObject socketGate(String name) throws IOException, ParseException {
        return callDataAndSave2DataBean(name);
    }
    synchronized public JSONObject callDataAndSave2DataBean(String name) throws IOException, ParseException {
        GitLab gitLab =gitLabDao.byName(name);
        DatasBean datasBean = datasBeanDao.datasBeanByName(name);
        if(datasBean==null){
            datasBean = new DatasBean();
            datasBean.setName(getName());
        }
        JSONArray returnArray = dataOfChosenProject(gitLab);
//        datasBean.setDatas(returnArray);
        datasBean.setDatas(returnArray.toString());
        datasBeanDao.datasBeanSaveAndUpdate(datasBean);
        return statusHelper.jsonArray2Success(returnArray);
    }
    public JSONArray dataOfChosenProject(GitLab gitLab) throws IOException, ParseException {
        JSONArray idsOfAllProject = JSONArray.fromObject(dataOfAllProject(gitLab.getIp(),gitLab.getPrivateToken()));
        JSONArray idArrayJson = idOfChosenProject(idsOfAllProject,gitLab.getChosenProjects());
        JSONArray dataArray = new JSONArray();
        for (int i = 0; i < idArrayJson.size(); i++) {
            JSONObject aData = idArrayJson.getJSONObject(i);
            dataArray=dealWithData(aData,dataArray,20,gitLab.getIp(),gitLab.getPrivateToken(),164825,aData.getString("id"));
        }
        return sortAndRemoveData(dataArray);
    }
//       allDataAndOperation.element(getPIPELINEDATA(),ciData.dataOfProjects(datas,projectIds,gitIp,showNumber,gitPrivateToken,ciData,164825));
    public JSONArray dealWithData(JSONObject aProject,JSONArray returnDatas,int showNumber,String gitIp,String gitPrivateToken,int chosenNumber,String projectId) throws IOException, ParseException {
        JSONArray datas =JSONArray.fromObject(gitlabHttpClientHelper.httpClientGetUilt(gitlabHttpClientHelper.dealPipelinesUrl("ALLPIPELINE", projectId, gitIp, "", ""), gitPrivateToken));
        if (datas.size()!=0){
            JSONArray pipelines =JSONArray.fromObject(datas);
            for(int i=0;i<pipelines.size()&&i<showNumber;i++){
                JSONObject aPipelineData = JSONObject.fromObject(gitlabHttpClientHelper.httpClientGetUilt(gitlabHttpClientHelper.dealPipelinesUrl("ONEPIPELINE", projectId, gitIp, pipelines.getJSONObject(i).getString(getID()), ""), gitPrivateToken));
                returnDatas.element(dealWithData(aPipelineData,aProject.getString(getFROMPROJECTNAME()),chosenNumber,gitIp,projectId));
            }

        }
        return returnDatas;
    }
    public HashMap<String,String> choiceField(int choice,HashMap<String,String> chosenMap,String projectName){

        switch (choice){
            case 0:
                chosenMap.put(getPIPELINEID(),getID());  //1 1
                break;
            case 1:
                chosenMap.put(getMYBRANCH(),getFROMMESSAGEBRACH());//0 1
                break;
            case 2:
                chosenMap.put(getTIME(),getFROMCREATEDAT());//0 1
                break;
            case 3:
                chosenMap.put(getTIME(),getFROMUPDATEDAT());//1 0
                break;
            case 4:
                chosenMap.put(getMYBRANCH(),getFROMBRANCH());//1 0
                break;
            case 5:
                chosenMap.put(getPROJECTID(),getID());//0 1
                break;
            case 6:
                chosenMap.put(getMYURL(), getMYURL());//1 0
                break;
            case 7:
                chosenMap.put(getMYAUTHORIMAGE(),getFROMAUTHORIMAGE());//1 1
                break;
            case 8:
                chosenMap.put(getMYAUTHORNAME(),getFROMAUTHORNAME());//1 1
                break;
            case 9:
                chosenMap.put(getMYPROJECTNAME(),projectName);//1 1
                break;
            case 10:
                chosenMap.put(getMYURL(), getFROMMURL());//1 1
                break;
            case 11:

                break;
            case 12:

                break;
            case 13:

                break;
            case 14:
                chosenMap.put(getTIME(),getBJTIMEUTC());//1 1
                break;
            case 15:
                chosenMap.put(getTIME(),getBJTIME());//1 0
            break;
            case 16:
                chosenMap.put(getTIME(),getSTANDARDTIME());//0 0
            break;
            case 17:
                chosenMap.put(getSORT(),getSORT());//1 0
            break;
            case 18:
            break;
            case 19:
            break;
            case 20:
            break;
        }
        return chosenMap;
    }
    public JSONObject dealWithFieldData(JSONObject aData,HashMap<String,String> chosenFieldMap,String gitIp,String projectId,String projectName){
        JSONObject returnObject = new JSONObject();
        Iterator it = chosenFieldMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if(entry.getKey().toString().equals(getMYPROJECTNAME())){
                returnObject.element(entry.getKey().toString(),entry.getValue().toString());
                continue;
            }
            if(entry.getKey().toString().equals(getMYURL())){
                returnObject.element(entry.getKey().toString(), gitlabHttpClientHelper.dealPipelinesUrl("ONEPIPELINEURL", projectId, gitIp, aData.getString(getID()), projectName));
                continue;
            }
            if(entry.getKey().toString().equals(getMYAUTHORIMAGE())||entry.getKey().toString().equals(getMYAUTHORNAME())){
                JSONObject authorData = aData.getJSONObject(getUSER());
                returnObject.element(entry.getKey().toString(),authorData.getString(entry.getValue().toString()));
                continue;
            }
            returnObject.element(entry.getKey().toString(),aData.getString(entry.getValue().toString()));
        }
        return returnObject;
    }
    public JSONObject dealWithMessageFieldData(JSONObject aData,HashMap<String,String> chosenFieldMap){
        JSONObject returnObject = new JSONObject();
        Iterator it = chosenFieldMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if(entry.getKey().toString().equals(getMYPROJECTNAME())){
                returnObject.element(entry.getKey().toString(),entry.getValue().toString());
                continue;
            }
            if(entry.getKey().toString().equals(getMYAUTHORIMAGE())||entry.getKey().toString().equals(getMYAUTHORNAME())){
                JSONObject authorData = aData.getJSONObject(getUSER());
                returnObject.element(entry.getKey().toString(),authorData.getString(entry.getValue().toString()));
                continue;
            }else if(entry.getKey().toString().equals(getPIPELINEID())||entry.getKey().toString().equals(getTIME())){
                JSONObject  objectAttributesData= aData.getJSONObject(getOBJECTATTRIBUTES());
                returnObject.element(entry.getKey().toString(),objectAttributesData.getString(entry.getValue().toString()));
                continue;
            }else{
                JSONObject  projectJson= aData.getJSONObject(getPROJECT());//
                if(entry.getKey().toString().equals(getMYURL())){
                    JSONObject objectAttributesData= aData.getJSONObject(getOBJECTATTRIBUTES());
                    returnObject.element(entry.getKey().toString(),projectJson.getString(entry.getValue().toString())+"pipelines/"+objectAttributesData.getString(getID()));
                    continue;
                }
                returnObject.element(entry.getKey().toString(),projectJson.getString(entry.getValue().toString()));
                continue;
            }
         }
        return returnObject;
    }

    public JSONObject removeHookData(JSONObject data){
        if(data.has(getTIME())){
            data.remove(getTIME());
        }
//        if(data.has(getPIPELINEID())){
//            data.remove(getPIPELINEID());
//        }
        return data;
    }

    public JSONObject removeData(JSONObject data){
        if(data.has(getTIME())){
            data.remove(getTIME());
        }
//        if(data.has(getPIPELINEID())){
//            data.remove(getPIPELINEID());
//        }
        return data;
    }
    public JSONObject dealWithPipelineStatus(JSONObject aData,JSONObject myData){
        if(aData.has(getOBJECTATTRIBUTES())){
            aData = aData.getJSONObject(getOBJECTATTRIBUTES());
        }
        if (aData.get(getSTATUS()).toString().equals(getFAILED())) {
            myData.element(getSTATUS(), getFALSE());
        }
        else if (aData.get(getSTATUS()).toString().equals(getSUCCESS())) {
            myData.element(getSTATUS(), getTRUE());
        }
        else if (aData.get(getSTATUS()).toString().equals(getRUNNING())) {
            myData.element(getSTATUS(), getRUNNING());
        }
        else if (aData.get(getSTATUS()).toString().equals(getBUILDING())) {
            myData.element(getSTATUS(), getBUILDING());
        }else{
            myData.element(getSTATUS(),getOTHER());
        }
        return myData;
    }
    //之后要删除
    public JSONObject addProjectID(JSONObject datas,String id){
        datas.element("id",id);
        return datas;
    }
    public JSONObject addEventType(JSONObject data){
        return data.element(getEVENTTYPE(),getPIPELINE());
    }
    public JSONObject dealWithData(JSONObject pipeLineData,String projectName,int chosenNumber,String gitIp,String projectId) throws ParseException {
        HashMap<String,String> chosenFieldMap = dealWithChosenField(0,chosenNumber,projectName,0,10);
        HashMap<String,String>  chosenFunctionMap = dealWithChosenFunction(0,chosenNumber,projectName,10,getFIELDLENGTH());
        return removeData(dealWithPipelineStatus(pipeLineData,dealWithFunctionData(addProjectID(dealWithFieldData(pipeLineData,chosenFieldMap,gitIp,projectId,projectName),projectId),chosenFunctionMap)));
    }
    public JSONObject dealWithHookMessage(JSONObject pipeLineData,String projectName,int chosenNumber) throws ParseException {
        HashMap<String,String> chosenFieldMap = dealWithChosenField(0,chosenNumber,projectName,0,10);
        HashMap<String,String>  chosenFunctionMap = dealWithChosenFunction(0,chosenNumber,projectName,10,getFIELDLENGTH());
        return addEventType(removeHookData(dealWithPipelineStatus(pipeLineData,dealWithFunctionData(dealWithMessageFieldData(pipeLineData,chosenFieldMap),chosenFunctionMap))));
    }
}
