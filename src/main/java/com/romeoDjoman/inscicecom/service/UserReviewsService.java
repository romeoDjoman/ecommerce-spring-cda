package com.romeoDjoman.inscicecom.service;

import com.romeoDjoman.inscicecom.entity.UserReviews;
import com.romeoDjoman.inscicecom.repository.UserReviewsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserReviewsService {

    private final UserReviewsRepository userReviewsRepository;

    public void createUserReviews(UserReviews userReviews) {
        this.userReviewsRepository.save(userReviews);
    }
}
