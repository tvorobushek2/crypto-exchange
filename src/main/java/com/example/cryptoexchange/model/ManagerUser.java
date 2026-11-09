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

    public ManagerUser(Long id, String username, String email, String password, String department) {
        super.setId(id);
        super.setUsername(username);
        super.setEmail(email);
        super.setPassword(password);
        super.setRole(UserRole.MANAGER);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}