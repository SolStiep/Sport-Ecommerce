package com.example.sport_ecommerce.application.service.product;

import com.example.sport_ecommerce.application.mapper.ProductResponseMapper;
import com.example.sport_ecommerce.application.model.response.ProductResponse;
import com.example.sport_ecommerce.application.port.in.product.GetProductDetailUseCase;
import com.example.sport_ecommerce.application.port.out.ProductRepositoryPort;
import com.example.sport_ecommerce.domain.exception.ProductNotFoundException;
import com.example.sport_ecommerce.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetProductDetailService implements GetProductDetailUseCase {

    private final ProductRepositoryPort productRepository;
    private final ProductResponseMapper responseMapper;

    @Override
    public ProductResponse getProductById(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
        return responseMapper.toResponse(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(responseMapper::toResponse)
                .toList();
    }
}
