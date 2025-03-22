package com.example.sport_ecommerce.application.mapper;

import com.example.sport_ecommerce.application.model.response.PartOptionResponse;
import com.example.sport_ecommerce.application.model.response.PartResponse;
import com.example.sport_ecommerce.application.model.response.ProductResponse;
import com.example.sport_ecommerce.domain.model.Part;
import com.example.sport_ecommerce.domain.model.PartOption;
import com.example.sport_ecommerce.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductResponseMapper {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    ProductResponse toResponse(Product product);

    PartResponse toPartResponse(Part part);

    PartOptionResponse toPartOptionResponse(PartOption option);
}

