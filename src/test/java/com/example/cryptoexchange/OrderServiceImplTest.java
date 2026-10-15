package com.example.cryptoexchange;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceImplTest {

    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        orderService = new OrderServiceImpl();
    }

    @Test
    public void testAddAndFindOrder() {
        Order order = new Order(0L, 1L, "BTC", 1.5, 30000.0, OrderType.BUY, OrderStatus.PENDING);
        Order savedOrder = orderService.addOrder(order);
        
        assertNotNull(savedOrder.getId());
        Optional<Order> foundOrder = orderService.getOrderById(savedOrder.getId());
        assertTrue(foundOrder.isPresent());
        assertEquals("BTC", foundOrder.get().getCryptoSymbol());
    }

    @Test
    public void testFindOrdersBySymbol() {
        orderService.addOrder(new Order(0L, 1L, "BTC", 1.0, 30000.0, OrderType.BUY, OrderStatus.PENDING));
        orderService.addOrder(new Order(0L, 1L, "ETH", 10.0, 2000.0, OrderType.SELL, OrderStatus.PENDING));

        List<Order> btcOrders = orderService.findOrdersBySymbol("BTC");
        assertEquals(1, btcOrders.size());
        assertEquals("BTC", btcOrders.get(0).getCryptoSymbol());
    }

    @Test
    public void testDeleteOrder() {
        Order order = new Order(0L, 1L, "BTC", 1.0, 30000.0, OrderType.BUY, OrderStatus.PENDING);
        Order savedOrder = orderService.addOrder(order);
        
        boolean isDeleted = orderService.deleteOrder(savedOrder.getId());
        assertTrue(isDeleted);
        assertFalse(orderService.getOrderById(savedOrder.getId()).isPresent());
    }
}