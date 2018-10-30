package com.test.demo.DataBean.Platform.Interface;

import com.test.demo.DataBean.Platform.bean.Sentry;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatformDao {
    @Autowired
    PlatformRepository platformRepository = null;
    Platforms byName(String name);
    Platforms saveAndUpdate(Platforms platform);
    Platforms platformFindAndUpdate(String name, JSONObject setting);
}
