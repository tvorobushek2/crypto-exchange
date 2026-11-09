package com.example.cryptoexchange.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("CUSTOMER") // Значение в колонке dtype
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class CustomerUser extends User {
    private Double walletBalance;

    public CustomerUser(Long id, String username, String email, String password, Double walletBalance) {
        super.setId(id);
        super.setUsername(username);
        super.setEmail(email);
        super.setPassword(password);
        super.setRole(UserRole.CUSTOMER);
        this.walletBalance = walletBalance;
    }
}