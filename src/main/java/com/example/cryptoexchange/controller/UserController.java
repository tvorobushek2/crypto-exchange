package com.example.cryptoexchange.controller;

import com.example.cryptoexchange.dto.UserDto;
import com.example.cryptoexchange.model.*;
import com.example.cryptoexchange.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto dto) {
        User user;
        UserRole role = UserRole.valueOf(dto.getRole().toUpperCase());

        switch (role) {
            case ADMIN:
                user = new AdminUser(null, dto.getUsername(), dto.getEmail(), dto.getPassword());
                break;
            case MANAGER:
                user = new ManagerUser(null, dto.getUsername(), dto.getEmail(), dto.getPassword(), dto.getDepartment());
                break;
            case CUSTOMER:
                user = new CustomerUser(null, dto.getUsername(), dto.getEmail(), dto.getPassword(), dto.getWalletBalance() != null ? dto.getWalletBalance() : 0.0);
                break;
            default:
                return ResponseEntity.badRequest().build();
        }

        User saved = userService.addUser(user);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String role) {
        UserRole userRole = UserRole.valueOf(role.toUpperCase());
        return ResponseEntity.ok(userService.findUsersByRole(userRole));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.deleteUser(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}