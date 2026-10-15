package com.example.cryptoexchange.service;

import com.example.cryptoexchange.model.Order;
import com.example.cryptoexchange.model.OrderStatus;
import com.example.cryptoexchange.model.OrderType;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderProcessingServiceImplTest {

    @Test
    public void testProcessOrdersMultithreading() {
        OrderProcessingService processingService = new OrderProcessingServiceImpl();
        
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Order order = new Order((long) i, 1L, "BTC", 1.0, 30000.0, OrderType.BUY, OrderStatus.PENDING);
            orders.add(order);
        }

        processingService.processOrders(orders);

        for (Order order : orders) {
            assertEquals(OrderStatus.FILLED, order.getStatus(), "Заявка должна быть обработана и получить статус FILLED");
        }
    }
}