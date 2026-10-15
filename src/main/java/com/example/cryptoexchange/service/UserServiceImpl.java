package com.example.cryptoexchange.service;

import com.example.cryptoexchange.model.User;
import com.example.cryptoexchange.model.UserRole;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final List<User> users = new ArrayList<>();
    private final Map<Long, User> userMap = new HashMap<>();
    private long nextId = 1L;

    @Override
    public User addUser(User user) {
        user.setId(nextId++);
        users.add(user);
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return Optional.ofNullable(userMap.get(id));
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    @Override
    public List<User> findUsersByRole(UserRole role) {
        List<User> result = new ArrayList<>();
        for (User user : users) {
            if (user.getRole() == role) {
                result.add(user);
            }
        }
        return result;
    }

    @Override
    public boolean deleteUser(Long id) {
        User removed = userMap.remove(id);
        if (removed != null) {
            users.remove(removed);
            return true;
        }
        return false;
    }

    @Override
    public void displayUsers() {
        for (User user : users) {
            System.out.println(user.toString());
        }
    }
}