package com.project.ecommerce.presentation.cart.dto.request;

import com.project.ecommerce.common.exception.BusinessException;
import lombok.Data;

@Data
public class CartRequestDto {

    private String userId;
    private Long productOptionId;
    private int quantity;

    public void checkQuantity() {
        if (quantity <= 0) {
            throw BusinessException.badRequest("수량은 1개 이상이어야 합니다.");
        }
    }
}
