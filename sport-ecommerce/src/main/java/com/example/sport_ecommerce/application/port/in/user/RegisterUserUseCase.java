package com.example.sport_ecommerce.application.port.in.user;

public interface RegisterUserUseCase {
    void register(String name, String email, String password);
}
