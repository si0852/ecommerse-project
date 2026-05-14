package com.project.ecommerce.domain.dto.cart;

import com.project.ecommerce.common.exception.BusinessException;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartDto {

    private String userId;
    private Long productOptionId;
    private int quantity;

    public void checkQuantity() {
        if (quantity <= 0) {
            throw BusinessException.badRequest("수량은 1개 이상이어야 합니다.");
        }
    }

    public static CartDto toCartDto(String userId, Long productOptionId, int quantity) {
        return new CartDto(userId, productOptionId, quantity);
    }
}
