package com.project.ecommerce.domain.product.service;

import com.project.ecommerce.domain.dto.order.ProductOptionDto;
import com.project.ecommerce.domain.dto.product.DecreaseInventoryData;
import com.project.ecommerce.domain.product.entity.ProductOption;
import com.project.ecommerce.presentation.product.dto.response.ProductsDetailsResponseDto;
import com.project.ecommerce.presentation.product.dto.response.ProductsResponseDto;

import java.util.List;

public interface ProductsService {

    ProductsDetailsResponseDto getProductsById(Long id);

    List<ProductsResponseDto> getProductsData();

    void decreaseStock(List<ProductOptionDto> data);

    List<ProductOption> getProductsOptionData(List<Long> id);
}
