package com.romeoDjoman.inscicecom.controller;


import com.romeoDjoman.inscicecom.entity.User;
import com.romeoDjoman.inscicecom.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private UserService userService;

    @PostMapping(path = "login")
    public void login(@RequestBody User user){
        log.info("Login");
        this.userService.login(user);
    }

}