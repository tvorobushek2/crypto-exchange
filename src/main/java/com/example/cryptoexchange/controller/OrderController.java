package com.example.cryptoexchange.controller;

import com.example.cryptoexchange.dto.OrderDto;
import com.example.cryptoexchange.model.Order;
import com.example.cryptoexchange.model.OrderStatus;
import com.example.cryptoexchange.model.OrderType;
import com.example.cryptoexchange.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderRequestMapper orderMapper;

    public OrderController(OrderService orderService, OrderRequestMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderDto dto) {
        Order order = orderMapper.toEntity(dto);
        return ResponseEntity.ok(orderService.addOrder(order));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody OrderDto dto) {
        Order order = orderMapper.toEntity(dto);
        return ResponseEntity.ok(orderService.updateOrder(id, order));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderOrThrow(id));
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
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}