package com.test.demo.service;

import com.test.demo.User.service.UserService;
import com.test.demo.bean.platform.data.SentryData;
import com.test.demo.common.httpHelper.SentryPlatformHttpClientHelper;
import com.test.demo.common.httpHelper.StatusHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;

@Service
public class SentryService extends ApiService{
    private SentryPlatformHttpClientHelper sentryHttpClientHelper = new SentryPlatformHttpClientHelper();
    private StatusHelper statusHelper = new StatusHelper();
    @Autowired
    UserService userService;
    @Autowired
    SentryData sentryData;
    public JSONObject majorProjectsData(String body) throws IOException {
        JSONObject bodyObject = JSONObject.fromObject(body);
        String name = bodyObject.getString("name");
        String privateToken = bodyObject.getString("privateToken");
        return statusHelper.jsonObject2Success(statusHelper.boardNameAndBoardData(name,sentryData.comfirmBaseAuthorityAndGetCheckBoxData(privateToken)));
    }
//    public boolean deleteIssue(String url,String authorization) throws IOException, JSONException {
//        JSONObject responseBody = JSONObject.fromObject(sentryHttpClientHelper.httpClientDeleteUilt(url,authorization));
//        if(responseBody.getString("status").equals("200")){
//            return true;
//        }
//        return false;
//    }
    public JSONObject initCallApi(String body) throws IOException, ParseException {
        JSONObject bodyObject = JSONObject.fromObject(body);
        String name = bodyObject.getString("name");
        String privateToken = bodyObject.getString("privateToken");
        JSONArray allProjects = sentryData.comfirmBaseAuthorityAndGetCheckBoxData(privateToken);
        JSONArray chosenProjects = getIdArray(allProjects);
        JSONObject setting = new JSONObject();
        setting.element("privateToken",privateToken);
        setting.element("allProjects",allProjects);
        setting.element("chosenProjects",chosenProjects);
        setting.element("interval",initInterval);
        if(name.equals("")){
            setting.element("name","Sentry");
            userService.toSetting(adminAcount,setting.toString());

        }
        return statusHelper.onlySuccess();
    }
    public JSONObject dispelBug(){
        return sentryData.dispelBug();
    }

}
