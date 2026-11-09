package com.example.cryptoexchange.controller;

import com.example.cryptoexchange.model.Order;
import com.example.cryptoexchange.model.OrderType;
import com.example.cryptoexchange.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController // Указывает, что это REST-контроллер (возвращает JSON, а не HTML)
@RequestMapping("/api/orders") // Базовый путь для всех методов в этом классе
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        // @RequestBody означает, что Order берется из JSON-тела запроса
        return ResponseEntity.ok(orderService.addOrder(order));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        // @PathVariable берет значение из URL (например, /api/orders/5)
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build()); // Возвращает 404, если не найдено
    }

    @GetMapping("/symbol/{symbol}")
    public ResponseEntity<List<Order>> getOrdersBySymbol(@PathVariable String symbol) {
        return ResponseEntity.ok(orderService.findOrdersBySymbol(symbol));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Order>> getOrdersByType(@PathVariable OrderType type) {
        return ResponseEntity.ok(orderService.findOrdersByType(type));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        if (orderService.deleteOrder(id)) {
            return ResponseEntity.noContent().build(); // Возвращает 204 No Content
        }
        return ResponseEntity.notFound().build();
    }
}