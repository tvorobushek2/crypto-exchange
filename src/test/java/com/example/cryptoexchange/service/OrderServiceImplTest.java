package com.example.cryptoexchange.service;

import com.example.cryptoexchange.model.Order;
import com.example.cryptoexchange.model.OrderStatus;
import com.example.cryptoexchange.model.OrderType;
import com.example.cryptoexchange.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddOrder() {
        // Подготовка
        Order order = new Order(null, 1L, "BTC", 1.5, 30000.0, OrderType.BUY, OrderStatus.PENDING);
        Order savedOrder = new Order(1L, 1L, "BTC", 1.5, 30000.0, OrderType.BUY, OrderStatus.PENDING);
        
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        // Выполнение
        Order result = orderService.addOrder(order);

        // Проверка
        assertNotNull(result.getId());
        assertEquals(1L, result.getId());
        assertEquals("BTC", result.getCryptoSymbol());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void testGetOrderById() {
        Order order = new Order(1L, 1L, "BTC", 1.5, 30000.0, OrderType.BUY, OrderStatus.PENDING);
        
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Optional<Order> result = orderService.getOrderById(1L);

        assertTrue(result.isPresent());
        assertEquals("BTC", result.get().getCryptoSymbol());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindOrdersBySymbol() {
        Order order1 = new Order(1L, 1L, "BTC", 1.0, 30000.0, OrderType.BUY, OrderStatus.PENDING);
        Order order2 = new Order(2L, 1L, "ETH", 10.0, 2000.0, OrderType.SELL, OrderStatus.PENDING);
        
        when(orderRepository.findByCryptoSymbolIgnoreCase("BTC")).thenReturn(Arrays.asList(order1));

        List<Order> btcOrders = orderService.findOrdersBySymbol("BTC");
        
        assertEquals(1, btcOrders.size());
        assertEquals("BTC", btcOrders.get(0).getCryptoSymbol());
        verify(orderRepository, times(1)).findByCryptoSymbolIgnoreCase("BTC");
    }

    @Test
    public void testFindOrdersByType() {
        Order order1 = new Order(1L, 1L, "BTC", 1.0, 30000.0, OrderType.BUY, OrderStatus.PENDING);
        Order order2 = new Order(2L, 1L, "ETH", 10.0, 2000.0, OrderType.SELL, OrderStatus.PENDING);
        
        when(orderRepository.findByType(OrderType.BUY)).thenReturn(Arrays.asList(order1));

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