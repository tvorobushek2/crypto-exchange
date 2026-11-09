package com.example.cryptoexchange.repository;

import com.example.cryptoexchange.model.Order;
import com.example.cryptoexchange.model.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // IgnoreCase делает поиск нечувствительным к регистру ("btc" = "BTC")
    List<Order> findByCryptoSymbolIgnoreCase(String symbol);
    List<Order> findByType(OrderType type);
    List<Order> findByUserId(Long userId);
}