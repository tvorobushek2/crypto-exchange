package com.example.cryptoexchange.model;

public class AdminUser extends User {
    public AdminUser(Long id, String username, String email, String password) {
        super(id, username, email, password, UserRole.ADMIN);
    }
}