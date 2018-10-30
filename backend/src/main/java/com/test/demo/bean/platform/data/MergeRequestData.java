package com.test.demo.bean.platform.data;

import com.test.demo.DataBean.DataBeanDao.DatasBean;
import com.test.demo.DataBean.DataBeanDao.DatasBeanDao;
import com.test.demo.DataBean.Platform.dao.GitLabDao;
import com.test.demo.DataBean.Platform.bean.GitLab;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
@ToString
@Component
@Setter
@Service
public class MergeRequestData extends GitData {
    String name="Merge Request";
    @Autowired
    GitLabDao gitLabDao;
    @Autowired
    DatasBeanDao datasBeanDao;
    GitLab gitLab;
    private Logger logger = Logger.getLogger(MergeRequestData.class);
    private  int FIELDLENGTH =20;
    private final String MERGEREQUESTID = "mergeRequestId";
    private final String AUTHOR ="author";
    private final String LINKS ="_links";
    private final String MERGE_REQUESTS ="merge_requests";
    private final String TITLE ="title";
    private final String MYTARGETBRANCH ="targetBranch";
    private final String FROMTARGETBRANCH ="target_branch";
    private final String FROMSOURCEBRANCH ="source_branch";
    private final String MYSOURCEBRANCH ="sourceBranch";
    private final String SOURCEBRANCH ="merge_requests";
    private final String FROMMERGESTATUS ="merge_status";
    private final String MYMERGESTATUS ="mergeStatus";
    private final String PENDING ="pending";
    private final String UNCHECKED ="unchecked";
    private final String CAN_BE_MERGED ="can_be_merged";
    private final String CANNOT_BE_MERGED ="cannot_be_merged";
    private final String EVENTTYPE ="eventType";
    private final String MERGEREQUEST ="mergeRequest";
    private final String PROJECTIMAGE ="projectImage";
    private final String FROMPROJECTIMAGE="avatar_url";


//    @Override
//    public boolean getPlatformFromDb() {
//        GitLab gitLab = gitLabDao.byName(getName());
//        if(gitLab!=null){
//            this.gitLab = gitLab;
//            return true;
//        }else{
//            return false;
//        }
//    }
//
//    @Override
//    public int getInterval() {
//        if(gitLab==null){
//            return 0;
//        }
//        return gitLab.getInterval();
//    }
//
//    @Override
//    public boolean setInterval(int interval) {
//        if(interval!=getInterval()) {
//            gitLab.setInterval(interval);
//            gitLabDao.saveAndUpdate(gitLab);
//        }
//        return false;
//    }
//    public boolean calcTimeForInterval(int now) {
////        logger.info("meger_now:"+timeHelper.calcMinDiff(now,beforeTime));
//        if(beforeTime==0||timeHelper.calcMinDiff(now,beforeTime) ==gitLab.getInterval()){
////            logger.info("meger_begin:"+timeHelper.calcMinDiff(now,beforeTime));
//            beforeTime = now;
//            return true;
//        }
//        return false;
//    }

    //需要测试 并且需要重写chosenOfChosenProject方法
    public JSONArray changeParams(JSONObject setting) throws IOException, ParseException {
//        GitLab mergeRequest = gitLabDao.byName(getName());
//        if(mergeRequest==null){
//            mergeRequest = new GitLab();
//            mergeRequest.setName("Merge Request");
//        }
//        JSONArray allProjects = setting.getJSONArray("allProjects");
//        JSONArray chosenProject = setting.getJSONArray("chosenProjects");
//        String privateToken = setting.getString("privateToken");
//        String ip = setting.getString("ip");
//        mergeRequest.setAllProjects(allProjects);
//        mergeRequest.setChosenProjects(chosenProject);
//        mergeRequest.setPrivateToken(privateToken);
//        mergeRequest.setIp(ip);
//        mergeRequest.setInterval(setting.getInt("interval"));
//        gitLabDao.saveAndUpdate(mergeRequest);
        GitLab mergeRequest =(GitLab)gitLabDao.platformFindAndUpdate(getName(),setting);
        JSONArray newHooks= dataOfChosenProject2Hook(mergeRequest);
        JSONArray projectImages= allProjectImage(mergeRequest);
        mergeRequest.setImageInSetting(projectImages);
        mergeRequest = gitLabDao.saveAndUpdate(mergeRequest);
        if(newHooks.size()>mergeRequest.getHooksInSetting().size()){
            mergeRequest.setHooksInSetting(newHooks);
            mergeRequest = gitLabDao.saveAndUpdate(mergeRequest);
        }
        return dataOfChosenProject(mergeRequest);
    }

