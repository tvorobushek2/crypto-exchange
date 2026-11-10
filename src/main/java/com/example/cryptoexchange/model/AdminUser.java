package com.example.cryptoexchange.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class AdminUser extends User {

    public AdminUser() {
        super();
    }

    public AdminUser(Long id, String username, String email, String password) {
        super(id, username, email, password, UserRole.ADMIN);
    }
}