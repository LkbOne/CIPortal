package com.test.demo.User.service;


import com.test.demo.DataBean.DataBeanDao.*;
import com.test.demo.DataBean.Platform.PlatformFactory;
import com.test.demo.DataBean.Platform.bean.*;
import com.test.demo.DataBean.Platform.dao.*;
import com.test.demo.User.Users;
import com.test.demo.User.dao.UserDaoImp;
import com.test.demo.User.usersDao.UsersDao;
import com.test.demo.bean.User;
import com.test.demo.bean.platform.data.*;
import com.test.demo.common.exception.ApiHttpCodeException;
import com.test.demo.common.httpHelper.StatusHelper;
import com.test.demo.common.thread.IntervalSubject;
import lombok.Getter;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;

@Service
@Getter
@Component
public class UserService {
    @Autowired
    private UserDaoImp userDaoImp;
    @Autowired
    private UsersDao usersDao;
    @Autowired
    DatasBeanDao datasBeanDao;
    @Autowired
    private IntervalSubject intervalSubject;
    @Autowired
    private PlatformFactory platformFactory;
    @Autowired
    FlurryDao flurryDao;
    @Autowired
    TrelloDao trelloDao;
    @Autowired
    SonarDao sonarDao;
    @Autowired
    SentryDao sentryDao;
    @Autowired
    GitLabDao gitLabDao;

    private Logger logger = Logger.getLogger(UserService.class);
    private StatusHelper statusHelper=new StatusHelper();
    private final String STATUS = "status";
    private final String FAIL = "fail";
    private final String adminAccount ="admin";
    public boolean usersByAccount(String account) throws JSONException {
        Users users= usersDao.userByAccount(account);
        if(users!=null){
            return false;
        }
        return true;
    }

    public JSONObject changePassword(Users user){
        String account = user.getAccount();
        String password = user.getPassword();
        Users users= usersDao.userByAccount(account);
        if(users!=null){
            users.setPassword(password);
            usersDao.updateData(users);
            return statusHelper.onlySuccess();
        }
        return statusHelper.onlyFail();
    }
    public JSONArray settingForChosenConfig(JSONObject setting) throws IOException, ParseException {
        Data data = platformFactory.dataFactoryMethod(setting.getString("name"));
        JSONArray returnArray = data.changeParams(setting);
        intervalSubject.registerOberver(data);
        intervalSubject.removeOberver(data);
        return returnArray;
    }
    public boolean settingForChosenBoard(String account, JSONObject setting){
        String boardName = setting.getString("name");
        JSONArray chosenProjects = setting.getJSONArray("chosenProjects");
        JSONArray allProjects = setting.getJSONArray("allProjects");
        JSONObject nowUsersChosenBoards = statusHelper.boardNameAndBoardData(boardName,chosenProjects);
        JSONObject nowUsersAllBoards = statusHelper.boardNameAndBoardData(boardName,allProjects);
        Users users = usersDao.userByAccount(account);
        JSONArray usersChosenBoards = users.getChosenProjects();
        JSONArray usersAllBoards = users.getAllProjects();
        users.setChosenProjects(removeAndAddChosenBoard(boardName,usersChosenBoards,nowUsersChosenBoards));
        users.setAllProjects(removeAndAddChosenBoard(boardName,usersAllBoards,nowUsersAllBoards));
        usersDao.updateData(users);
        return true;
    }
    public JSONArray removeAndAddChosenBoard(String name,JSONArray array,JSONObject nowChosenBoard){
        for(int i=0;i<array.size();i++){
            JSONObject object = array.getJSONObject(i);
            String boardName;
            if(object.has("name")) {
                boardName = object.getString("name");
            }else{
                boardName = object.getString("boardName");
            }
            if(boardName.equals(name)){
                array.remove(i);
                array.element(nowChosenBoard);
                return array;
            }
        }
        array.element(nowChosenBoard);
        return array;
    }
    public JSONObject toSetting(String account, String settingString) throws IOException, ParseException {
        JSONObject setting = JSONObject.fromObject(settingString);
        if(account.equals(adminAccount)){
            JSONArray returnArray= settingForChosenConfig(setting);
            if(settingForChosenBoard(account,setting)){
                return statusHelper.jsonArray2Success(returnArray);
            }
        }else{
            settingForChosenBoard(account,setting);
            return statusHelper.jsonArray2Success(filterOneByOneConfigChosenProject(setting.getString("name"),setting.getJSONArray("chosenProjects")));
        }
        return statusHelper.onlyFail();
    }

