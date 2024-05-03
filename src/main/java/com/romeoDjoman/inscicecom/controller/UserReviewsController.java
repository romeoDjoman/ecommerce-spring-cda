package com.romeoDjoman.inscicecom.controller;

import com.romeoDjoman.inscicecom.entity.UserReviews;
import com.romeoDjoman.inscicecom.service.UserReviewsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/userReviews")
public class UserReviewsController {

    private final UserReviewsService userReviewsService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createUserReviews(@RequestBody UserReviews userReviews) {
        this.userReviewsService.createUserReviews(userReviews);
    }
}
