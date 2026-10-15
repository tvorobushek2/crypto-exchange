package com.example.cryptoexchange;

public class Order {
    private Long id;
    private Long userId;
    private String cryptoSymbol;
    private Double amount;
    private Double price;
    private OrderType type;
    private OrderStatus status;

    public Order(Long id, Long userId, String cryptoSymbol, Double amount, Double price, OrderType type, OrderStatus status) {
        this.id = id;
        this.userId = userId;
        this.cryptoSymbol = cryptoSymbol;
        this.amount = amount;
        this.price = price;
        this.type = type;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getCryptoSymbol() { return cryptoSymbol; }
    public void setCryptoSymbol(String cryptoSymbol) { this.cryptoSymbol = cryptoSymbol; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public OrderType getType() { return type; }
    public void setType(OrderType type) { this.type = type; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    @Override
    public String toString() {
        return "Order{id=" + id + ", userId=" + userId + ", symbol='" + cryptoSymbol + "', amount=" + amount + ", price=" + price + ", type=" + type + ", status=" + status + "}";
    }
}