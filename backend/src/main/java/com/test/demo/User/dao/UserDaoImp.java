package com.test.demo.User.dao;

import com.test.demo.bean.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class UserDaoImp implements UserDao{
    private Logger logger = Logger.getLogger(UserDaoImp.class);
    @Autowired
    private UserRepository userRepository;
    @Override
    @Transactional
    public User register(User user){
        User userSaved=userRepository.save(user);
        return userSaved;
    }
    @Transactional
    public User userByAccount(String account){
        User user = userRepository.findByAccount(account);
        return user;
    }
    @Transactional
    public User updateData(User user){
        User userUpdated=userRepository.save(user);
        return userUpdated;
    }
}
