package com.project.ecommerce.presentation.order.dto.request;

import lombok.Data;

@Data
public class CartOrderRequestDto {

    private long cartId;
    private long productId;
    private String productName;
    private long productOptionId;
    private int quantity;
}
