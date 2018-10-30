package com.test.demo.DataBean.Platform.Interface;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlatformRepository extends MongoRepository<Platforms, String> {
    Platforms findByName(String name);
}
