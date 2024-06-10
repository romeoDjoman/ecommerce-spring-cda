package com.romeoDjoman.inscicecom.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "review_publications")
public class ReviewPublication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewPublicationId;
    private String reviewText;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
