package org.example.project3.controller;

//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.project3.dto.UserDTO;
import org.example.project3.entity.UserEntity;
import org.example.project3.repository.UserRepository;
//import org.example.project3.service.UserService;
//import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;

//import java.io.IOException;
import java.util.Base64;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserRepository userRepository;
//    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(HttpSession session) {
        Object userIdObj = session.getAttribute("userId");
        if (userIdObj == null) {
            throw new RuntimeException("Не авторизован");
        }

        Long userId = (Long) userIdObj;

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        String avatarBase64 = null;
        if (user.getAvatar() != null && user.getAvatar().length > 0) {
            // Здесь важно указать правильный MIME-тип, png/jpg и т.д.
            avatarBase64 = "data:image/jpg;base64," + Base64.getEncoder().encodeToString(user.getAvatar());
        }

        UserDTO dto = new UserDTO(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getUsername(),
                avatarBase64
        );

        return ResponseEntity.ok(dto);
    }


//    @PostMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @Operation(summary = "Загрузить аватар")
//    public ResponseEntity<String> uploadAvatar(
//            @Parameter(description = "Файл аватара", required = true)
//            @RequestParam("file") MultipartFile file,
//            HttpSession session) throws IOException {
//
//        Long userId = (Long) session.getAttribute("userId");
//        if (userId == null) return ResponseEntity.status(401).body("Не авторизован");
//
//        if (file.isEmpty()) return ResponseEntity.badRequest().body("Файл пустой");
//
//        userService.uploadAvatar(userId, file);
//
//        return ResponseEntity.ok("Аватар загружен");
//    }

}
