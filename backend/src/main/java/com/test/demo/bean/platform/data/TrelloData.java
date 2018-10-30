package com.test.demo.bean.platform.data;

import com.test.demo.DataBean.DataBeanDao.DatasBean;
import com.test.demo.DataBean.DataBeanDao.DatasBeanDao;
import com.test.demo.DataBean.Platform.Interface.Platforms;
import com.test.demo.DataBean.Platform.dao.TrelloDao;
import com.test.demo.DataBean.Platform.bean.Trello;
import com.test.demo.common.httpHelper.TrelloPlatformHttpClient;
import lombok.Getter;
import lombok.ToString;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;

@Getter
@Component
@ToString
public class TrelloData extends Data {
    private String name="Trello";
    private final String MYURL ="url";
    @Autowired
    TrelloDao trelloDao;
    @Autowired
    DatasBeanDao datasBeanDao;

    Trello trello;
    TrelloPlatformHttpClient trelloHttpClient = new TrelloPlatformHttpClient();

    public boolean getPlatformFromDb(){

        Trello trello = trelloDao.byName(getName());
        if(trello!=null){
            this.trello = trello;
            return true;
        }else{
            return false;
        }
    }
    public int getInterval(){

        if(trello==null){
            return 0;
        }
        return trello.getInterval();
    }
    public boolean setInterval(int interval){
        if(interval!=getInterval()) {
            trello.setInterval(interval);
            trelloDao.saveAndUpdate(trello);
        }
        return true;
    }

    public boolean calcTimeForInterval(int now) {
        if(beforeTime==0||timeHelper.calcMinDiff(now,beforeTime) ==trello.getInterval()){
            beforeTime = now;
            return true;
        }
        return false;
    }

    @Override
    public JSONArray comfirmBaseAuthorityAndGetCheckBoxData(String token) throws IOException {
        return null;
    }

    public JSONArray comfirmBaseAuthorityAndGetCheckBoxData(String trelloToken,String trelloAppkey) throws IOException {
        return projects(dataOfAllProject(trelloAppkey,trelloToken));
    }


    public String dataOfAllProject(String trelloAppkey,String trelloToken) throws IOException {
        String data = trelloHttpClient.httpClientGetUilt(trelloHttpClient.dealUrl("ALLPROJECTS", "", trelloToken, trelloAppkey, "", "", "", ""));
        return data;
    }
    public JSONArray projects(String data) {
        JSONArray projects = JSONArray.fromObject(data);
        JSONArray nameAndIdArray = new JSONArray();
        for(int i=0;i<projects.size();i++){
            JSONObject project = projects.getJSONObject(i);
            JSONObject nameAndIdObject = new JSONObject();
            nameAndIdObject.element(getID(),project.getString(getID()));
            nameAndIdObject.element(getMYPROJECTNAME(),project.getString(getFROMPROJECTNAME()));
            nameAndIdObject.element(getMYURL(),project.getString(getMYURL()));
            nameAndIdArray.element(nameAndIdObject);
        }
        return nameAndIdArray;
    }
    public JSONObject callDataAndSave2DataBean(String name) throws IOException, ParseException {
        Trello trello = trelloDao.byName(name);
        DatasBean datasBean = datasBeanDao.datasBeanByName(name);
        if(datasBean==null){
            datasBean = new DatasBean();
            datasBean.setName(getName());
        }
        JSONArray returnArray = dataOfChosenProject(trello);
        datasBean.setDatas(returnArray.toString());
        datasBeanDao.datasBeanSaveAndUpdate(datasBean);
        return statusHelper.jsonArray2Success(returnArray);
    }

    public JSONObject socketGate(String name) throws IOException, ParseException {
        return callDataAndSave2DataBean(name);
    }


    @Override
    public String dataOfAllProject(String code) throws IOException {
        return null;
    }

