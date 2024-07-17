package com.romeoDjoman.inscicecom.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_table")
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    @Column(name = "password")
    private String password;
    private String phoneNumber;
    private boolean actif = false;


    @OneToOne(cascade = CascadeType.ALL)
    private UserRole userRole;

    // Manage role and permission by Admin
    private boolean publisherRequested = false;
    private boolean contributorRequested = false;
    private boolean publisherApproved = false;
    private boolean contributorApproved = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.userRole.getRoleName().getAuthorities();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.actif;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.actif;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.actif;
    }

    @Override
    public boolean isEnabled() {
        return this.actif;
    }
}
