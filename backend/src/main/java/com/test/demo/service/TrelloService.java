package com.test.demo.service;

import com.test.demo.User.service.UserService;
import com.test.demo.bean.platform.data.TrelloData;
import com.test.demo.common.httpHelper.StatusHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.text.ParseException;
@Service
public class TrelloService extends ApiService{
    private Logger logger = Logger.getLogger(TrelloService.class);
    @Autowired
    UserService userService;
    @Autowired
    TrelloData trelloData;
    private StatusHelper statusHelper = new StatusHelper();
    public JSONObject majorProjectsData(String body) throws IOException {
        JSONObject bodyObject = JSONObject.fromObject(body);
        String name = bodyObject.getString("name");
        String privateToken = bodyObject.getString("privateToken");
        String appKey = bodyObject.getString("appKey");
        return statusHelper.jsonObject2Success( statusHelper.boardNameAndBoardData(name, trelloData.comfirmBaseAuthorityAndGetCheckBoxData(privateToken, appKey)));
    }
    public JSONObject initCallApi(String body) throws IOException, ParseException {
        JSONObject bodyObject = JSONObject.fromObject(body);
        String name = bodyObject.getString("name");
        String privateToken = bodyObject.getString("privateToken");
        String appkey = bodyObject.getString("appKey");
        JSONArray allProjects = trelloData.comfirmBaseAuthorityAndGetCheckBoxData(privateToken, appkey);
        JSONArray chosenProjects = getIdArray(allProjects);
        JSONObject setting = new JSONObject();
        setting.element("privateToken", privateToken);
        setting.element("allProjects", allProjects);
        setting.element("chosenProjects", chosenProjects);
        setting.element("chosenProjects", chosenProjects);
        setting.element("appKey", appkey);
        setting.element("interval", initInterval);
        if(name.equals("")){
            setting.element("name", "Trello");
            userService.toSetting(adminAcount, setting.toString());
        }
        return statusHelper.onlySuccess();
    }
}
