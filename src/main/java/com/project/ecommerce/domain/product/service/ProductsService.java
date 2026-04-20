package com.project.ecommerce.domain.product.service;

import com.project.ecommerce.domain.dto.product.response.ProductsResponseDto;

import java.util.List;

public interface ProductsService {

    ProductsResponseDto getProductsById(Long id);

    List<ProductsResponseDto> getProductsData();
}
