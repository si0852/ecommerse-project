package com.project.ecommerce.domain.dto.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class CartResponseDto {
    private Long productId;
    private Long cartId;
    private String productName;
    private Long productOptionId;
    private String description;
    private String optionName;
    private int quantity;
    private BigDecimal totalPrice;

    public CartResponseDto(Long productId, Long cartId, String productName, String description,Long optionId,  String optionName, int quantity,
                           BigDecimal price, BigDecimal additionalPrice) {
        this.productId = productId;
        this.cartId = cartId;
        this.productName = productName;
        this.description = description;
        this.productOptionId = optionId;
        this.optionName = optionName;
        this.quantity = quantity;

        this.totalPrice = price.add(additionalPrice)
                .multiply(BigDecimal.valueOf(quantity));
    }
}
