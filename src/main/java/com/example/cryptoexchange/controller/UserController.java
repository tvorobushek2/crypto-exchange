package com.example.cryptoexchange.controller;

import com.example.cryptoexchange.dto.UserDto;
import com.example.cryptoexchange.model.*;
import com.example.cryptoexchange.service.UserService;
import jakarta.validation.Valid;
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
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDto dto) {
        User user = createUserByRole(dto);
        return ResponseEntity.ok(userService.addUser(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto dto) {
        User user = createUserByRole(dto);
        user.setId(id);
        return ResponseEntity.ok(userService.updateUser(id, user));
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

    private User createUserByRole(UserDto dto) {
        UserRole role = UserRole.valueOf(dto.getRole().toUpperCase());
        return switch (role) {
            case ADMIN -> new AdminUser(dto.getUsername(), dto.getEmail(), dto.getPassword());
            case MANAGER -> new ManagerUser(dto.getUsername(), dto.getEmail(), dto.getPassword(), dto.getDepartment());
            case CUSTOMER -> new CustomerUser(dto.getUsername(), dto.getEmail(), dto.getPassword(),
                    dto.getWalletBalance() != null ? dto.getWalletBalance() : 0.0);
        };
    }
}