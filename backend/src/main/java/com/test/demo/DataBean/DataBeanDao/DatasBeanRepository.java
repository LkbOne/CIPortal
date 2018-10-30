package com.test.demo.DataBean.DataBeanDao;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DatasBeanRepository extends MongoRepository<DatasBean, String> {
    DatasBean findByName(String name);
}
