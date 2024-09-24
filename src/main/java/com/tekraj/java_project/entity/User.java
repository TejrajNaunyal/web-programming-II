package com.tekraj.java_project.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use IDENTITY for auto-increment in most databases
    private Long id;

    @Column(nullable = false, unique = true) // Enforce unique usernames
    private String userName;

    @Column(nullable = false) // Password cannot be null
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // You can return roles/authorities here if needed
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Indicates that the account is not expired
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Indicates that the account is not locked
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Indicates that credentials are not expired
    }

    @Override
    public boolean isEnabled() {
        return true; // Indicates that the account is enabled
    }

    // Setter and getter methods
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserName'");
    }
}
