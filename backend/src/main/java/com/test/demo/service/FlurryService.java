package com.test.demo.service;

import com.test.demo.User.service.UserService;
import com.test.demo.bean.platform.data.FlurryData;
import com.test.demo.common.httpHelper.StatusHelper;
import lombok.Getter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;

@Service
@Getter
public class FlurryService extends ApiService{
    private StatusHelper statusHelper = new StatusHelper();
    @Autowired
    FlurryData flurryData;
    @Autowired
    UserService userService;
    public JSONObject majorProjectsData(String body) throws IOException {
        JSONObject bodyObject = JSONObject.fromObject(body);
        String name = bodyObject.getString("name");
        String privateToken = bodyObject.getString("privateToken");
        return statusHelper.jsonObject2Success(statusHelper.boardNameAndBoardData(name, flurryData.comfirmBaseAuthorityAndGetCheckBoxData(privateToken)));
    }
    JSONArray getIdArray(JSONArray allProject){
        JSONArray array = new JSONArray();
        for(int i = 0; i < allProject.size(); i++){
            JSONObject object = allProject.getJSONObject(i);
            JSONArray apps = object.getJSONArray("apps");
            for(int j = 0; j < apps.size(); j++){
                JSONObject app = apps.getJSONObject(j);
                array.element(app.getString("id"));
            }
        }
        return array;
    }
    public JSONObject initCallApi(String body) throws IOException, ParseException {
        JSONObject bodyObject = JSONObject.fromObject(body);
        String name = bodyObject.getString("name");
        String privateToken = bodyObject.getString("privateToken");
        JSONArray allProjects = flurryData.comfirmBaseAuthorityAndGetCheckBoxData(privateToken);
        JSONArray chosenProjects = getIdArray(allProjects);
        JSONObject setting = new JSONObject();
        setting.element("privateToken", privateToken);
        setting.element("allProjects", allProjects);
        setting.element("chosenProjects", chosenProjects);
        setting.element("interval", initInterval);
        if(name.equals("")){
            setting.element("name","Event Logs");
            userService.toSetting(adminAcount,setting.toString());
        }
        return statusHelper.onlySuccess();
    }


}
