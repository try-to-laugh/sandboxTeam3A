package com.sandbox.rest;

import com.sandbox.User;
import com.sandbox.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/budget")
@RequiredArgsConstructor
public class MainController {

  private final UserService userService;

    @GetMapping("/hello")
    public String sayHello() {
        return "hello world";
    }

       @GetMapping
       public List<User> getAllUsers(){
        return userService.findAllUsers();
    }
}
