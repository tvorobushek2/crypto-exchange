package com.example.cryptoexchange.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("MANAGER")
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class ManagerUser extends User {

    private String department;

    public ManagerUser(String username, String email, String password, String department) {
        super.setUsername(username);
        super.setEmail(email);
        super.setPassword(password);
        super.setRole(UserRole.MANAGER);
        this.department = department;
    }
}