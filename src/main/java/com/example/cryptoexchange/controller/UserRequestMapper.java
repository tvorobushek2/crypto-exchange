package com.example.cryptoexchange.controller;

import com.example.cryptoexchange.dto.UserDto;
import com.example.cryptoexchange.exception.InvalidUserDataException;
import com.example.cryptoexchange.model.*;
import org.springframework.stereotype.Component;

@Component
public class UserRequestMapper {

    public User toEntity(UserDto dto) {
        if (dto == null) {
            throw new InvalidUserDataException("Тело запроса отсутствует");
        }
        if (isBlank(dto.getUsername())) {
            throw new InvalidUserDataException("Поле username обязательно");
        }
        if (isBlank(dto.getEmail())) {
            throw new InvalidUserDataException("Поле email обязательно");
        }
        if (!dto.getEmail().contains("@")) {
            throw new InvalidUserDataException("Некорректный формат email");
        }
        if (isBlank(dto.getPassword())) {
            throw new InvalidUserDataException("Поле password обязательно");
        }
        if (dto.getPassword().length() < 6) {
            throw new InvalidUserDataException("Пароль должен быть не короче 6 символов");
        }
        if (isBlank(dto.getRole())) {
            throw new InvalidUserDataException("Поле role обязательно");
        }

        UserRole role;
        try {
            role = UserRole.valueOf(dto.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidUserDataException("Недопустимая роль: " + dto.getRole() + ". Допустимые: ADMIN, MANAGER, CUSTOMER");
        }

        switch (role) {
            case ADMIN:
                return new AdminUser(dto.getUsername(), dto.getEmail(), dto.getPassword());
            case MANAGER:
                if (isBlank(dto.getDepartment())) {
                    throw new InvalidUserDataException("Для роли MANAGER поле department обязательно");
                }
                return new ManagerUser(dto.getUsername(), dto.getEmail(), dto.getPassword(), dto.getDepartment());
            case CUSTOMER:
                if (dto.getWalletBalance() == null) {
                    throw new InvalidUserDataException("Для роли CUSTOMER поле walletBalance обязательно");
                }
                if (dto.getWalletBalance() < 0) {
                    throw new InvalidUserDataException("Баланс кошелька не может быть отрицательным");
                }
                return new CustomerUser(dto.getUsername(), dto.getEmail(), dto.getPassword(), dto.getWalletBalance());
            default:
                throw new InvalidUserDataException("Неизвестная роль");
        }
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}