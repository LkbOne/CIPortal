package com.test.demo.DataBean.Platform.bean;

import com.test.demo.DataBean.Platform.Interface.Platforms;
import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.persistence.Id;
import java.util.HashMap;

@Component
@Getter
@Setter
public class Sentry implements Platforms {
    @Id
    private String id;
    private String name;
    private String privateToken;
    private JSONArray chosenProjects;
    private JSONArray sortProjects;
    private JSONArray allProjects;
    private JSONObject setting = new JSONObject();
    private int interval;
    public JSONObject allSettingParam(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.element("privateToken", privateToken);
        jsonObject.element("interval", interval);
        jsonObject.element("sentryTrelloMapping", getSentryTrelloMapping());
        jsonObject.element("sentryGitLabMapping", getSentryGitLabMapping());
        return jsonObject;
    }
    public boolean getBugs(){
        if(setting.has("bug")){
           return setting.getBoolean("bug");
        }
        return false;
    }
    public void setBugs(boolean flag){
        setting.element("bug", flag);
    }
    public Sentry setAllPlatformsParams(JSONObject settings){
        JSONArray allProjects = settings.getJSONArray("allProjects");
        JSONArray chosenProject = settings.getJSONArray("chosenProjects");
        String privateToken = settings.getString("privateToken");
        setAllProjects(allProjects);
        setChosenProjects(chosenProject);
        setPrivateToken(privateToken);
        setInterval(settings.getInt("interval"));
        return this;
    }
    public void setSentryGitLabMapping(JSONArray sentryGitLabMapping){
         setting.element("sentryGitLabMapping", sentryGitLabMapping);
    }
    public JSONArray getSentryGitLabMapping(){
        if(setting.has("sentryGitLabMapping")){
            return setting.getJSONArray("sentryGitLabMapping");

        }
        return new JSONArray();
    }
    public void setSentryGitLabHashMap(HashMap sentryGitLabHashMap){
        setting.element("sentryGitLabHashMap", sentryGitLabHashMap);
    }
    public JSONObject getSentryGitLabHashMap(){
        if(setting.has("sentryGitLabHashMap")){
            return  setting.getJSONObject("sentryGitLabHashMap");
        }
        return new JSONObject();
    }


    public JSONObject getSentryTrelloHashMap(){
        if(setting.has("sentryTrelloHashMap")){
            setting.getJSONObject("sentryTrelloHashMap");
        }
        return new JSONObject();
    }
    public void setSentryTrelloHashMap(HashMap sentryTrelloHashMap){
        setting.element("sentryTrelloHashMap", sentryTrelloHashMap);
    }

    public JSONArray getSentryTrelloMapping(){
        if(setting.has("sentryTrelloMapping")){
            return setting.getJSONArray("sentryTrelloMapping");
        }
        return new JSONArray();
    }
    public void setSentryTrelloMapping(JSONArray sentryTrelloMapping){
        setting.element("sentryTrelloMapping", sentryTrelloMapping);
    }
}
