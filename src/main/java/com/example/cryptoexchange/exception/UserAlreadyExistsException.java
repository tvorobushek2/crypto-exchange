package com.example.cryptoexchange.exception;

public class UserAlreadyExistsException extends RuntimeException {

    private final String email;

    public UserAlreadyExistsException(String email) {
        super("Пользователь с email '" + email + "' уже существует. Используйте другой email или выполните вход.");
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}