package com.project.ecommerce.presentation.product.dto.response;

import com.project.ecommerce.domain.product.entity.Products;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ProductsDetailsResponseDto {

    private long productId;
    private String productName;
    private long productPrice;

    List<ProductsOptionDetailsResponseDto> options;

    public static ProductsDetailsResponseDto from(Products product) {
        return ProductsDetailsResponseDto.builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .productPrice(product.getPrice().longValue())
                .options(product.getProductOptions().stream()
                        .map(ProductsOptionDetailsResponseDto::from)
                        .toList())
                .build();

    }
}
