package com.example.cryptoexchange.service;

import com.example.cryptoexchange.model.Order;
import com.example.cryptoexchange.model.OrderStatus;
import com.example.cryptoexchange.model.OrderType;
import com.example.cryptoexchange.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @Test
    public void testAddOrder() {
        Order order = Order.builder()
                .cryptoSymbol("BTC")
                .amount(1.5)
                .price(30000.0)
                .type(OrderType.BUY)
                .status(OrderStatus.PENDING)
                .build();

        Order saved = Order.builder()
                .id(1L)
                .cryptoSymbol("BTC")
                .amount(1.5)
                .price(30000.0)
                .type(OrderType.BUY)
                .status(OrderStatus.PENDING)
                .build();

        when(orderRepository.save(any(Order.class))).thenReturn(saved);

        Order result = orderService.addOrder(order);

        assertNotNull(result.getId());
        assertEquals("BTC", result.getCryptoSymbol());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void testGetOrderById() {
        Order order = Order.builder()
                .id(1L)
                .cryptoSymbol("BTC")
                .amount(1.5)
                .price(30000.0)
                .type(OrderType.BUY)
                .status(OrderStatus.PENDING)
                .build();

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Optional<Order> result = orderService.getOrderById(1L);

        assertTrue(result.isPresent());
        assertEquals("BTC", result.get().getCryptoSymbol());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindOrdersBySymbol() {
        Order order = Order.builder()
                .id(1L)
                .cryptoSymbol("BTC")
                .amount(1.0)
                .price(30000.0)
                .type(OrderType.BUY)
                .status(OrderStatus.PENDING)
                .build();

        when(orderRepository.findByCryptoSymbolIgnoreCase("BTC")).thenReturn(Arrays.asList(order));

        List<Order> btcOrders = orderService.findOrdersBySymbol("BTC");

        assertEquals(1, btcOrders.size());
        assertEquals("BTC", btcOrders.get(0).getCryptoSymbol());
        verify(orderRepository, times(1)).findByCryptoSymbolIgnoreCase("BTC");
    }

    @Test
    public void testFindOrdersByType() {
        Order order = Order.builder()
                .id(1L)
                .cryptoSymbol("BTC")
                .amount(1.0)
                .price(30000.0)
                .type(OrderType.BUY)
                .status(OrderStatus.PENDING)
                .build();

        when(orderRepository.findByType(OrderType.BUY)).thenReturn(Arrays.asList(order));

        List<Order> buyOrders = orderService.findOrdersByType(OrderType.BUY);

        assertEquals(1, buyOrders.size());
        assertEquals(OrderType.BUY, buyOrders.get(0).getType());
        verify(orderRepository, times(1)).findByType(OrderType.BUY);
    }

    @Test
    public void testDeleteOrder() {
        Long orderId = 1L;
        when(orderRepository.existsById(orderId)).thenReturn(true);
        doNothing().when(orderRepository).deleteById(orderId);

        boolean isDeleted = orderService.deleteOrder(orderId);

        assertTrue(isDeleted);
        verify(orderRepository, times(1)).deleteById(orderId);
    }

    @Test
    public void testDeleteOrderNotFound() {
        Long orderId = 999L;
        when(orderRepository.existsById(orderId)).thenReturn(false);

        boolean isDeleted = orderService.deleteOrder(orderId);

        assertFalse(isDeleted);
        verify(orderRepository, never()).deleteById(orderId);
    }
}