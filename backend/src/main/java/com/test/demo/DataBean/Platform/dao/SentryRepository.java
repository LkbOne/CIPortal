package com.test.demo.DataBean.Platform.dao;
import com.test.demo.DataBean.Platform.Interface.PlatformRepository;
import com.test.demo.DataBean.Platform.bean.Sentry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SentryRepository extends MongoRepository<Sentry, String> {
    Sentry findByName(String name);
}
