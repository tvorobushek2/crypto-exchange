package com.example.cryptoexchange;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final List<Order> orders = new ArrayList<>();
    private final Map<Long, Order> orderMap = new HashMap<>();
    private long nextId = 1L;

    @Override
    public Order addOrder(Order order) {
        order.setId(nextId++);
        orders.add(order);
        orderMap.put(order.getId(), order);
        return order;
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return Optional.ofNullable(orderMap.get(id));
    }

    @Override
    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }

    @Override
    public List<Order> findOrdersBySymbol(String symbol) {
        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            if (order.getCryptoSymbol().equalsIgnoreCase(symbol)) {
                result.add(order);
            }
        }
        return result;
    }

    @Override
    public List<Order> findOrdersByType(OrderType type) {
        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            if (order.getType() == type) {
                result.add(order);
            }
        }
        return result;
    }

    @Override
    public boolean deleteOrder(Long id) {
        Order removed = orderMap.remove(id);
        if (removed != null) {
            orders.remove(removed);
            return true;
        }
        return false;
    }
}