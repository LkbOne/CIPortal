package com.test.demo.DataBean.Platform.dao;

import com.test.demo.DataBean.Platform.Interface.PlatformDao;
import com.test.demo.DataBean.Platform.Interface.Platforms;
import com.test.demo.DataBean.Platform.bean.Sonar;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SonarDao implements PlatformDao {
    @Autowired
    private SonarRepository sonarRepository;
    public Sonar byName(String name) {
        return sonarRepository.findByName(name);
    }
    public Sonar saveAndUpdate(Platforms platforms) {
        Sonar sonar = (Sonar) platforms;
        return sonarRepository.save(sonar);
    }


    public Platforms platformFindAndUpdate(String name, JSONObject setting) {
        Sonar sonar =byName(name);
        if(sonar==null){
            sonar = new Sonar();
            sonar.setName(name);
        }
        sonar = saveAndUpdate(sonar.setAllPlatformsParams(setting));
        return sonar;
    }

}
