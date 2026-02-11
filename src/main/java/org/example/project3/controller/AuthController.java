package org.example.project3.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.project3.dto.UserLoginDto;
import org.example.project3.dto.UserRegisterDto;
import org.example.project3.entity.UserEntity;
import org.example.project3.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public UserEntity register(@Valid @RequestBody UserRegisterDto dto, HttpSession session) {
        UserEntity user = authService.register(dto);
        session.setAttribute("userId", user.getId()); // сохраняем в сессию
        return user;
    }

    @PostMapping("/login")
    public UserEntity login(@Valid @RequestBody UserLoginDto dto, HttpSession session) {
        UserEntity user = authService.login(dto);
        session.setAttribute("userId", user.getId()); // сохраняем в сессию
        return user;
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "Вышли из системы";
    }
}
