package com.example.cryptoexchange.exception;

public class UserNotFoundException extends RuntimeException {

    private final Long userId;

    public UserNotFoundException(Long userId) {
        super("Пользователь с ID " + userId + " не найден. Проверьте правильность ID или создайте нового пользователя через POST /api/users.");
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}