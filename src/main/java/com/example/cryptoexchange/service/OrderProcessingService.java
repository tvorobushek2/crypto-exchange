package com.example.cryptoexchange.service;

import com.example.cryptoexchange.model.Order;
import java.util.List;

import java.util.List;

public interface OrderProcessingService {
    void processOrders(List<Order> orders);
}