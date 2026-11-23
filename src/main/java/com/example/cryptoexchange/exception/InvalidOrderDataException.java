package com.example.cryptoexchange.exception;

public class InvalidOrderDataException extends RuntimeException {

    public InvalidOrderDataException(String message) {
        super("Некорректные данные заявки: " + message + ". Проверьте поля amount, price, cryptoSymbol, type.");
    }
}