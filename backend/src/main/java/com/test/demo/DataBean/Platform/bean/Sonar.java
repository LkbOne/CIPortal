package com.test.demo.DataBean.Platform.bean;

import com.test.demo.DataBean.Platform.Interface.Platforms;
import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.persistence.Id;

@Component
@Getter
@Setter
public class Sonar implements Platforms {
    @Id
    private String id;
    private String name;
    private String host;
    private JSONArray chosenProjects;
    private JSONArray allProjects;
    private JSONObject setting;
    private int interval;
    public JSONObject allSettingParam(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.element("host",host);
        jsonObject.element("interval",interval);
        return jsonObject;
    }
    public JSONArray getSonarTrelloMapping(){
        if(setting.has("sonarTrelloMapping")){
            return setting.getJSONArray("sonarTrelloMapping");
        }
        return new JSONArray();
    }

    public Sonar setAllPlatformsParams(JSONObject settings) {
        JSONArray allProjects = settings.getJSONArray("allProjects");
        JSONArray chosenProject = settings.getJSONArray("chosenProjects");
        String host = settings.getString("host");
        setAllProjects(allProjects);
        setChosenProjects(chosenProject);
        setHost(host);
        setInterval(settings.getInt("interval"));
        return this;
    }
}
