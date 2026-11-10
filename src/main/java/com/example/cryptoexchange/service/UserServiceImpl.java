package com.example.cryptoexchange.service;

import com.example.cryptoexchange.model.User;
import com.example.cryptoexchange.model.UserRole;
import com.example.cryptoexchange.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        return userRepository.findById(id)
                .map(existing -> {
                    existing.setUsername(user.getUsername());
                    existing.setEmail(user.getEmail());
                    existing.setRole(user.getRole());
                    return userRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findUsersByRole(UserRole role) {
        return userRepository.findByRole(role);
    }

    @Override
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public void displayUsers() {
        userRepository.findAll().forEach(System.out::println);
    }
}