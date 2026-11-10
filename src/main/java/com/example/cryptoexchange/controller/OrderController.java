package com.example.cryptoexchange.controller;

import com.example.cryptoexchange.dto.OrderDto;
import com.example.cryptoexchange.model.Order;
import com.example.cryptoexchange.model.OrderType;
import com.example.cryptoexchange.model.OrderStatus;
import com.example.cryptoexchange.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderDto dto) {
        Order order = new Order(
                null,
                dto.getUserId(),
                dto.getCryptoSymbol(),
                dto.getAmount(),
                dto.getPrice(),
                OrderType.valueOf(dto.getType().toUpperCase()),
                dto.getStatus() != null ? OrderStatus.valueOf(dto.getStatus().toUpperCase()) : OrderStatus.PENDING
        );
        Order saved = orderService.addOrder(order);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/symbol/{symbol}")
    public ResponseEntity<List<Order>> getOrdersBySymbol(@PathVariable String symbol) {
        return ResponseEntity.ok(orderService.findOrdersBySymbol(symbol));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Order>> getOrdersByType(@PathVariable String type) {
        OrderType orderType = OrderType.valueOf(type.toUpperCase());
        return ResponseEntity.ok(orderService.findOrdersByType(orderType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        if (orderService.deleteOrder(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}