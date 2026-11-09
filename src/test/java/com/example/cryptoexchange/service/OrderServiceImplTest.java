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

@SpringBootTest // Загружает контекст Spring для теста
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderService; // Реальный сервис

    @MockBean
    private OrderRepository orderRepository; // Поддельный (mock) репозиторий

    @Test
    public void testAddOrder() {
        // 1. Подготовка данных
        Order order = new Order(1L, 1L, "BTC", 1.5, 30000.0, OrderType.BUY, OrderStatus.PENDING);
        
        // 2. Настройка поведения заглушки: "Когда вызовут save, верни этот же order"
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // 3. Выполнение тестируемого метода
        Order savedOrder = orderService.addOrder(order);

        // 4. Проверка результатов
        assertNotNull(savedOrder.getId());
        assertEquals("BTC", savedOrder.getCryptoSymbol());
        
        // 5. Проверка, что метод save был вызван ровно 1 раз
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void testFindOrdersBySymbol() {
        Order order1 = new Order(1L, 1L, "BTC", 1.0, 30000.0, OrderType.BUY, OrderStatus.PENDING);
        
        when(orderRepository.findByCryptoSymbolIgnoreCase("BTC")).thenReturn(Arrays.asList(order1));

        List<Order> btcOrders = orderService.findOrdersBySymbol("BTC");
        
        assertEquals(1, btcOrders.size());
        assertEquals("BTC", btcOrders.get(0).getCryptoSymbol());
        verify(orderRepository, times(1)).findByCryptoSymbolIgnoreCase("BTC");
    }
}