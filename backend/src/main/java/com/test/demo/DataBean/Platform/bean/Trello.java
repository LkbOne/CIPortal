package com.test.demo.DataBean.Platform.bean;

import com.test.demo.DataBean.Platform.Interface.Platforms;
import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.persistence.Id;

@Component
@Setter
@Getter
public class Trello implements Platforms {
    @Id
    private String id;
    private String name;
    private String appKey;
    private String privateToken;
    private JSONArray chosenProjects;
    private JSONArray allProjects;
    private JSONObject setting;
    private int interval;
    public JSONObject allSettingParam(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.element("appKey", appKey);
        jsonObject.element("privateToken", privateToken);
        jsonObject.element("interval", interval);
        return jsonObject;
    }
    public Trello setAllPlatformsParams(JSONObject settings) {
        JSONArray allProjects = settings.getJSONArray("allProjects");
        JSONArray chosenProject = settings.getJSONArray("chosenProjects");
        String privateToken = settings.getString("privateToken");
        String appKey = settings.getString("appKey");
        setAllProjects(allProjects);
        setChosenProjects(chosenProject);
        setPrivateToken(privateToken);
        setAppKey(appKey);
        setInterval(settings.getInt("interval"));
        return this;
    }

}
