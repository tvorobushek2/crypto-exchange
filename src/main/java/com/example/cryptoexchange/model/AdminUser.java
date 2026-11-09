package com.example.cryptoexchange.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("ADMIN")
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class AdminUser extends User {

    public AdminUser(Long id, String username, String email, String password) {
        super.setId(id);
        super.setUsername(username);
        super.setEmail(email);
        super.setPassword(password);
        super.setRole(UserRole.ADMIN);
    }
}