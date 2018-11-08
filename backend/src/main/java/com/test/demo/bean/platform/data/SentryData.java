package com.test.demo.bean.platform.data;

import com.test.demo.DataBean.DataBeanDao.DatasBean;
import com.test.demo.DataBean.DataBeanDao.DatasBeanDao;
import com.test.demo.DataBean.Platform.Interface.Platforms;
import com.test.demo.DataBean.Platform.bean.GitLab;
import com.test.demo.DataBean.Platform.dao.GitLabDao;
import com.test.demo.DataBean.Platform.dao.SentryDao;
import com.test.demo.DataBean.Platform.bean.Sentry;
import com.test.demo.common.httpHelper.SentryPlatformHttpClientHelper;
import com.test.demo.common.word.WordHelp;
import com.test.demo.common.time.TimeHelper;
import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Getter
@Component
@Setter
public class SentryData extends Data implements MappingOperator{
    String name = "Sentry";
    private WordHelp wordHelp = new WordHelp();
    @Autowired
    DatasBeanDao datasBeanDao;
    @Autowired
    SentryDao sentryDao;
    @Autowired
    TrelloData trelloData;
    @Autowired
    GitLabDao gitLabDao;
    Sentry sentry;
    final private String ID = "id";
    final private int FIELDLENGTH = 20;
    final private String MYPROJECTNAME = "projectName";
    final private String FROMPROJECTNAME = "name";
    final private String ISSUEID = "issueId";
    final private String USERCOUNT = "userCount";
    final private String DIFFTIME = "diffTime";
    final private String DIFFTIMEFORID = "diffTimeId";
    final private String TIMEDEAL = "timeDeal";
    final private String LASTSEEN = "lastSeen";
    final private String TITLE = "title";
    final private String MYEVENTCOUNT = "eventCount";
    final private String FROMEVENTCOUNT = "count";
    final private String PROJECTID = "projectId";
    final private String FROMURL = "permalink";
    final private String MYURL = "url";
    final private String ORGANIZATION = "organization";
    final private String SLUG = "slug";
    final private String EVENTCOUNT = "eventCount";
    final private String COUNT = "count";
    final private String FIRSTSEEN = "firstSeen";
    final private String BJTIME = "beijing";
    final private String TIME = "time";
    final private String STANDARDTIME = "standard";
    final private String STANDERSORT = "standerSort";
    final private String DEALSORT = "dealSort";
    final private String NOSORT = "noSort";
    final private String EXCEPTION2TRELLO = "exception2Trello";
    final private String SEENTIME = "seenTime";
    final private String MYSTATUS = "status";
    final private String PLATFORM = "platform";
    private Logger logger = Logger.getLogger(SentryData.class);
    private TimeHelper timeHelper = new TimeHelper();
    private SentryPlatformHttpClientHelper sentryHttpClientHelper = new SentryPlatformHttpClientHelper();

    public boolean getPlatformFromDb() {
        Sentry sentry = sentryDao.byName(getName());
        if(sentry != null){
            this.sentry = sentry;
            return true;
        }
        return false;
    }

    public int getInterval(){
        if(sentry == null){
            return 0;
        }
        return sentry.getInterval();
    }
    public boolean setInterval(int interval){
        if(interval != getInterval()) {
            sentry.setInterval(interval);
            sentryDao.saveAndUpdate(sentry);
        }
        return true;
    }
    //需要做一波测试
    public boolean calcTimeForInterval(int now) {
        if(timeHelper.calcMinDiff(now, beforeTime) >= sentry.getInterval()){
            beforeTime = now;
            return true;
        }
        return false;
    }
    public JSONArray comfirmBaseAuthorityAndGetCheckBoxData(String sentryToken) throws IOException {
        return projects(dataOfAllProject(sentryToken));
    }

    public String dataOfAllProject(String sentryToken) throws IOException {
        String data = sentryHttpClientHelper.httpClientGetUilt(sentryHttpClientHelper.dealUrl("ALLPROJECTS","","",""), sentryToken);
        return data;
    }

