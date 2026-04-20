package com.project.ecommerce.domain.dto.product.response;

import com.project.ecommerce.domain.product.entity.Products;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductsResponseDto {

    private long id;
    private String productName;
    private BigDecimal price;
    private long stock;

    public static ProductsResponseDto from(Products product) {
        return ProductsResponseDto.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }
}
