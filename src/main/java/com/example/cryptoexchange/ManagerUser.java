package com.example.cryptoexchange;

public class ManagerUser extends User {
    private String department;

    public ManagerUser(Long id, String username, String email, String password, String department) {
        super(id, username, email, password, UserRole.MANAGER);
        this.department = department;
    }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}