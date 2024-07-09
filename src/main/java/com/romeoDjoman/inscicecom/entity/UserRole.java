package com.romeoDjoman.inscicecom.entity;

import com.romeoDjoman.inscicecom.ennum.UserRoleType;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_role")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userRoleId;
    @Enumerated(EnumType.STRING)
    private UserRoleType roleName;
}
