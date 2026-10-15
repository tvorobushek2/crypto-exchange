package com.example.cryptoexchange.model;

public class CustomerUser extends User {
    private Double walletBalance;

    public CustomerUser(Long id, String username, String email, String password, Double walletBalance) {
        super(id, username, email, password, UserRole.CUSTOMER);
        this.walletBalance = walletBalance;
    }

    public Double getWalletBalance() { return walletBalance; }
    public void setWalletBalance(Double walletBalance) { this.walletBalance = walletBalance; }

    @Override
    public String toString() {
        return "CustomerUser{walletBalance=" + walletBalance + ", id=" + getId() + ", username='" + getUsername() + "'}";
    }
}