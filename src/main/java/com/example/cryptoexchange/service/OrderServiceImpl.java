package com.example.cryptoexchange.service;

import com.example.cryptoexchange.exception.InsufficientBalanceException;
import com.example.cryptoexchange.exception.OrderNotFoundException;
import com.example.cryptoexchange.exception.UserNotFoundException;
import com.example.cryptoexchange.model.CustomerUser;
import com.example.cryptoexchange.model.Order;
import com.example.cryptoexchange.model.OrderType;
import com.example.cryptoexchange.model.User;
import com.example.cryptoexchange.repository.OrderRepository;
import com.example.cryptoexchange.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Order addOrder(Order order) {
        User user = userRepository.findById(order.getUserId())
                .orElseThrow(() -> new UserNotFoundException(order.getUserId()));

        if (order.getType() == OrderType.BUY && user instanceof CustomerUser) {
            CustomerUser customer = (CustomerUser) user;
            double total = order.getAmount() * order.getPrice();
            if (customer.getWalletBalance() < total) {
                throw new InsufficientBalanceException(user.getId(), total, customer.getWalletBalance());
            }
        }

        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Long id, Order order) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        existing.setCryptoSymbol(order.getCryptoSymbol());
        existing.setAmount(order.getAmount());
        existing.setPrice(order.getPrice());
        existing.setType(order.getType());
        existing.setStatus(order.getStatus());
        return orderRepository.save(existing);
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order getOrderOrThrow(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
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
        throw new OrderNotFoundException(id);
    }
}