package com.example.sport_ecommerce.application.port.in.user;

public interface AuthenticateUserUseCase {
    String authenticate(String email, String password);
}