    synchronized public JSONObject callDataAndSave2DataBean(String name) throws IOException, ParseException {
        GitLab gitLab = gitLabDao.byName(name);
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
    public JSONObject socketGate(String name) throws IOException, ParseException {
        return callDataAndSave2DataBean(name);
    }
    public JSONArray dataOfChosenProject(GitLab mergeRequest) throws IOException, ParseException {
        JSONArray idsOfAllProject = JSONArray.fromObject(dataOfAllProject(mergeRequest.getIp(),mergeRequest.getPrivateToken()));
        JSONArray idArrayJson = idOfChosenProject(idsOfAllProject,mergeRequest.getChosenProjects());
        JSONArray dataArray = new JSONArray();
        for (int i = 0; i < idArrayJson.size(); i++) {
            JSONObject aData = idArrayJson.getJSONObject(i);
            dataArray=dealWithData(aData,dataArray,mergeRequest.getIp(),mergeRequest.getPrivateToken(),216057,aData.getString("id"));
//            82425
        }
        return sortAndRemoveData(dataArray);
    }


// allDataAndOperation.element(getMERGEDATA(),mergeRequestData.dataOfProjects(datas,projectIds,gitIp,0,gitPrivateToken,mergeRequestData,82425));
    public JSONArray dealWithData(JSONObject aProject, JSONArray returnDatas,  String gitIp, String gitPrivateToken, int chosenNumber, String projectId) throws IOException, ParseException {

//        JSONObject links = JSONObject.fromObject(aProject.getString(getLINKS()));

//        JSONArray datas = JSONArray.fromObject(gitlabHttpClientHelper.httpClientGetUilt(links.getString(getMERGE_REQUESTS()), gitPrivateToken));
        JSONArray datas = JSONArray.fromObject( gitlabHttpClientHelper.httpClientGetUilt(gitlabHttpClientHelper.dealUrl("Merge Request", gitIp, projectId, ""), gitPrivateToken));
        for (int i = 0; i < datas.size(); i++) {
            returnDatas.element(dealWithData(datas.getString(i), aProject,chosenNumber));
        }
        if(returnDatas.size()!=0&&returnDatas.getJSONObject(0).has(getDATELASTACTIVITY())){
            timeHelper.sortDate(returnDatas);
        }
        return returnDatas;
    }
    public HashMap<String,String> choiceField(int choice,HashMap<String,String> chosenMap,String projectName){
        switch (choice){
            case 0:
                chosenMap.put(getMERGEREQUESTID(),getID());  //1 1
                break;
            case 1:
                chosenMap.put(getTITLE(),getTITLE()); //0 1
                break;
            case 2:
                chosenMap.put(getTIME(),getFROMCREATEDAT());//0 1
                break;
            case 3:
                chosenMap.put(getTIME(),getFROMUPDATEDAT());//1 0
                break;
            case 4:
                chosenMap.put(getMYTARGETBRANCH(),getFROMTARGETBRANCH());//1 1
                break;
            case 5:
                chosenMap.put(getMYSOURCEBRANCH(),getFROMSOURCEBRANCH());//1 1
                break;
            case 6:
                chosenMap.put(getMYURL(), getFROMMURL());//1 0
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
                chosenMap.put(getMYURL(), getMYURL());//0 1  110100101111111001
                break;
            case 11:
                chosenMap.put(getPROJECTIMAGE(),getFROMPROJECTIMAGE());//1 1
                break;
            case 12:

                break;
            case 13:
                chosenMap.put(getTIME(),getBJTIMEUTC());//0 1
                break;
            case 14:
                chosenMap.put(getTIME(),getBJTIME()); //1 0
                break;
            case 15:
                chosenMap.put(getTIME(),getSTANDARDTIME());// 0 0
                break;
             case 16:
                 chosenMap.put(getSORT(),getSORT());// 1 0  timeFlag
                break;
            case 17:
                chosenMap.put(getTIMEFLAG(),getTIMEFLAG());// 1 0
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

    public JSONObject dealWithFieldData(JSONObject aData,HashMap<String,String> chosenFieldMap,JSONObject aProject){
        JSONObject returnObject = new JSONObject();
        Iterator it = chosenFieldMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if(entry.getKey().toString().equals(getMYPROJECTNAME())){
                returnObject.element(entry.getKey().toString(),entry.getValue().toString());
                continue;
            }
            if(entry.getKey().toString().equals(getPROJECTIMAGE())){

                if(aProject.getString(entry.getValue().toString()).equals("null")){
                    returnObject.element(entry.getKey().toString(),"");
                }else{
                    returnObject.element(entry.getKey().toString(),aProject.getString(entry.getValue().toString()));
                }
                continue;
            }
            if(entry.getKey().toString().equals(getMYAUTHORIMAGE())||entry.getKey().toString().equals(getMYAUTHORNAME())){
                JSONObject authorData = aData.getJSONObject(getAUTHOR());
                returnObject.element(entry.getKey().toString(),authorData.getString(entry.getValue().toString()));
                continue;
            }
            returnObject.element(entry.getKey().toString(),aData.getString(entry.getValue().toString()));
        }
        return returnObject;
    }
    public JSONObject dealWithMessageFieldData(JSONObject aData,HashMap<String,String> chosenFieldMap){
       //logger.info("chosenFieldMap:"+chosenFieldMap);
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
            }else{

                JSONObject  objectAttributesData= aData.getJSONObject(getOBJECTATTRIBUTES());
                returnObject.element(entry.getKey().toString(),objectAttributesData.getString(entry.getValue().toString()));
            }
           // returnObject.element(entry.getKey().toString(),aData.getString(entry.getValue().toString()));
        }
        return returnObject;
    }



