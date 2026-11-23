package com.example.cryptoexchange.controller;

import com.example.cryptoexchange.dto.OrderDto;
import com.example.cryptoexchange.exception.InvalidOrderDataException;
import com.example.cryptoexchange.model.Order;
import com.example.cryptoexchange.model.OrderStatus;
import com.example.cryptoexchange.model.OrderType;
import org.springframework.stereotype.Component;

@Component
public class OrderRequestMapper {

    public Order toEntity(OrderDto dto) {
        if (dto == null) {
            throw new InvalidOrderDataException("Тело запроса отсутствует");
        }
        if (dto.getUserId() == null || dto.getUserId() <= 0) {
            throw new InvalidOrderDataException("Поле userId обязательно и должно быть положительным");
        }
        if (dto.getCryptoSymbol() == null || dto.getCryptoSymbol().trim().isEmpty()) {
            throw new InvalidOrderDataException("Поле cryptoSymbol обязательно");
        }
        if (dto.getAmount() == null || dto.getAmount() <= 0) {
            throw new InvalidOrderDataException("Поле amount должно быть больше нуля");
        }
        if (dto.getPrice() == null || dto.getPrice() <= 0) {
            throw new InvalidOrderDataException("Поле price должно быть больше нуля");
        }
        if (dto.getType() == null) {
            throw new InvalidOrderDataException("Поле type обязательно");
        }

        OrderType type;
        try {
            type = OrderType.valueOf(dto.getType().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidOrderDataException("Недопустимый тип заявки: " + dto.getType() + ". Допустимые: BUY, SELL");
        }

        OrderStatus status = OrderStatus.PENDING;
        if (dto.getStatus() != null && !dto.getStatus().isEmpty()) {
            try {
                status = OrderStatus.valueOf(dto.getStatus().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new InvalidOrderDataException("Недопустимый статус: " + dto.getStatus());
            }
        }

        return new Order(
                null,
                dto.getUserId(),
                dto.getCryptoSymbol(),
                dto.getAmount(),
                dto.getPrice(),
                type,
                status
        );
    }
}