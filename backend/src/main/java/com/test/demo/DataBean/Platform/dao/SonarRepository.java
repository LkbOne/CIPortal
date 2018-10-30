package com.test.demo.DataBean.Platform.dao;

import com.test.demo.DataBean.Platform.Interface.PlatformRepository;
import com.test.demo.DataBean.Platform.bean.Sonar;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SonarRepository  extends MongoRepository<Sonar, String> {
    Sonar findByName(String name);
}
