package org.example.project3.service;

import lombok.RequiredArgsConstructor;
import org.example.project3.entity.UserEntity;
import org.example.project3.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void uploadAvatar(Long userId, MultipartFile file) throws IOException {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        // Проверяем размер файла (не обязательно, но полезно)
        if (file.isEmpty()) throw new RuntimeException("Файл пустой");

        // Сохраняем байты файла
        user.setAvatar(file.getBytes());
        userRepository.saveAndFlush(user); // flush гарантирует, что Hibernate сразу сгенерирует SQL
    }
}


