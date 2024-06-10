package com.romeoDjoman.inscicecom.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "billing_addresses")
public class BillingAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int billingAddressId;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;

    @OneToOne(mappedBy = "billingAddress")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
