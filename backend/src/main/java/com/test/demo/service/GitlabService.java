package com.test.demo.service;

import com.test.demo.User.dao.UserDaoImp;
import com.test.demo.User.service.UserService;
import com.test.demo.bean.User;
import com.test.demo.bean.platform.data.GitData;
import com.test.demo.common.httpHelper.GitlabPlatformHttpClientHelper;
import com.test.demo.common.httpHelper.StatusHelper;
import com.test.demo.common.socket.WebSockets;
import lombok.Getter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.text.ParseException;
@Service
@Getter
public class GitlabService extends ApiService{
    @Autowired
    UserDaoImp userDaoImp;
    @Autowired
    GitData gitData;
    private GitlabPlatformHttpClientHelper gitlabHttpClientHelper = new GitlabPlatformHttpClientHelper();
    @Autowired
    UserService userService;
    private Logger logger = Logger.getLogger(GitlabService.class);
    private StatusHelper statusHelper = new StatusHelper();
    public JSONObject majorProjectsData(String body) throws IOException {
        JSONObject bodyObject = JSONObject.fromObject(body);
        String name = bodyObject.getString("name");
        String privateToken = bodyObject.getString("privateToken");
        String ip = bodyObject.getString("ip");
        JSONObject object= statusHelper.jsonObject2Success(statusHelper.boardNameAndBoardData(name, gitData.comfirmBaseAuthorityAndGetCheckBoxData(ip, privateToken)));
        return object;
    }
    public JSONObject initCallApi(String body) throws IOException, ParseException {
        JSONObject bodyObject = JSONObject.fromObject(body);
        String name = bodyObject.getString("name");
        String privateToken = bodyObject.getString("privateToken");
        String ip = bodyObject.getString("ip");
        JSONArray allProjects = gitData.comfirmBaseAuthorityAndGetCheckBoxData(ip, privateToken);
        JSONArray chosenProjects = getIdArray(allProjects);
        JSONObject setting = new JSONObject();
        setting.element("privateToken", privateToken);
        setting.element("ip", ip);
        setting.element("allProjects", allProjects);
        setting.element("chosenProjects", chosenProjects);
        setting.element("interval", initInterval);
        if(name.equals("")){
            setting.element("name", "Merge Request");
            userService.toSetting(adminAcount, setting.toString());
            setting.element("name", "CI Testing");
            userService.toSetting(adminAcount, setting.toString());
        }
        return statusHelper.onlySuccess();
    }
    public void sendAndComfirm(String account, String data) throws ParseException, IOException {
        User user = userDaoImp.userByAccount(account);
        JSONObject jsonObject = gitData.dataOfSendMessage(data);
        if(jsonObject.size() != 0) {
            JSONObject object = statusHelper.addBugStatusForArray(comfirmNew(user, jsonObject));
            WebSockets.sendMessageAll(object.toString());
        }
    }
    public JSONArray comfirmNew(User user, JSONObject jsonObject){
        JSONArray jsonArray = new JSONArray();
        if(jsonObject.getString("eventType").equals("mergeRequest")) {
            jsonArray = user.getMergeRequestData();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject aObject = jsonArray.getJSONObject(i);
                if (aObject.getString("mergeRequestId").equals(jsonObject.getString("mergeRequestId")) && !aObject.getString("mergeStatus").equals(jsonObject.getString("mergeStatus"))) {
                    jsonArray.element(i, jsonObject);
                    break;
                }
            }
        }else if(jsonObject.getString("eventType").equals("pipeline")){
            jsonArray = user.getCIData();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject aObject = jsonArray.getJSONObject(i);
                if (aObject.getString("pipelineId").equals(jsonObject.getString("pipelineId")) && !aObject.getString("status").equals(jsonObject.getString("status"))) {
                    jsonArray.element(i, jsonObject);
                    break;
                }
            }
        }
        return jsonArray;
    }
}