    protected HashMap<String,String> choiceField(int choice, HashMap<String,String> chosenMap, String projectName){
        switch (choice){
            case 0:
                chosenMap.put(getPROJECTID(), getID());  //1 1
                break;
            case 1:
                chosenMap.put(getISSUEID(), getID()); //1 1
                break;
            case 2:
                chosenMap.put(getUSERCOUNT(), getUSERCOUNT());//0 0
                break;
            case 3:
                chosenMap.put(getTITLE(), getTITLE());//1 0
                break;
            case 4:
                chosenMap.put(getEVENTCOUNT(), getCOUNT());//1 0
                break;
            case 5:
                chosenMap.put(getMYURL(), getFROMURL());//1 0
                break;
            case 6:
                chosenMap.put(getMYPROJECTNAME(), getFROMPROJECTNAME());//1 0
                break;
            case 7:
                chosenMap.put(getID(), getID());//1 0
                break;
            case 8:
                chosenMap.put(getTIME(), getLASTSEEN());//1 0
                break;
            case 9:
                chosenMap.put(getTIME(), getFIRSTSEEN());//0 1
                 break;
            case 10:
                chosenMap.put(getMYSTATUS(), getMYSTATUS());//0 0  可以得到解决的情况
                break;
            case 11:
                chosenMap.put(getPLATFORM(), getPLATFORM());//1 1
                break;
            case 12:
                break;
            case 13:
                chosenMap.put(getDIFFTIME(), getDIFFTIME());//0 1
                break;
            case 14:
                chosenMap.put(getSTANDERSORT(), getSTANDERSORT());//0 0
                break;
            case 15:
                chosenMap.put(getDEALSORT(), getDEALSORT());//1 0
                break;
            case 16:
                chosenMap.put(getNOSORT(), getNOSORT());
                break;
            case 17:
                chosenMap.put(getTIME(), getSTANDARDTIME());
                break;
            case 18:
                chosenMap.put(getTIME(), getBJTIME());
                break;
            case 19:

                break;
            case 20:
                break;


        }
        return chosenMap;
    }
    private JSONObject dealWithFunctionData(JSONObject myData, HashMap<String,String> chosenFieldMap) throws ParseException {
        if(chosenFieldMap.containsKey(getTIME())){
            myData = timeData(getSEENTIME(),getTIME(), chosenFieldMap.get(getTIME()),myData);
        }
        if(chosenFieldMap.containsKey(getDEALSORT())){
            myData = timeDiff(getTIME(), myData,"DIFFANDDEAL");
        }
        if(chosenFieldMap.containsKey(getDIFFTIME())){
            myData = timeDiff(getTIME(), myData,"ONLYDIFF");
        }
        return myData;
    }
    private JSONObject timeDiff(String from, JSONObject myObject, String choice) throws ParseException {
        if(choice.equals("DIFFANDDEAL")) {
            myObject.element(getDIFFTIME(), timeHelper.dealWithTimeAndDiff(myObject.getString(from), "yyyy-MM-dd'T'HH:mm:ss'Z'"));
            myObject.element(getTIMEDEAL(), timeHelper.dealSecondToTimeLook(myObject.getString(getDIFFTIME())));
            myObject.element(getSEENTIME(), myObject.getJSONObject(getTIMEDEAL()).getString(getLASTSEEN()));
        }else if(choice.equals("ONLYDIFF")){
            myObject.element(getDIFFTIME(), timeHelper.dealWithTimeAndDiff(myObject.getString(from), "yyyy-MM-dd'T'HH:mm:ss'Z'"));
        }
        return myObject;
    }
    private JSONObject timeData(String my, String from, String choice, JSONObject myObject) throws ParseException {
        if(choice.equals(getSTANDARDTIME())){
            myObject.element(my, myObject.getString(from));
        }else if(choice.equals(getBJTIME())){
            myObject.element(my, timeHelper.time2GMT8(myObject.getString(from),"yyyy-MM-dd'T'HH:mm:ss'Z'"));
        }
        return myObject;
    }

