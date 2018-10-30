package com.test.demo.DataBean.Platform.dao;

import com.test.demo.DataBean.Platform.Interface.PlatformRepository;
import com.test.demo.DataBean.Platform.bean.Flurry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FlurryRepository extends MongoRepository<Flurry, String> {
    Flurry findByName(String name);
}
