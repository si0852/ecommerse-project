package com.project.ecommerce.presentation.cart.dto.request;

import lombok.Data;

@Data
public class CartRequestDto {

    private Long productOptionId;
    private int quantity;
}
