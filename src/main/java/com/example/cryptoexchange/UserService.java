package com.example.cryptoexchange;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User addUser(User user);
    Optional<User> getUserById(Long id);
    List<User> getAllUsers();
    List<User> findUsersByRole(UserRole role);
    boolean deleteUser(Long id);
    void displayUsers();
}