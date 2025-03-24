package com.example.sport_ecommerce.infrastructure.adapter.persistence.repository;

import com.example.sport_ecommerce.infrastructure.adapter.persistence.jpa.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, UUID> {
}
