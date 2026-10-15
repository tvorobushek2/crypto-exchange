package com.example.cryptoexchange.service;

import com.example.cryptoexchange.model.Order;
import com.example.cryptoexchange.model.OrderType;
import java.util.List;
import java.util.Optional;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order addOrder(Order order);
    Optional<Order> getOrderById(Long id);
    List<Order> getAllOrders();
    List<Order> findOrdersBySymbol(String symbol);
    List<Order> findOrdersByType(OrderType type);
    boolean deleteOrder(Long id);
}