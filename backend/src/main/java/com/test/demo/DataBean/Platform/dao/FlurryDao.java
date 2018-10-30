package com.test.demo.DataBean.Platform.dao;

import com.test.demo.DataBean.Platform.Interface.PlatformDao;
import com.test.demo.DataBean.Platform.bean.Flurry;
import com.test.demo.DataBean.Platform.Interface.Platforms;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FlurryDao implements PlatformDao {
    @Autowired
    private FlurryRepository flurryRepository;
    public Flurry byName(String name){
        return flurryRepository.findByName(name);
    }
    public Flurry saveAndUpdate(Platforms platforms){
        Flurry flurry = (Flurry) platforms;
        return flurryRepository.save(flurry);
    }

    @Override
    public Platforms platformFindAndUpdate(String name, JSONObject setting) {
        Flurry flurry = byName(name);
        if(flurry==null){
            flurry = new Flurry();
            flurry.setName(name);
        }
        JSONArray allProjects = setting.getJSONArray("allProjects");
        JSONArray chosenProject = setting.getJSONArray("chosenProjects");
        String privateToken = setting.getString("privateToken");
        flurry.setAllProjects(allProjects);
        flurry.setChosenProjects(chosenProject);
        flurry.setPrivateToken(privateToken);
        flurry.setInterval(setting.getInt("interval"));
        return saveAndUpdate(flurry);
    }
}
