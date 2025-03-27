package com.example.sport_ecommerce.application.service.user;

import com.example.sport_ecommerce.application.model.dto.LoginResponse;
import com.example.sport_ecommerce.application.port.in.user.AuthenticateUserUseCase;
import com.example.sport_ecommerce.application.port.in.user.RegisterUserUseCase;
import com.example.sport_ecommerce.application.port.out.UserRepositoryPort;
import com.example.sport_ecommerce.domain.model.User;
import com.example.sport_ecommerce.domain.model.valueobject.Role;
import com.example.sport_ecommerce.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticateUserUseCase, RegisterUserUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(String name, String email, String password) {
        User user = new User(
                name,
                email,
                passwordEncoder.encode(password),
                Role.USER
        );
        userRepository.save(user);
    }

    @Override
    public LoginResponse authenticate(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return LoginResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}

