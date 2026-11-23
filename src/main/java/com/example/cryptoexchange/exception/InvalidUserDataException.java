package com.example.cryptoexchange.exception;

public class InvalidUserDataException extends RuntimeException {

    public InvalidUserDataException(String message) {
        super("Некорректные данные пользователя: " + message + ". Проверьте обязательные поля (username, email, password, role).");
    }
}