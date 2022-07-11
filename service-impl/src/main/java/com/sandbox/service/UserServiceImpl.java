package com.sandbox.service;

import com.sandbox.User;
import com.sandbox.dao.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserDao userDao;

    @Override
    public List<User> findAllUsers(){
        return userDao.findAll();
    }
}
