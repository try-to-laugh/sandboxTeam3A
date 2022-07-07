package com.exadel.sandboxteam3a.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    //example
    public List<User> findAllUsers(){
        return userDao.findAll();
    }

}
