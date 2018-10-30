package com.test.demo.DataBean.Platform.dao;

import com.test.demo.DataBean.Platform.Interface.PlatformRepository;
import com.test.demo.DataBean.Platform.bean.Trello;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TrelloRepository extends MongoRepository<Trello, String> {
    Trello findByName(String name);
}
