package htw.webtech.WT_todo.rest.controller;

import htw.webtech.WT_todo.business.service.UserService;
import htw.webtech.WT_todo.persistence.entity.UserEntity;
import htw.webtech.WT_todo.rest.model.AuthResponse;
import htw.webtech.WT_todo.rest.model.LoginRequest;
import htw.webtech.WT_todo.rest.model.RegisterRequest;
import htw.webtech.WT_todo.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        UserEntity user = userService.register(request.getUsername(), request.getPassword());
        String token = jwtService.createToken(user.getUsername());
        return new AuthResponse(token, user.getUsername());
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        UserEntity user = userService.authenticate(request.getUsername(), request.getPassword());
        String token = jwtService.createToken(user.getUsername());
        return new AuthResponse(token, user.getUsername());
    }

    @GetMapping("/me")
    public AuthResponse me(Authentication auth) {
        // token Feld bewusst leer lassen
        return new AuthResponse(null, auth.getName());
    }
}
