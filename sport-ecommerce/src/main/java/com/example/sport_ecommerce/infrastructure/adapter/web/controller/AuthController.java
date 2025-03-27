package com.example.sport_ecommerce.infrastructure.adapter.web.controller;

import com.example.sport_ecommerce.application.model.dto.LoginRequest;
import com.example.sport_ecommerce.application.model.dto.LoginResponse;
import com.example.sport_ecommerce.application.model.dto.RegisterRequest;
import com.example.sport_ecommerce.application.port.in.user.AuthenticateUserUseCase;
import com.example.sport_ecommerce.application.port.in.user.RegisterUserUseCase;
import com.example.sport_ecommerce.infrastructure.security.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Value("${jwt.cookieName}")
    private String cookieName;

    private final AuthenticateUserUseCase authUseCase;
    private final RegisterUserUseCase registerUseCase;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest req) {
        registerUseCase.register(req.getName(), req.getEmail(), req.getPassword());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req, HttpServletResponse response) {
        LoginResponse loginResponse = authUseCase.authenticate(req.getEmail(), req.getPassword());
        String token = jwtService.generateToken(
                new org.springframework.security.core.userdetails.User(
                        loginResponse.getEmail(),
                        "",
                        List.of(new SimpleGrantedAuthority("ROLE_" + loginResponse.getRole()))
                )
        );

        Cookie jwtCookie = new Cookie(cookieName, token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(60 * 60 * 10); // 10 hours
        // jwtCookie.setSecure(true); // only with HTTPS

        response.addCookie(jwtCookie);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }

}

