package com.example.sport_ecommerce.application.port.in.user;

import com.example.sport_ecommerce.application.model.dto.LoginResponse;

public interface AuthenticateUserUseCase {
    LoginResponse authenticate(String email, String password);
}
