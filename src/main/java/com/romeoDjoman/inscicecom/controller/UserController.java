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

import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private UserService userService;

    @PostMapping(path = "register")
    public void register(@RequestBody User user){
        log.info("Register");
        this.userService.register(user);
    }

    @PostMapping(path = "activationCode")
    public void activationCode(@RequestBody Map<String, String> activationCode){
        this.userService.activationCode(activationCode);
    }

}