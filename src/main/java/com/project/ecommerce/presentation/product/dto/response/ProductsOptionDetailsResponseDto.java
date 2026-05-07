package com.project.ecommerce.presentation.product.dto.response;

import com.project.ecommerce.domain.product.entity.ProductOption;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductsOptionDetailsResponseDto {
    private long productOptionId;
    private String optionName;
    private long optionPrice;
    private long inventoryId;
    private int quantity;

    public static ProductsOptionDetailsResponseDto from(ProductOption option) {
        return ProductsOptionDetailsResponseDto.builder()
                .productOptionId(option.getId())
                .optionName(option.getOptionName())
                .optionPrice(option.getAdditionalPrice().longValue())
                .inventoryId(option.getInventory() != null ? option.getId() : -1)
                .quantity(option.getInventory() != null ? option.getInventory().getQuantity() : 0)
                .build();
    }
}
