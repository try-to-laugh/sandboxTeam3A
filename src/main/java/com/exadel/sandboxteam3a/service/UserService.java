package com.exadel.sandboxteam3a.service;

import com.exadel.sandboxteam3a.dao.UserDao;
import com.exadel.sandboxteam3a.dao.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    //example
    public List<User> findAllUsers(){
        return userDao.findAll();
    }

}
