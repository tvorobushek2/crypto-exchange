package com.example.cryptoexchange.exception;

public class InsufficientBalanceException extends RuntimeException {

    private final Long userId;
    private final Double required;
    private final Double available;

    public InsufficientBalanceException(Long userId, Double required, Double available) {
        super("Недостаточно средств на кошельке пользователя " + userId +
                ". Требуется: " + required + ", доступно: " + available +
                ". Пополните баланс через PUT /api/users/" + userId + "/balance.");
        this.userId = userId;
        this.required = required;
        this.available = available;
    }

    public Long getUserId() { return userId; }
    public Double getRequired() { return required; }
    public Double getAvailable() { return available; }
}