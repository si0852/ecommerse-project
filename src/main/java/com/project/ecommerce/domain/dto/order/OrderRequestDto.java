package com.project.ecommerce.domain.dto.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {

    private long productId;
    private String productName;
    private List<ProductOptionDto> optionData;
}