    public JSONArray dealBoardChoice(JSONArray boardChoice){
        int k=0;
        JSONArray array = new JSONArray();
        while(k<boardChoice.size()) {
            for (int i = 0; i < boardChoice.size(); i++) {
                JSONObject object = boardChoice.getJSONObject(i);
                if(object.getInt("x")==0&&object.getInt("y")==0&&k==0){
                    array.element(object);
                    k++;
                }
                if(object.getInt("x")==6&&object.getInt("y")==0&&k==1){
                    array.element(object);
                    k++;
                }
                if(object.getInt("x")==0&&object.getInt("y")==12&&k==2){
                    array.element(object);
                    k++;
                }
                if(object.getInt("x")==6&&object.getInt("y")==12&&k==3){
                    array.element(object);
                    k++;
                }
            }
        }
        return array;
    }

    public JSONObject individuate(String account, JSONArray boardChoice){
        Logger.getLogger("account:"+account);
        Logger.getLogger("boardChoice:"+boardChoice);
        Users users = usersDao.userByAccount(account);
        boardChoice = dealBoardChoice(boardChoice);
        users.setBoardChoice(boardChoice);
        usersDao.updateData(users);
        return statusHelper.jsonArray2Success(boardChoice);
    }

    public JSONObject createSubAccount(Users users) throws JSONException {

        if(usersByAccount(users.getAccount())){
            if(!users.getAccount().equals(adminAccount)){
                Users adminUsers =usersDao.userByAccount(adminAccount);
                users.setBoardChoice(adminUsers.getBoardChoice());
                users.setChosenProjects(adminUsers.getChosenProjects());
            }
            Users userSaved= usersDao.register(users);
            if(!userSaved.getId().equals("")){
                return statusHelper.onlySuccess();
            }else{
                throw new ApiHttpCodeException(getSTATUS(),getFAIL());
            }
        }
        return statusHelper.onlyFail();
    }
    public JSONObject datasFromDB(String account){

        Users users;
        //frontend 发送给backend一个空格admin账户，实际上发送为一个'+'
        if(account.equals("+")){
            users = usersDao.userByAccount(adminAccount);
        }else{
            users = usersDao.userByAccount(account);
        }
        return fromAdminChosenData(users.getBoardChoice(),users.getChosenProjects(),users.getAuthority());
    }
    public JSONObject fromAdminChosenData(JSONArray boardChoice, JSONArray chosen, int authority){
        JSONObject returnObject;
        JSONArray returnArray = new JSONArray();
        for(int i=0;i<boardChoice.size();i++){
            JSONObject aBoardChoice= boardChoice.getJSONObject(i);
            String key = aBoardChoice.getString("i");
            DatasBean datasBean=datasBeanDao.datasBeanByName(key);
            if(datasBean==null)continue;
            JSONArray aa = JSONArray.fromObject(datasBean.getDatas());
            returnArray.element(filterBychosen(key,chosen,aa,authority));
        }
        returnObject = statusHelper.jsonArray2Success(returnArray);
        returnObject.element("boardChoice",boardChoice);
        return returnObject;
    }
    public JSONArray filterOneByOneConfigChosenProject(String key, JSONArray chosen){
        DatasBean datasBean=datasBeanDao.datasBeanByName(key);
        JSONArray boardData = new JSONArray();
        for(int i=0;i<chosen.size();i++){
            JSONArray datas = JSONArray.fromObject(datasBean.getDatas());
            for(int j=0;j<datas.size();j++){
                JSONObject aData = datas.getJSONObject(j);
                if(aData.getString("id").equals(chosen.getString(i))){
                    boardData.element(aData);
                }
            }
        }
        return boardData;
    }
    public JSONObject filterBychosen(String key, JSONArray chosen, JSONArray data, int authority){
        JSONObject returnObject = new JSONObject();
        JSONArray boardData = new JSONArray();
        returnObject.element("boardName",key);
        if(authority==0){
            return returnObject.element("boardData",data);
        }
        for(int i=0;i<chosen.size();i++){
            JSONObject achosen= chosen.getJSONObject(i);
            if(achosen.getString("name").equals(key)){
                JSONArray chosenBoard = achosen.getJSONArray("boardData");
                for(int j=0;j<data.size();j++){
                    JSONObject aData = data.getJSONObject(j);
                    for(int k=0;k<chosenBoard.size();k++){
                        if(chosenBoard.getString(k).equals(aData.getString("id"))){
                            boardData.element(aData);
                        }
                    }
                }
            }
        }
       return returnObject.element("boardData",boardData);
    }
    public JSONObject showAllParamInConfigWithoutChosenProject(String key){
        JSONArray allProject;
        JSONObject setting;
        switch (key){
            case "Sonar":
                Sonar sonar = sonarDao.byName(key);
                allProject = sonar.getAllProjects();
                setting = sonar.allSettingParam();
                break;
            case "Trello":
                Trello trello = trelloDao.byName(key);
                allProject = trello.getAllProjects();
                setting = trello.allSettingParam();
                break;
            case "Sentry":
                Sentry sentry = sentryDao.byName(key);
                allProject = sentry.getAllProjects();
                setting = sentry.allSettingParam();
                break;
            case "Event Logs":
            case "Geography DashBoard":
            case "Technical DashBoard":
                Flurry flurry = flurryDao.byName(key);
                allProject = flurry.getAllProjects();
                setting = flurry.allSettingParam();
                break;
            case "Merge Request":
            case "CI Testing":
                GitLab gitLab = gitLabDao.byName(key);
                allProject = gitLab.getAllProjects();
                setting = gitLab.allSettingParam();
                break;
            default:
                allProject = new JSONArray();
                setting = new JSONObject();
                break;
        }

        return setting.element("allProjects",allProject);
    }
    public JSONObject fixUsersParam(Users users){
        JSONObject usersObject = new JSONObject();
        usersObject.element("account",users.getAccount());
        usersObject.element("authority",users.getAuthority());
        usersObject.element("boardChoice",users.getBoardChoice());
        usersObject.element("boardSetting", loginAnd2ShowChosenInConfig(users.getChosenProjects(),users.getAuthority()));
        usersObject.element("boardMenu",boardMenu(users.getAuthority()));
        return usersObject;
    }
    public JSONArray boardMenu(int authority){
        if(authority!=0){
            Users adminUser =  usersDao.userByAccount(adminAccount);
            JSONArray allProject= adminUser.getAllProjects();
            JSONArray array = new JSONArray();
            for(int i=0;i<allProject.size();i++){
                JSONObject object = allProject.getJSONObject(i);
                array.element(object.getString("name"));
            }
            return array;
        }else{
            return JSONArray.fromObject(IntervalSubject.datas);
        }

    }
    public JSONObject usersLogin(Users users){
        Users userLogin= usersDao.userByAccount(users.getAccount());
        if(userLogin!=null&&users.getPassword().equals(userLogin.getPassword())) {
            JSONObject object =fixUsersParam(userLogin);
            return statusHelper.user2Success(object);
        }
        return statusHelper.onlyFail();
    }
    public JSONArray loginAnd2ShowChosenInConfig(JSONArray chosen, int authority){
        JSONArray returnArray = new JSONArray();
        if(authority==0){
            for(int i=0;i<chosen.size();i++){
                JSONObject object = chosen.getJSONObject(i);
                String key =object.getString("name");
                JSONObject objectSetting = showAllParamInConfigWithoutChosenProject(key);
                objectSetting.element("name",key);
                objectSetting.element("chosenProjects",object.getJSONArray("boardData"));
                returnArray.element(objectSetting);
            }
        }else {

            Users adminUsers = usersDao.userByAccount(adminAccount);
            JSONArray adminAllBoard = loginAnd2ShowChosenInConfig(adminUsers.getChosenProjects(), adminUsers.getAuthority());
            for(int i=0;i<adminAllBoard.size();i++){
                JSONObject aBoard = adminAllBoard.getJSONObject(i);
                for(int j=0;j<chosen.size();j++){
                    JSONObject subAccountChosenBoard = chosen.getJSONObject(j);
                    if(aBoard.getString("name").equals(subAccountChosenBoard.getString("name"))){
                        if(!aBoard.getString("name").equals("Event Logs")&&!aBoard.getString("name").equals("Technical DashBoard")&&!aBoard.getString("name").equals("Geography DashBoard")) {
                            returnArray.element(ordinarySubAccountChosenProjectDeal(aBoard,subAccountChosenBoard));
                        }else{
                            returnArray.element(flurrySubAccoutChosenProjectDeal(aBoard,subAccountChosenBoard));
                        }
                    }
                }
            }
        }
        return returnArray;
    }
   public JSONObject flurrySubAccoutChosenProjectDeal(JSONObject aBoard,JSONObject subAccountChosenBoard){
       JSONObject returnObject = new JSONObject();
       returnObject.element("name",aBoard.getString("name"));
       JSONArray projectIdForBoard = new JSONArray();
       JSONObject android = new JSONObject();
       android.element("platform","android");
       android.element("apps",new JSONArray());
       projectIdForBoard.element(android);

       JSONObject ios = new JSONObject();
       ios.element("platform","ios");
       ios.element("apps",new JSONArray());
       projectIdForBoard.element(ios);

       JSONObject mobileweb = new JSONObject();
       mobileweb.element("platform","mobileweb");
       mobileweb.element("apps",new JSONArray());

       projectIdForBoard.element(mobileweb);
       JSONArray aBoardJSONArray = aBoard.getJSONArray("chosenProjects");
       for(int k=0;k<aBoardJSONArray.size();k++){
           JSONArray allBoardJSONArray =aBoard.getJSONArray("allProjects");
           for(int m=0;m<aBoard.getJSONArray("allProjects").size();m++){
               JSONObject aProjectObject = allBoardJSONArray.getJSONObject(m);

               String projectId = aBoardJSONArray.getString(k);

               JSONArray apps= aProjectObject.getJSONArray("apps");
               String platform = aProjectObject.getString("platform");
               int ii = 0;
               for(;ii<projectIdForBoard.size();ii++){
                   if(projectIdForBoard.getJSONObject(ii).getString("platform").equals(platform)){
                       break;
                   }
               }
               for(int mm=0;mm<apps.size();mm++){
                   JSONObject aProject = apps.getJSONObject(mm);
                   if(aProject.getString("id").equals(projectId)){
                       projectIdForBoard.getJSONObject(ii).getJSONArray("apps").element(aProject);
                   }
               }
           }
       }
       returnObject.element("chosenProjects",subAccountChosenBoard.getJSONArray("boardData"));
       returnObject.element("allProjects",projectIdForBoard);
       return returnObject;
   }
   public JSONObject ordinarySubAccountChosenProjectDeal(JSONObject aBoard,JSONObject subAccountChosenBoard){
       JSONObject returnObject = new JSONObject();
       returnObject.element("name",aBoard.getString("name"));
       JSONArray projectIdForBoard = new JSONArray();
       JSONArray aBoardJSONArray = aBoard.getJSONArray("chosenProjects");
       for(int k=0;k<aBoardJSONArray.size();k++){
           JSONArray allBoardJSONArray =aBoard.getJSONArray("allProjects");
           for(int m=0;m<aBoard.getJSONArray("allProjects").size();m++){
               JSONObject aProjectObject = allBoardJSONArray.getJSONObject(m);
               if(aBoardJSONArray.getString(k).equals(aProjectObject.getString("id"))){
                   projectIdForBoard.element(aProjectObject);
               }


           }
       }
       returnObject.element("chosenProjects",subAccountChosenBoard.getJSONArray("boardData"));
       returnObject.element("allProjects",projectIdForBoard);
       return returnObject;
   }
   public JSONObject canCallPlatform(){
      String[][] platformAndboard={
              {"GitLab","CI Testing","Merge Request"},
              {"Trello","Trello"},
              {"Sentry","Sentry"},
              {"Sonar","Sonar"},
              {"Flurry","Technical DashBoard","Geography DashBoard","Event Logs"},
      };
      JSONArray selectPlatformForFrontend = new JSONArray();
      for(int i=0;i<platformAndboard.length;i++){
          JSONObject selectPlatform = new JSONObject();
          JSONArray selectBoard = new JSONArray();
          for(int j=1;j<platformAndboard[i].length;j++){
              if(platformFactory.dataFactoryMethod(platformAndboard[i][j]).getInterval()>0){
                  selectBoard.element(platformAndboard[i][j]);
              }
          }
          if(selectBoard.size()>0){
              selectPlatform.element("platform",platformAndboard[i][0]);
              selectPlatform.element("boardName",selectBoard);
              selectPlatformForFrontend.element(selectPlatform);
          }
      }
      logger.info("selectPlatformForFrontend:"+selectPlatformForFrontend);
      return statusHelper.jsonArray2Success(selectPlatformForFrontend);
   }
   public JSONObject selectBoard2BoardChoice(JSONArray array){

        JSONArray originBoardChoice = new JSONArray();
        originBoardChoice.element(JSONObject.fromObject("{\"x\" : 0.0, \"y\" : 0.0, \"w\" : 6.0,  \"h\" : 12.0, \"i\" : \"0\"}"));
        originBoardChoice.element(JSONObject.fromObject("{\"x\" : 6.0, \"y\" : 0.0, \"w\" : 6.0,  \"h\" : 12.0, \"i\" : \"00\"}"));
        originBoardChoice.element(JSONObject.fromObject("{\"x\" : 0.0, \"y\" : 12.0, \"w\" : 6.0,  \"h\" : 12.0, \"i\" : \"000\"}"));
        originBoardChoice.element(JSONObject.fromObject("{\"x\" : 6.0, \"y\" : 12.0, \"w\" : 6.0,  \"h\" : 12.0, \"i\" : \"0000\"}"));
        for(int i=0;i<array.size();i++){
            JSONObject object = originBoardChoice.getJSONObject(i);
            object.element("i",array.getString(i));
            originBoardChoice.element(i,object);
        }
        Users users = usersDao.userByAccount(adminAccount);
        users.setBoardChoice(originBoardChoice);
        usersDao.updateData(users);
        return statusHelper.jsonArray2Success(originBoardChoice);
   }
    public JSONObject changeEngineStatus(){
        IntervalSubject.engine = !IntervalSubject.engine;
        return statusHelper.onlySuccess();
    }
}
