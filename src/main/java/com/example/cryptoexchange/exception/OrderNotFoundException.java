package com.example.cryptoexchange.exception;

public class OrderNotFoundException extends RuntimeException {

    private final Long orderId;

    public OrderNotFoundException(Long orderId) {
        super("Заявка с ID " + orderId + " не найдена. Проверьте правильность ID.");
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }
}