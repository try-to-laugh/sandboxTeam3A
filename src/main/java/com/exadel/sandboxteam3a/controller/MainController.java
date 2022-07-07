package com.exadel.sandboxteam3a.controller;

import com.exadel.sandboxteam3a.dao.entity.User;
import com.exadel.sandboxteam3a.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController(value = "/budget")
public class MainController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService=userService;
    }
    

    @GetMapping("/hello")
    public String sayHello() {
        return "hello world";
    }
    
    @GetMapping
    public List<User> getAllUsers(){
        return userService.findAllUsers();
    }  

}
