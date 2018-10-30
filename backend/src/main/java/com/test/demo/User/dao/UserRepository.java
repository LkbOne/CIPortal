package com.test.demo.User.dao;

import com.test.demo.bean.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface  UserRepository extends MongoRepository<User, String> {
    public User findByAccount(String account);

}
