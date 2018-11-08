package com.test.demo.bean.platform.data;

import com.test.demo.DataBean.Platform.Interface.Platforms;
import com.test.demo.DataBean.Platform.bean.GitLab;
import com.test.demo.DataBean.Platform.dao.GitLabDao;
import com.test.demo.common.httpHelper.GitlabPlatformHttpClientHelper;
import com.test.demo.common.time.TimeHelper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ToString
@Getter
@Component
@Setter
@Service
public class GitData extends Data{
    private final String DATELASTACTIVITY ="dateLastActivity";
    private final String MYAUTHORIMAGE ="authorImage";
    private final String FROMAUTHORIMAGE ="avatar_url";
    private final String BJTIME = "beijing";
    private final String STANDARDTIME = "standard";
    private final String TIME ="time";
    private final String SORT = "sort";
    private final String MYAUTHORNAME ="authorName";
    private final String FROMAUTHORNAME ="username";
    private final String MYCREATEDAT ="createdAt";
    private final String FROMCREATEDAT ="created_at";
    private final String USER = "user";
    private final String MYUPDATEDAT ="updatedAt";
    private final String FROMUPDATEDAT ="updated_at";
    private final String MYURL ="url";
    private final String FROMMURL ="web_url";
    private final String FALSE ="false";
    private final String TRUE ="true";
    private final String OBJECTATTRIBUTES ="object_attributes";
    private final String BJTIMEUTC = "beijingUTC";
    private final String TIMEFLAG = "timeFlag";
    @Autowired
    MergeRequestData mergeRequestData;
    @Autowired
    CIData ciData;
    @Autowired
    GitLab gitLab;
    @Autowired
    GitLabDao gitLabDao;
    protected GitlabPlatformHttpClientHelper gitlabHttpClientHelper = new GitlabPlatformHttpClientHelper();
    private Logger logger = Logger.getLogger(Data.class);
    protected TimeHelper timeHelper = new TimeHelper();
    public String dataOfAllProject(String gitIp, String gitPrivateToken) throws IOException {
        return gitlabHttpClientHelper.httpClientGetUilt(gitlabHttpClientHelper.dealUrl("ALLGITPROJECT", gitIp, "", ""), gitPrivateToken);
    }

