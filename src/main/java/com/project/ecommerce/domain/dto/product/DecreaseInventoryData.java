package com.project.ecommerce.domain.dto.product;

import lombok.Data;

@Data
public class DecreaseInventoryData {
    private Long productOptionId;
    private int reqQuantity;
}
