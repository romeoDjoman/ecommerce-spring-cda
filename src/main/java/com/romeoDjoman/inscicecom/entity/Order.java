package com.romeoDjoman.inscicecom.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    private String orderNumber;
    private Date creationDate;

    @OneToOne
    private Cart cart;

    @OneToOne
    private PaymentCard paymentCard;

    @OneToOne
    private BillingAddress billingAddress;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
