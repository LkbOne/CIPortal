package com.test.demo.User.usersDao;

import com.test.demo.User.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepository extends MongoRepository<Users, String> {
    public Users findByAccount(String account);

}
