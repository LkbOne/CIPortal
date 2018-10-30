package com.test.demo.DataBean.Platform.dao;

import com.test.demo.DataBean.Platform.Interface.PlatformDao;
import com.test.demo.DataBean.Platform.Interface.Platforms;
import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class NullPlatformDao implements PlatformDao {
    @Override
    public Platforms byName(String name) {
        return null;
    }

    @Override
    public Platforms saveAndUpdate(Platforms platform) {
        return null;
    }

    @Override
    public Platforms platformFindAndUpdate(String name, JSONObject setting) {
        return null;
    }
}
