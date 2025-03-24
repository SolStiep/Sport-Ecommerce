package com.example.sport_ecommerce.infrastructure.adapter.persistence.mapper;

import com.example.sport_ecommerce.domain.model.User;
import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {
    UserEntity toEntity(User user);
    User toDomain(UserEntity entity);
}

