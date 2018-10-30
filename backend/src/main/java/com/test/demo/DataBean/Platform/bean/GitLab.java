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
public class GitLab implements Platforms {
    @Id
    private String id;
    private String name;
    private String privateToken;
    private String ip;
    private JSONArray chosenProjects;
    private JSONArray allProjects;
    private JSONObject setting =new JSONObject();
    private int interval;
    public JSONObject allSettingParam(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.element("privateToken",privateToken);
        jsonObject.element("ip",ip);
        jsonObject.element("interval",interval);
        return jsonObject;
    }

    public GitLab setAllPlatformsParams(JSONObject settings){
        JSONArray allProjects = settings.getJSONArray("allProjects");
        JSONArray chosenProject = settings.getJSONArray("chosenProjects");
        String privateToken = settings.getString("privateToken");
        String ip = settings.getString("ip");
        setAllProjects(allProjects);
        setChosenProjects(chosenProject);
        setPrivateToken(privateToken);
        setIp(ip);
        setInterval(settings.getInt("interval"));
        return this;
    }
    public void setHooksInSetting(JSONArray hooks){
        setting.element("hooks",hooks);
    }
    public JSONArray getHooksInSetting(){
        if(setting.has("hooks")){
            return setting.getJSONArray("hooks");
        }else{
            return new JSONArray();
        }
    }
    public void setImageInSetting(JSONArray projectImages){
        setting.element("projectImage",projectImages);
    }
    public JSONArray getImagesInSetting(){
        if(setting.has("projectImage")){
            return setting.getJSONArray("projectImage");
        }else{
            return new JSONArray();
        }
    }
}
