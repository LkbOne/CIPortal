package com.test.demo.DataBean.Platform.dao;

import com.test.demo.DataBean.Platform.Interface.PlatformDao;
import com.test.demo.DataBean.Platform.Interface.Platforms;
import com.test.demo.DataBean.Platform.bean.Sentry;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SentryDao implements PlatformDao {
    @Autowired
    SentryRepository sentryRepository;
    public Sentry byName(String name) {
        return sentryRepository.findByName(name);
    }
    public Sentry saveAndUpdate(Platforms platforms) {
        Sentry sentry = (Sentry) platforms;
        return sentryRepository.save(sentry);
    }
    public Sentry platformFindAndUpdate(String name, JSONObject setting){
        Sentry sentry = byName(name);
        if(sentry==null){
            sentry = new Sentry();
            sentry.setName(name);
        }
        sentry = sentry.setAllPlatformsParams(setting);
        saveAndUpdate(sentry);
        return sentry;
    }
}