package com.romeoDjoman.inscicecom.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "payment_cards")
public class PaymentCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentCardId;
    private String cardHolderName;
    private Date expirationDate;
    private String cvv;
    private String cardType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
