package org.example.project3.service;

import lombok.RequiredArgsConstructor;
import org.example.project3.dto.UserLoginDto;
import org.example.project3.dto.UserRegisterDto;
import org.example.project3.entity.UserEntity;
import org.example.project3.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public UserEntity register(UserRegisterDto dto) {
        // Проверка уникальности логина и почты
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Логин уже занят");
        }
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email уже используется");
        }

        // Простейшее хеширование пароля (для начала можно plain, потом заменить на BCrypt)
        UserEntity user = UserEntity.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .build();

        return userRepository.save(user);
    }

    public UserEntity login(UserLoginDto dto) {
        UserEntity user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        if (!user.getPassword().equals(dto.getPassword())) {
            throw new RuntimeException("Неверный пароль");
        }

        return user;
    }
}

