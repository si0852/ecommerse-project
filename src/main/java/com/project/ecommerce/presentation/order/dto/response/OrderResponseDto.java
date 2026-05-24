package com.project.ecommerce.presentation.order.dto.response;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class OrderResponseDto {
    private String orderId;
    private int totalPrice;
}