    public JSONArray sortAndRemoveData(JSONArray dataArray){
        if(dataArray.size() != 0 && dataArray.getJSONObject(0).has(getDATELASTACTIVITY())){
            dataArray = dateLastActivity(timeHelper.sortDate(dataArray));
        }
        return dataArray;
    }
    public JSONArray dateLastActivity(JSONArray datas){
        for(int i = 0; i < datas.size(); i++){
            JSONObject data = datas.getJSONObject(i);
            data.remove(getDATELASTACTIVITY());
            datas.element(i,data);
        }
        return datas;
    }
    public JSONArray dataOfChosenProject(JSONObject aProject, JSONArray returnDatas, int showNumber, String gitIp, String gitPrivateToken, int chosenNumber, String projectId) throws IOException, ParseException {
        return null;
    }
    public JSONObject dealWithFunctionData(JSONObject myData, HashMap<String,String> chosenFieldMap) throws ParseException {
        if(chosenFieldMap.containsKey(getTIME())){
            myData=timeData(getMYUPDATEDAT(),getTIME(),chosenFieldMap.get(getTIME()),myData);
        }
        if(chosenFieldMap.containsKey(getSORT())){
            myData = timeDiff(getTIME(),myData);
        }
        if(chosenFieldMap.containsKey(getTIMEFLAG())){
            myData = timeFlag(getTIME(),myData);
        }
        return myData;
    }
    public JSONObject timeDiff(String from, JSONObject myObject) throws ParseException {
        myObject.element(getDATELASTACTIVITY(), timeHelper.dealWithTimeAndDiff(myObject.getString(from),"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        return myObject;
    }
    public JSONObject timeFlag(String from, JSONObject myObject) throws ParseException {
        int interval = Integer.valueOf(timeHelper.dealWithTimeAndDiff(myObject.getString(from),"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        if(interval >= 60*60*24) {
            myObject.element(getTIMEFLAG(),false);
        }else{
            myObject.element(getTIMEFLAG(),true);
        }
        return myObject;
    }
    public JSONObject timeData(String my, String from, String choice, JSONObject myObject) throws ParseException {
        if(choice.equals(getSTANDARDTIME())){
            myObject.element(my, myObject.getString(from));
        }else if(choice.equals(getBJTIME())){
            myObject.element(my, timeHelper.time2GMT8(myObject.getString(from),"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        }else if(choice.equals(getBJTIMEUTC())){
            myObject.element(my, timeHelper.time2GMT8(myObject.getString(from),"yyyy-MM-dd HH:mm:ss 'UTC'"));
        }
        return myObject;
    }


    @Override
    public boolean getPlatformFromDb() {
        GitLab gitLab = gitLabDao.byName(getName());
        if(gitLab!=null){
            this.gitLab = gitLab;
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int getInterval() {
        if(gitLab == null){
            return 0;
        }
        return gitLab.getInterval();
    }

    @Override
    public boolean setInterval(int interval) {
        if(interval != getInterval()) {
            gitLab.setInterval(interval);
            gitLabDao.saveAndUpdate(gitLab);
        }
        return false;
    }
    public boolean calcTimeForInterval(int now) {
        if(beforeTime == 0 || timeHelper.calcMinDiff(now,beforeTime) == gitLab.getInterval()){
            beforeTime = now;
            return true;
        }
        return false;
    }

    @Override
    public JSONArray comfirmBaseAuthorityAndGetCheckBoxData(String token) throws IOException {
        return null;
    }


    public JSONArray comfirmBaseAuthorityAndGetCheckBoxData(String gitIp, String gitPrivateToken) throws IOException {
        return projects(dataOfAllProject(gitIp, gitPrivateToken));
    }

    @Override
    public String dataOfAllProject(String code) throws IOException {
        return null;
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
        return null;
    }

    @Override
    public JSONArray dataOfChosenProject(Platforms platforms) throws IOException {
        return null;
    }
   public JSONArray dataOfChosenProject2Hook(GitLab gitLab) throws IOException {
        return addHook(comfirmHook(gitLab), gitLab);
    }

    public JSONObject dataOfSendMessage(String data) throws ParseException {
        JSONObject sendJson = JSONObject.fromObject(data);
        if(sendJson.has("object_kind") && sendJson.getString("object_kind").equals("merge_request")) {
            return mergeRequestData.dealWithHookMessage(sendJson,"",12215);//12215 10167
        }else if(sendJson.has("object_kind")&&sendJson.getString("object_kind").equals("pipeline")){
            return ciData.dealWithHookMessage(sendJson,"",18343);
        }
        return new JSONObject();
    }
    public JSONArray comfirmHook(GitLab gitLab){
        JSONArray projectIds = gitLab.getChosenProjects();
        JSONArray hookArray = gitLab.getHooksInSetting();
        List<Integer> projectIdsTemp = new ArrayList<>();
        JSONArray hookTemp = new JSONArray();
        for(int i = 0; i < projectIds.size(); i++){
            projectIdsTemp.add(i);
        }
        for(int i = 0; i < projectIds.size(); i++){
            for(int j = 0; j < hookArray.size(); j++){
                JSONObject aHook = hookArray.getJSONObject(j);
                if(aHook.getString(getID()).equals(projectIds.getString(i))){
                    projectIdsTemp.set(i, -1);
                    break;
                }
            }
        }
        for(int i = 0; i < projectIdsTemp.size(); i++){
            if(projectIdsTemp.get(i) != -1) {
                hookTemp.element(projectIds.get(projectIdsTemp.get(i)));
            }
        }
        return hookTemp;
    }
    public JSONArray addHook(JSONArray newHooks,GitLab gitLab) throws JSONException, IOException {
        JSONArray hookArray = gitLab.getHooksInSetting();
        for (int i = 0; i < newHooks.size(); i++) {
            JSONObject jsonObjectTemp = new JSONObject();
            JSONObject permisionData = JSONObject.fromObject(gitlabHttpClientHelper.httpClientPostUilt(gitlabHttpClientHelper.dealUrl("HOOK",gitLab.getIp(), gitLab.getChosenProjects().getString(i),""), gitLab.getPrivateToken(),"admin"));
            jsonObjectTemp.element(getMYURL(), gitlabHttpClientHelper.dealUrl("ADDWS","","",permisionData.getString("url").substring(4)));
            jsonObjectTemp.element(getID(), gitLab.getChosenProjects().get(i));
            hookArray.element(jsonObjectTemp);
        }
        return hookArray;
    }


    public JSONArray allProjectImage(GitLab mergeRequest) throws IOException {
        JSONArray idsOfAllProject = JSONArray.fromObject(dataOfAllProject(mergeRequest.getIp(),mergeRequest.getPrivateToken()));
        JSONArray ids =mergeRequest.getChosenProjects();
        JSONArray returnArray = new JSONArray();
        for(int i = 0 ; i < ids.size(); i++){
            String id = ids.getString(i);
            for(int j = 0; j < idsOfAllProject.size(); j++){
                JSONObject project = idsOfAllProject.getJSONObject(j);
                if(project.getString("id").equals(id)){
                    JSONObject object = new JSONObject();
                    object.element("id",project.getString("id"));
                    object.element("projectName",project.getString("name"));
                    if(project.getString("avatar_url").equals("null")) {
                        object.element("projectImage", "");
                    }else{
                        object.element("projectImage", project.getString("avatar_url"));
                    }
                    returnArray.element(object);
                }
            }
        }
        return returnArray;
    }
}
