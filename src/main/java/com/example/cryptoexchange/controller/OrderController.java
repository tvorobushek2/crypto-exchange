package com.example.cryptoexchange.controller;

import com.example.cryptoexchange.dto.OrderDto;
import com.example.cryptoexchange.model.Order;
import com.example.cryptoexchange.model.OrderStatus;
import com.example.cryptoexchange.model.OrderType;
import com.example.cryptoexchange.service.OrderService;
import jakarta.validation.Valid;
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
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderDto dto) {
        Order order = Order.builder()
                .userId(dto.getUserId())
                .cryptoSymbol(dto.getCryptoSymbol())
                .amount(dto.getAmount())
                .price(dto.getPrice())
                .type(OrderType.valueOf(dto.getType().toUpperCase()))
                .status(dto.getStatus() != null
                        ? OrderStatus.valueOf(dto.getStatus().toUpperCase())
                        : OrderStatus.PENDING)
                .build();
        return ResponseEntity.ok(orderService.addOrder(order));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderDto dto) {
        Order order = Order.builder()
                .cryptoSymbol(dto.getCryptoSymbol())
                .amount(dto.getAmount())
                .price(dto.getPrice())
                .type(OrderType.valueOf(dto.getType().toUpperCase()))
                .status(dto.getStatus() != null
                        ? OrderStatus.valueOf(dto.getStatus().toUpperCase())
                        : OrderStatus.PENDING)
                .build();
        return ResponseEntity.ok(orderService.updateOrder(id, order));
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