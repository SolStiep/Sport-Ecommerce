package com.example.sport_ecommerce.application.service.product;

import com.example.sport_ecommerce.application.mapper.ProductResponseMapper;
import com.example.sport_ecommerce.application.model.response.ProductResponse;
import com.example.sport_ecommerce.application.port.in.product.GetProductCatalogUseCase;
import com.example.sport_ecommerce.application.port.out.ProductRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetProductCatalogService implements GetProductCatalogUseCase {

    private final ProductRepositoryPort productRepository;
    private final ProductResponseMapper responseMapper;

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(responseMapper::toResponse)
                .toList();
    }
}
