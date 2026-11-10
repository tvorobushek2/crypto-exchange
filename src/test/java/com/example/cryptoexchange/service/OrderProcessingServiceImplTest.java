package com.example.cryptoexchange.service;

import com.example.cryptoexchange.model.Order;
import com.example.cryptoexchange.model.OrderStatus;
import com.example.cryptoexchange.model.OrderType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OrderProcessingServiceImplTest {

    @Test
    public void testProcessOrdersMultithreading() {
        OrderProcessingService processingService = new OrderProcessingServiceImpl();

        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Order order = Order.builder()
                    .id((long) i)
                    .userId(1L)
                    .cryptoSymbol("BTC")
                    .amount(1.0)
                    .price(30000.0)
                    .type(OrderType.BUY)
                    .status(OrderStatus.PENDING)
                    .build();
            orders.add(order);
        }

        processingService.processOrders(orders);

        for (Order order : orders) {
            assertEquals(OrderStatus.FILLED, order.getStatus(),
                    "Заявка ID=" + order.getId() + " должна получить статус FILLED");
        }
    }
}