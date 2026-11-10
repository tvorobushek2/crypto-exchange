package com.example.cryptoexchange.service;

import com.example.cryptoexchange.model.Order;
import com.example.cryptoexchange.model.OrderType;
import com.example.cryptoexchange.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Long id, Order order) {
        return orderRepository.findById(id)
                .map(existing -> {
                    existing.setCryptoSymbol(order.getCryptoSymbol());
                    existing.setAmount(order.getAmount());
                    existing.setPrice(order.getPrice());
                    existing.setType(order.getType());
                    existing.setStatus(order.getStatus());
                    return orderRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> findOrdersBySymbol(String symbol) {
        return orderRepository.findByCryptoSymbolIgnoreCase(symbol);
    }

    @Override
    public List<Order> findOrdersByType(OrderType type) {
        return orderRepository.findByType(type);
    }

    @Override
    public boolean deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}