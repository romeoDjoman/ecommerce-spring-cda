package com.romeoDjoman.inscicecom.controller;

import com.romeoDjoman.inscicecom.dto.AuthenticationDTO;
import com.romeoDjoman.inscicecom.entity.User;
import com.romeoDjoman.inscicecom.security.JwtService;
import com.romeoDjoman.inscicecom.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtService jwtService;

    @PostMapping(path = "register")
    public void register(@RequestBody User user){
        log.info("Register");
        this.userService.register(user);
    }

    @PostMapping(path = "modify-password")
    public void modifyPassword(@RequestBody Map<String, String> parameters) {
        this.userService.modifyPassword(parameters);
    }

    @PostMapping(path = "new-password")
    public void newPassword(@RequestBody Map<String, String> parameters) {
        this.userService.newPassword(parameters);
    }


    @PostMapping(path = "activationCode")
    public void activationCode(@RequestBody Map<String, String> activationCode){
        this.userService.activationCode(activationCode);
    }

    @PostMapping(path = "log-out")
    public void logout(){
        this.jwtService.logout();
    }

    @PostMapping(path = "login")
    public Map<String,String> login(@RequestBody AuthenticationDTO authenticationDTO){
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password())
        );

        if(authentication.isAuthenticated()){
            return this.jwtService.generate(authenticationDTO.username());
        }
        return null;
    }


}
