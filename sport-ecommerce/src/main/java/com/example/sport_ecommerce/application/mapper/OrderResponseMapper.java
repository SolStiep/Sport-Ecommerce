package com.example.sport_ecommerce.application.mapper;

import com.example.sport_ecommerce.application.model.response.OrderDetailResponse;
import com.example.sport_ecommerce.application.model.response.UserOrderSummaryResponse;
import com.example.sport_ecommerce.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = { ConfigurationResponseMapper.class }
)
public interface OrderResponseMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.name", target = "userName")
    @Mapping(source = "items", target = "items")
    OrderDetailResponse toDetail(Order order);

    UserOrderSummaryResponse toSummary(Order order);

    List<UserOrderSummaryResponse> toSummaryList(List<Order> orders);
}

