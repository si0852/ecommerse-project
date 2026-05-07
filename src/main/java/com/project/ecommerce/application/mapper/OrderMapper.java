package com.project.ecommerce.application.mapper;

import com.project.ecommerce.domain.dto.order.OrderData;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderMapper {

    public OrderData toOrderData(String userId, BigDecimal totalPrice) {
        return OrderData.builder()
                .userId(userId)
                .totalPrice(totalPrice)
                .build();
    }
}
