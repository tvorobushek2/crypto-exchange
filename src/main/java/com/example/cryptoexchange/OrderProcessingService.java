package com.example.cryptoexchange;

import java.util.List;

public interface OrderProcessingService {
    void processOrders(List<Order> orders);
}