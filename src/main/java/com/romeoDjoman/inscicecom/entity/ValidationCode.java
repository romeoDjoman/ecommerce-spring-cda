package com.romeoDjoman.inscicecom.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "validation_code")
@Entity
public class ValidationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int validationCodeId;
    private Instant creationDate;
    private Instant expirationDate;
    private Instant activationDate;
    private String code;
    @OneToOne(cascade = CascadeType.ALL)
    private User user;
}
