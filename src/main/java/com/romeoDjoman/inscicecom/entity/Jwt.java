package com.romeoDjoman.inscicecom.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="jwt")
public class Jwt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String value;
    private boolean desactive;
    private boolean expire;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