    //需要测试
    //
    public JSONArray changeParams(JSONObject setting) throws IOException, ParseException {
        JSONArray trelloData = dataOfChosenProject(trelloDao.platformFindAndUpdate(getName(),setting));
        datasBeanDao.datasBeanFindAndUpdate(getName(),trelloData);
        return trelloData;
    }
    public JSONArray dataOfChosenProject(Platforms platforms) throws JSONException, IOException, ParseException {
        //如果存在某些人去删除了项目，但是没有按搜索键，那么就会报错，因为也是从DB拿的旧数据
        //如果这个操作去执行，还要保存到 platform table中，那么就会导致出现"脏数据"的情况，就是保存到了AllProjects
        //而且后台刷新到了 allproject的更改，应该给frontend一个提醒才对
        Trello trello = (Trello) platforms;
        JSONArray idsOfAllProject = JSONArray.fromObject(dataOfAllProject(trello.getAppKey(),trello.getPrivateToken()));
        JSONArray idArrayJson = idOfChosenProject(idsOfAllProject,trello.getChosenProjects());
        JSONArray projects = new JSONArray();
        for (int i = 0; i < idArrayJson.size(); i++) {
            JSONObject idObjectJson = idArrayJson.getJSONObject(i);
            JSONObject oneProject = dealWithData(idObjectJson.getString("id"),trello.getPrivateToken(),trello.getAppKey());
            oneProject.element("id", idObjectJson.getString("id"));
//            oneProject.element("projectName", idObjectJson.getString("projectName"));
            oneProject.element("projectName", idObjectJson.getString(getFROMPROJECTNAME()));
            oneProject.element("broadUrl", idObjectJson.getString("url"));
            oneProject.element("sonarData",new JSONObject());
            projects.element(oneProject);
        }
        return projects;
    }
    public JSONObject initStateObjectAndArray(){
        JSONObject stateObjectAndArray = new JSONObject();
        stateObjectAndArray.element("bug",new JSONArray());
        stateObjectAndArray.element("todo",new JSONArray());
        stateObjectAndArray.element("todoCount",0);
        return stateObjectAndArray;
    }
    public JSONObject dealWithData(String id,String trelloToken, String trelloAppkey) throws IOException, ParseException {
        JSONArray lists = JSONArray.fromObject(trelloHttpClient.httpClientGetUilt(trelloHttpClient.dealUrl("AllCARDS", id, trelloToken, trelloAppkey, "", "", "", "")));
        JSONObject stateObjectAndArray = initStateObjectAndArray();
        JSONObject aProject = new JSONObject();
        aProject.put("allListNum", lists.size());
        aProject.put("testCardsNum", 0);
        aProject.put("doneCardsNum", 0);
        int allCardsNum = 0;
        for (int i = 0; i < lists.size(); i++) {
            JSONObject aList = lists.getJSONObject(i);
            JSONArray cards = aList.getJSONArray("cards");
            allCardsNum += cards.size();
            if (aList.getString("name").equals("Test Done")) {
                aProject = dealTestDoneList(cards,aProject);
                continue;
            }
            if (aList.getString("name").equals("Dev Done")) {
                aProject = dealDevDoneList(cards,aProject);
                continue;
            }
            stateObjectAndArray = dealList2TodoList(cards,stateObjectAndArray,trelloToken,trelloAppkey);
        }
        aProject.element("todoCards",sortOfTodoCard(stateObjectAndArray));
        aProject.element("todoCardsNum", stateObjectAndArray.getInt("todoCount"));
        aProject.element("allCardsNum", allCardsNum);
        aProject = initPieData(aProject);
        return aProject;
    }
    public JSONObject initPieData(JSONObject aProject){
        aProject.element("todoCardPercent", addPercentWord2Num(calcPacent(aProject.getDouble("todoCardsNum"),aProject.getDouble("allCardsNum"))));
        aProject.element("testCardPercent", addPercentWord2Num(calcPacent(aProject.getDouble("testCardsNum"),aProject.getDouble("allCardsNum"))));
        aProject.element("doneCardPercent", addPercentWord2Num(calcPacent(aProject.getDouble("doneCardsNum"),aProject.getDouble("allCardsNum"))));
        return aProject;
    }
    public JSONArray sortOfTodoCard(JSONObject stateObjectAndArray) throws JSONException {
        JSONArray allTodoList = timeHelper.sortDate(stateObjectAndArray.getJSONArray("bug"));
        JSONArray todoNew = timeHelper.sortDate(stateObjectAndArray.getJSONArray("todo"));
        for (int i = 0; i < todoNew.size(); i++) {
            allTodoList.element(todoNew.getJSONObject(i));
        }
        return allTodoList;
    }
    public JSONObject dealTestDoneList(JSONArray cards,JSONObject aProject){
        return aProject.element("testCardsNum", cards.size());
    }
    public JSONObject dealDevDoneList(JSONArray cards,JSONObject aProject){
        return aProject.element("doneCardsNum", cards.size());
    }
    public JSONObject dealList2TodoList(JSONArray cards,JSONObject stateObjectAndArray,String trelloToken, String trelloAppkey) throws IOException, ParseException {
        for (int i = 0; i < cards.size(); i++) {
           JSONObject aCard = dealACard(cards.getJSONObject(i),trelloToken,trelloAppkey);
           if(aCard.getDouble("all")!=0||aCard.getString("state").equals("bug")){
               stateObjectAndArray = addAcard2Object(aCard.getString("state"),aCard,stateObjectAndArray);
           }
        }
        return stateObjectAndArray;
    }
    public JSONObject dealACard(JSONObject aCard,String trelloToken, String trelloAppkey) throws ParseException, IOException {
        JSONObject tempCard = new JSONObject();
        tempCard.element("dateLastActivity", timeHelper.dealWithTimeAndDiff(aCard.getString("dateLastActivity"),"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        tempCard.element("cardName", aCard.getString("name"));
        tempCard.element("cardUrl", aCard.getString("url"));
        JSONObject idcheckLists = aCard.getJSONObject("badges");
        double all = idcheckLists.getDouble("checkItems");
        tempCard.element("state", comfirmHaveBug(aCard.getJSONArray("labels")));
        double completeNum = idcheckLists.getDouble("checkItemsChecked");
        double incompleteNum = all - completeNum;
        tempCard.element("incomplete", incompleteNum);
        tempCard.element("complete", completeNum);
        tempCard.element("all", all);
        tempCard.element("percent", calcPacent(completeNum,all));
        tempCard.element("members", dealMembers(aCard,trelloToken,trelloAppkey));
        return tempCard;
    }
    public JSONObject addAcard2Object(String bugFlag,JSONObject aCard,JSONObject stateObjectAndArray){
        stateObjectAndArray.getJSONArray(bugFlag).element(aCard);
        stateObjectAndArray.element("todoCount",stateObjectAndArray.getInt("todoCount")+1);
        return stateObjectAndArray;
    }
    public JSONArray dealMembers(JSONObject aCard, String trelloToken, String trelloAppkey) throws IOException {
        JSONArray members = aCard.getJSONArray("idMembers");
        JSONArray returnMembers = new JSONArray();
        if (members.size() != 0) {
            JSONArray membersOfCard = JSONArray.fromObject(trelloHttpClient.httpClientGetUilt(trelloHttpClient.dealUrl("MEMBERS", "", trelloToken, trelloAppkey, "", "", aCard.getString("id"), "")));
            for (int i = 0; i < membersOfCard.size(); i++) {
                JSONObject names = membersOfCard.getJSONObject(i);
                returnMembers.element(names.getString("initials"));
//                          members.put(names.getString("fullName"));  全名
//                          members.put(names.getString("username"));  用户名
            }
        }
        return returnMembers;
    }
    public String addPercentWord2Num(double answear){
        DecimalFormat df = new DecimalFormat("#0.0");
        return df.format(answear)+"%";
    }
    public double calcPacent(double part,double all){
        return  all <= 0 ? 0 : (Math.round(part * 10000 / all) / 100.00);
    }
    public String comfirmHaveBug(JSONArray labels) throws JSONException {
        for (int i = 0; i < labels.size(); i++) {
            JSONObject oneLabel = labels.getJSONObject(i);
            if (oneLabel.getString("name").equals("bug")) {
                return "bug";
            }
        }
        return "todo";
    }






    public String addList(String boardId, String token, String key) throws IOException, JSONException {
        return trelloHttpClient.httpClientPostUilt(trelloHttpClient.dealUrl("ADDLIST", boardId, token, key, "", "", "", ""));
    }

    public String showList(String boardId, String token, String key) throws IOException, JSONException {
        return trelloHttpClient.httpClientGetUilt(trelloHttpClient.dealUrl("SHOWLIST", boardId, token, key, "", "", "", ""));
    }

    public String addCard(String cardName, String ListId, String token, String key, String desc) throws IOException, JSONException {
        return trelloHttpClient.httpClientPostUilt(trelloHttpClient.dealUrl("ADDCARD", "", token, key, cardName, ListId, "", desc));
    }

    public String addLabel(String token, String key, String cardId) throws IOException, JSONException {
        return trelloHttpClient.httpClientPostUilt(trelloHttpClient.dealUrl("ADDLABEL", "", token, key, "", "", cardId, ""));
    }
    public boolean deleteCard(String token, String key, String cardId) throws IOException, JSONException {
        JSONObject responseBody=JSONObject.fromObject(trelloHttpClient.httpClientDelete(trelloHttpClient.dealUrl("DELETECARD", "", token, key, "", "", cardId, "")));
        if(responseBody.getString("status").equals("200")){
            return true;
        }
        return false;
    }


    public boolean addExceptionCard(String boardId,String cardName, String desc) throws IOException, JSONException {
        String token = trello.getPrivateToken();
        String key = trello.getAppKey();
        JSONArray showListData =  JSONArray.fromObject(showList(boardId, token, key));
        boolean state = false;
        String listId = "";
        String cardId = "";
        for (int i = 0; i < showListData.size(); i++) {
            JSONObject oneList = showListData.getJSONObject(i);
            if (oneList.getString("name").equals("Dev In-Progress")) {
                state = true;
                listId = oneList.getString("id");
                break;
            }
        }
        if (!state) {
            JSONObject oneList = JSONObject.fromObject(addList(boardId, token, key));
            if (oneList.getString("id").equals("") || oneList.getString("id") == null) {
                return false;
            }
            listId = oneList.getString("id");
        }
        JSONObject newCard = JSONObject.fromObject(addCard(cardName, listId, token, key, desc));

        if (newCard.getString("id").equals("") || newCard.getString("id") == null) {
            return false;
        }
        cardId = newCard.getString("id");
        JSONObject addLabel = JSONObject.fromObject(addLabel(token, key, cardId));
        if (addLabel.getString("id").equals("") || addLabel.getString("id") == null) {
            return false;
        }
        return true;
    }
}