    private JSONObject dealWithFieldData(JSONObject aProjectData,JSONObject aIssueData,HashMap<String,String> chosenFieldMap){
        JSONObject returnObject = new JSONObject();
        Iterator it = chosenFieldMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if(entry.getKey().toString().equals(getID()) || entry.getKey().toString().equals(getMYPROJECTNAME()) || entry.getKey().toString().equals(getPROJECTID())||entry.getKey().toString().equals(getPLATFORM())){
                returnObject.element(entry.getKey().toString(), aProjectData.getString(entry.getValue().toString()));
            }else {
                returnObject.element(entry.getKey().toString(), aIssueData.getString(entry.getValue().toString()));
            }
        }
        return returnObject;
    }
    synchronized public JSONObject callDataAndSave2DataBean(String name) throws IOException, ParseException {
        sentry = sentryDao.byName(name);
        JSONArray data = dataOfChosenProject(sentry);
        JSONArray addImageData = gitSentryMapping(data.getJSONArray(0), sentry);
        datasBeanDao.datasBeanFindAndUpdate(name, addImageData);
        JSONObject frontEndData;
        if(operator(sentry)){
            frontEndData = statusHelper.addBugStatusForArray(addImageData);
        }else{
            frontEndData = statusHelper.jsonArray2Success(addImageData);
        }
        return frontEndData;
    }


    public JSONObject socketGate(String name) throws IOException, ParseException {
        return callDataAndSave2DataBean(name);
    }

    public JSONObject dispelBug(){

        sentry = sentryDao.byName(name);
        DatasBean datasBean = datasBeanDao.datasBeanByName(name);
        if(sentry.getBugs()){
            sentry.setBugs(false);
            sentryDao.saveAndUpdate(sentry);
            return statusHelper.jsonArray2Success(JSONArray.fromObject(datasBean.getDatas()));

        }
        return statusHelper.jsonArray2Success(new JSONArray());
    }
    public JSONArray changeParams(JSONObject setting) throws IOException, ParseException {
        sentry = sentryDao.platformFindAndUpdate(getName(), setting);
        JSONArray result = dataOfChosenProject(sentry);
        sentry.setSetting(initSetting(result,setting));
        if(setting.has("sentryGitLabMapping")) {
            sentry.setSentryGitLabMapping(setting.getJSONArray("sentryGitLabMapping"));
            sentry.setSentryGitLabHashMap(dealWithOneForManyMapping(setting.getJSONArray("sentryGitLabMapping")));
        }
        sentryDao.saveAndUpdate(sentry);
        return gitSentryMapping(result.getJSONArray(0), sentry);
    }
    public JSONObject initSetting(JSONArray result,JSONObject setting){
        JSONArray sortIssuesID = result.getJSONArray(1);
        String lastIssue = "";
        if(sortIssuesID.size() > 0) {
            lastIssue =sortIssuesID.getJSONObject(0).getString("issueId");
        }
        JSONObject settingOfPlatform = new JSONObject();
        settingOfPlatform.element("lastIssue", lastIssue);
        settingOfPlatform.element("sortedIssue", sortIssuesID);
        if(setting.has("sentryTrelloMapping")) {
            settingOfPlatform.element("sentryTrelloMapping", setting.getJSONArray("sentryTrelloMapping"));//有可能不是array
        }else{
            settingOfPlatform.element("sentryTrelloMapping", new JSONArray());
        }
        settingOfPlatform.element("bug",false);
        return settingOfPlatform;
    }
    public JSONArray dataOfChosenProject(Platforms platforms) throws IOException, ParseException {
        sentry = (Sentry) platforms;
        JSONArray sortDataObject = new JSONArray();
        JSONArray idsOfAllProject = JSONArray.fromObject(dataOfAllProject(sentry.getPrivateToken()));
        JSONArray idOfChosenProject = idOfChosenProject(idsOfAllProject, sentry.getChosenProjects());
        sortDataObject.element(new JSONArray());
        sortDataObject.element(new JSONArray());
        for (int i = 0; i < idOfChosenProject.size(); i++) {
            JSONObject aProjectData = idOfChosenProject.getJSONObject(i);
            dataOfChosenProjectAndDiffArray(sortDataObject,aProjectData,35323, sentry.getPrivateToken());
        }
        sortDataObject.element(0, removeData(dealTimeToSort(sortDataObject.getJSONArray(0)))); // 0 是数据
        sortDataObject.element(1, dealDiffTime2Sort(sortDataObject.getJSONArray(1))); //1是排序的issueID

        return sortDataObject;
    }
    public JSONArray dataOfChosenProjectAndDiffArray(JSONArray sortDataArray, JSONObject aProjectData, int chosenNumber, String sentryToken) throws ParseException, IOException {
        JSONArray issueDatas = JSONArray.fromObject(sentryHttpClientHelper.httpClientGetUilt(sentryHttpClientHelper.dealUrl("ISSUE", aProjectData.getJSONObject(getORGANIZATION()).getString(getSLUG()), aProjectData.getString(getSLUG()), ""), sentryToken));
        for(int k = 0; k < issueDatas.size(); k++){
            sortDataArray.getJSONArray(0).element(dealWithData(aProjectData,issueDatas.getJSONObject(k),chosenNumber));
            sortDataArray.getJSONArray(1).element(removeDataOfObject(dealWithData(aProjectData,issueDatas.getJSONObject(k),8707)));
        }
        return sortDataArray;
    }
    private JSONObject dealWithData(JSONObject aProjectData, JSONObject aIssueData, int chosenNumber) throws ParseException {
        HashMap<String,String> chosenFieldMap = dealWithChosenField(2, chosenNumber,"",0,13);
        HashMap<String,String> chosenFunctionMap = dealWithChosenFunction(2, chosenNumber,"",13, getFIELDLENGTH());
        return dealWithFunctionData(dealWithFieldData(aProjectData,aIssueData,chosenFieldMap), chosenFunctionMap);
    }
    private JSONArray dealDiffTime2Sort(JSONArray idArrayJson){
        for (int i = 1; i < idArrayJson.size(); i++) {
            for (int j = i; j > 0; j--) {
                JSONObject behindIndex = idArrayJson.getJSONObject(j);
                int behindDateJson = behindIndex.getInt(getDIFFTIME());
                JSONObject frontndex = idArrayJson.getJSONObject(j - 1);
                int frontDateJson = frontndex.getInt(getDIFFTIME());
                if(behindDateJson < frontDateJson){
                    JSONObject tempObject = behindIndex;
                    idArrayJson.element(j, frontndex);
                    idArrayJson.element(j - 1, tempObject);
                }
            }
        }
        return idArrayJson;
    }
    private JSONArray dealTimeToSort(JSONArray idArrayJson) throws JSONException {
        for (int i = 1; i < idArrayJson.size(); i++) {
            for (int j = i; j > 0; j--) {
                JSONObject behindIndex = idArrayJson.getJSONObject(j);
                JSONObject behindDateJson = behindIndex.getJSONObject("timeDeal");
                JSONObject frontndex = idArrayJson.getJSONObject(j - 1);
                JSONObject frontDateJson = frontndex.getJSONObject("timeDeal");
                if (behindDateJson.getInt("timeFlag") < frontDateJson.getInt("timeFlag")) {
                    JSONObject tempObject = behindIndex;
                    idArrayJson.element(j, frontndex);
                    idArrayJson.element(j - 1, tempObject);
                } else if (behindDateJson.getInt("timeFlag") == frontDateJson.getInt("timeFlag")) {
                    if (behindDateJson.getInt("time") < frontDateJson.getInt("time")) {
                        JSONObject tempObject = behindIndex;
                        idArrayJson.element(j, frontndex);
                        idArrayJson.element(j - 1, tempObject);
                    } else if (behindDateJson.getInt("time") == frontDateJson.getInt("time")) {
                        if (behindIndex.getInt("eventCount") > frontndex.getInt("eventCount")) {
                            JSONObject tempObject = behindIndex;
                            idArrayJson.element(j, frontndex);
                            idArrayJson.element(j - 1, tempObject);
                        }
                    }
                } else {
                    break;
                }
            }
        }
        return idArrayJson;
    }
    private JSONObject removeDataOfObject(JSONObject data){
        if(data.has(getTIME())){
            data.remove(getTIME());
        }
        return data;
    }
    private JSONArray removeData(JSONArray datas){
        for(int i=0;i<datas.size();i++){
            JSONObject data = datas.getJSONObject(i);
            if(data.has(getTIMEDEAL())){
                data.remove(getTIMEDEAL());
            }
            if(data.has(getDIFFTIME())){
                data.remove(getDIFFTIME());
            }
            if(data.has(getTIME())){
                data.remove(getTIME());
            }
            datas.element(i,data);
        }
        return datas;
    }
    public JSONObject getSetting(){
        return sentry.getSetting();
    }
    public void setSetting(JSONObject setting){
        sentry.setSetting(setting);
    }
    public boolean operator(Sentry sentry) throws IOException {
        JSONArray sentry2issueIdArray = new JSONArray();
        JSONObject setting = sentry.getSetting();
        if(setting.getString("lastIssue").equals("") && setting.getJSONArray("sortedIssue").size() > 0){
            JSONObject last = setting.getJSONArray("sortedIssue").getJSONObject(0);
            setting.element("lastIssue", last.getString("issueId"));
            sentry.setSetting(setting);
            sentryDao.saveAndUpdate(sentry);
        }else{
            JSONArray issuesSortData = setting.getJSONArray("sortedIssue");
            String lastIssue = setting.getString("lastIssue");
            for(int i = 0; i < issuesSortData.size(); i++){
                JSONObject issuesObject = issuesSortData.getJSONObject(i);
                if(!issuesObject.getString("issueId").equals(lastIssue)){
                    if(i==0) {
                        setting.element("lastIssue",issuesObject.getString("issueId"));
                        //得到现在的时间，然后有一个计时的操作
                        setting.element("newIssue",true);
                        //不想加卡进去
                    }
                    JSONObject sentry2issueIdObject = new JSONObject();
                    sentry2issueIdObject.put("issueId", issuesObject.getString("issueId"));
                    sentry2issueIdObject.put("sentryId", issuesObject.getString("projectId"));
                    sentry2issueIdArray.element(sentry2issueIdObject);
                    break;//只想加一张进去
                }else{
                    break;
                }
            }
        }
        if(sentry2issueIdArray.size() > 0) {
            addBug2Trello(sentry2issueIdArray, sentry);
            setting.element("bug", true);
            sentry.setSetting(setting);
            sentryDao.saveAndUpdate(sentry);
            return true;
        }
        return false;
    }
    public int addBug2Trello(JSONArray sentry2issueIdArray, Sentry sentry) throws IOException {
        JSONArray issueAndTreloId = new JSONArray();
        if(sentry2issueIdArray.size() != 0) {
            //修改这一个
            HashMap map = dealWithOneForManyMapping(sentry.getSetting().getJSONArray("sentryTrelloMapping"));
            for(int i = 0; i < sentry2issueIdArray.size(); i++) {
                JSONObject trello2IssueId = sentry2issueIdArray.getJSONObject(i);
                String trelloId = (String)map.get(trello2IssueId.getString("sentryId"));
                String issueId = trello2IssueId.getString("issueId");
                if (!dealIssues2Event2Backend(sentry, trelloId, issueId)) {
                    JSONObject object = new JSONObject();
                    object.element("trelloId", trelloId);
                    object.element("issueId", issueId);
                    issueAndTreloId.element(object);
                }
            }
        }
        return issueAndTreloId.size();
    }

    public boolean dealIssues2Event2Backend(Sentry sentry, String trelloId, String issuesId) throws JSONException, IOException {
        JSONArray dataOfIssue = getAIssue(sentry.getPrivateToken(),issuesId);
        JSONObject issueDesc = dealIssueEventData(dataOfIssue);
        return trelloData.addExceptionCard(trelloId,issueDesc.getString("title"), issueDesc.getString("description"));
    }

    public JSONArray getAIssue(String sentryToken, String issuesId) throws IOException {

        JSONArray dataOfIssue = JSONArray.fromObject(sentryHttpClientHelper.httpClientGetUilt(sentryHttpClientHelper.dealUrl("ISSUEEVENT", "", "",issuesId), sentryToken));
        return dataOfIssue;
    }
    public JSONObject dealIssueEventData(JSONArray jsonArray) throws JSONException, UnsupportedEncodingException {
        JSONObject returnJsonObject = new JSONObject();
        StringBuilder description = new StringBuilder();
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        String eventId = jsonObject.getString("eventID");
        String tags = jsonObject.getString("tags");
        JSONArray tagArray = JSONArray.fromObject(tags);
        JSONObject contexts = jsonObject.getJSONObject("contexts");
        JSONObject metadata = jsonObject.getJSONObject("metadata");
        StringBuilder lines =new StringBuilder();
        lines.append(wordHelp.addLine(wordHelp.addStrong("ExceptionType")) + (metadata.getString("type")));
        description.append(wordHelp.addUnit("exception", lines.toString()));
        for (int i = 0; i < tagArray.size(); i++) {
            JSONObject tag = tagArray.getJSONObject(i);
            if (tag.getString("key").equals("os")) {
                lines = new StringBuilder();
                lines.append(wordHelp.addLine(wordHelp.addStrong("OS") + (tag.getString("value"))));
                description.append(wordHelp.addUnit("OS_", lines.toString()));
            }

            if (tag.getString("key").equals("user")) {
                lines = new StringBuilder();
                lines.append(wordHelp.addLine(wordHelp.addStrong("address") + (tag.getString("value"))));
                description.append(wordHelp.addUnit("user", lines.toString()));
            }
        }
        String[][] str1= new String[][]{
            {"device","screen_resolution", "battery_level", "orientation", "family", "model_id", "brand", "storage_size", "screen_dpi","memory_size","arch","manufacturer","simulator","low_memory","online","model","screen_density","charging","boot_time"},
            {"os", "kernel_version"},
            {"browser", "name", "version"},
            {"app", "app_version", "app_start_time", "app_identifier", "app_name"}

        };
        for(int i = 0; i < str1.length; i++){
            String lineHeader = str1[i][0];
            if(contexts.has(lineHeader)){
                JSONObject header =contexts.getJSONObject(lineHeader);
                lines =new StringBuilder();
                for (int j = 1; j < str1[i].length; j++) {
                    if (header.has(str1[i][j])) {
                        lines.append(wordHelp.addLine(wordHelp.addStrong(str1[i][j]) + (header.getString(str1[i][j]))));
                    }
                }
                description.append(wordHelp.addUnit(lineHeader, lines.toString()));
            }
        }
        returnJsonObject.put("title", sentryHttpClientHelper.encoderUrl(metadata.getString("value")));
        returnJsonObject.put("description", sentryHttpClientHelper.encoderUrl(description.toString()));
        returnJsonObject.put("eventId", eventId);
        return returnJsonObject;
    }

    JSONArray gitSentryMapping(JSONArray data,Sentry sentry){
        GitLab mergeRequestData = gitLabDao.byName("Merge Request");
        JSONObject gitLabSentryHashMapping = sentry.getSentryGitLabHashMap();
        JSONArray gitLabProjectImage = new JSONArray();
        if(mergeRequestData != null){
            gitLabProjectImage = mergeRequestData.getImagesInSetting();
        }

        for(int i = 0; i < data.size(); i++){
            JSONObject aProject = data.getJSONObject(i);
            String gitId = (String) gitLabSentryHashMapping.get(aProject.getString("id"));
            for(int j = 0; j < gitLabProjectImage.size(); j++){
                JSONObject aProjectImage = gitLabProjectImage.getJSONObject(j);
                if(aProjectImage.getString("id").equals(gitId)) {
                    aProject.element("gitLabProjectName", aProjectImage.getString("projectName"));
                    aProject.element("gitLabProjectImage", aProjectImage.getString("projectImage"));
                }
            }
            data.element(i,aProject);
        }
        return data;
    }
    public HashMap dealWithOneForManyMapping(JSONArray gitlabSentryMapping){
        HashMap gitLabSentryHashMapping = new HashMap<>();
        for(int i = 0; i < gitlabSentryMapping.size(); i++){
            JSONObject object = gitlabSentryMapping.getJSONObject(i);
            JSONArray  many = object.getJSONArray("sentry");
            for(int j = 0; j < many.size(); j++){
                String  one = object.getString("trello");
                gitLabSentryHashMapping.put(many.getJSONObject(j).getString("id"), one);
            }
        }
        return gitLabSentryHashMapping;
    }
}
