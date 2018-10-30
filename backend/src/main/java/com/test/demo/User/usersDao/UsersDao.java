package com.test.demo.User.usersDao;

import com.test.demo.User.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class UsersDao {
    @Autowired
    private UsersRepository usersRepository;
    @Transactional
    public Users userByAccount(String account){
        Users user = usersRepository.findByAccount(account);
        return user;
    }
    @Transactional
    public Users updateData(Users user){
        Users userUpdated=usersRepository.save(user);
        return userUpdated;
    }
    public Users register(Users user){
        Users userSaved=usersRepository.save(user);
        return userSaved;
    }
}