    public JSONObject dealWithMergeStatus(JSONObject aData,JSONObject myData){
        if(aData.has(getOBJECTATTRIBUTES())){
            aData = aData.getJSONObject(getOBJECTATTRIBUTES());
        }
        if (aData.get(getFROMMERGESTATUS()).toString().equals(getCANNOT_BE_MERGED())) {
            myData.element(getMYMERGESTATUS(), getFALSE());
        }
        if (aData.get(getFROMMERGESTATUS()).toString().equals(getCAN_BE_MERGED())) {
            myData.element(getMYMERGESTATUS(), getTRUE());
        }
        if (aData.get(getFROMMERGESTATUS()).toString().equals(getUNCHECKED())) {
            myData.element(getMYMERGESTATUS(), getPENDING());
        }
        return myData;
    }
    public JSONObject removeData(JSONObject data){
        if(data.has(getTIME())){
            data.remove(getTIME());
        }
//        if(data.has(getMERGEREQUESTID())){
//            data.remove(getMERGEREQUESTID());
//        }
        return data;
    }
    //之后要删除
    public JSONObject addProjectID(JSONObject datas,JSONObject project){
        datas.element("id",project.getString("id"));
        return datas;
    }
    public JSONObject dealWithData(String mergeRequestsData, JSONObject aProject,int chosenNumber) throws ParseException {
        JSONObject aData = JSONObject.fromObject(mergeRequestsData);
        HashMap<String,String>  chosenFieldMap = dealWithChosenField(1,chosenNumber,aProject.getString(getFROMPROJECTNAME()),0,13);
        HashMap<String,String>  chosenFunctionMap = dealWithChosenFunction(1,chosenNumber,aProject.getString(getFROMPROJECTNAME()),10,getFIELDLENGTH());
        return removeData(dealWithMergeStatus(aData,dealWithFunctionData(addProjectID(dealWithFieldData(aData,chosenFieldMap,aProject),aProject),chosenFunctionMap)));
    }
    public JSONObject dealWithHookMessage(JSONObject aData,String projectName,int chosenNumber) throws ParseException {
        HashMap<String,String>  chosenFieldMap = dealWithChosenField(1,chosenNumber,projectName,0,13);
        HashMap<String,String>  chosenFunctionMap = dealWithChosenFunction(1,chosenNumber,projectName,13,getFIELDLENGTH());
        return addEventType(removeData(dealWithMergeStatus(aData,dealWithFunctionData(dealWithMessageFieldData(aData,chosenFieldMap),chosenFunctionMap))));
    }
    public JSONObject addEventType(JSONObject data){
        return data.element(getEVENTTYPE(),getMERGEREQUEST());
    }
    public JSONArray dateLastActivity(JSONArray datas){
        for(int i=0;i<datas.size();i++){
            JSONObject data = datas.getJSONObject(i);
            data.remove(getDATELASTACTIVITY());
            datas.element(i,data);
        }
        return datas;
    }
}
