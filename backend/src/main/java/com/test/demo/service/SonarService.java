package com.test.demo.service;


import com.test.demo.User.service.UserService;
import com.test.demo.bean.platform.data.SonarData;
import com.test.demo.common.httpHelper.StatusHelper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;

@Service
public class SonarService extends ApiService{
    private StatusHelper statusHelper = new StatusHelper();
    @Autowired
    UserService userService;
    @Autowired
    SonarData sonarData;
    public JSONObject majorProjectsData(String body) throws IOException {
        JSONObject bodyObject = JSONObject.fromObject(body);
        String name = bodyObject.getString("name");
        String host = bodyObject.getString("host");
        return statusHelper.jsonObject2Success(statusHelper.boardNameAndBoardData(name,sonarData.comfirmBaseAuthorityAndGetCheckBoxData(host)));
    }
    public JSONObject initCallApi(String body) throws IOException, ParseException {
        JSONObject bodyObject = JSONObject.fromObject(body);
        String name = bodyObject.getString("name");
        String host = bodyObject.getString("host");

        JSONArray allProjects = sonarData.comfirmBaseAuthorityAndGetCheckBoxData(host);
        JSONArray chosenProjects = getIdArray(allProjects);
        JSONObject setting = new JSONObject();
        setting.element("host",host);
        setting.element("allProjects",allProjects);
        setting.element("chosenProjects",chosenProjects);
        setting.element("interval",initInterval);
        if(name.equals("")){
            setting.element("name","Sonar");
            userService.toSetting(adminAcount,setting.toString());

        }
        return statusHelper.onlySuccess();
    }
}
