package com.romeoDjoman.inscicecom.controller;

import com.romeoDjoman.inscicecom.entity.Opinion;
import com.romeoDjoman.inscicecom.entity.User;
import com.romeoDjoman.inscicecom.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    UserService userService;

    @PreAuthorize("hasAuthority('ADMINISTRATOR_VIEW_SUBMITTED_PUBLICATIONS')")
    @GetMapping
    public List<User> listOpinion() {
        return this.userService.listUser();
    }
}
