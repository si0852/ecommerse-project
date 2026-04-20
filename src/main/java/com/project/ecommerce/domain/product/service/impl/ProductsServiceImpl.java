package com.project.ecommerce.domain.product.service.impl;

import com.project.ecommerce.common.exception.BusinessException;
import com.project.ecommerce.domain.dto.product.response.ProductsResponseDto;
import com.project.ecommerce.domain.product.entity.Products;
import com.project.ecommerce.domain.product.repository.ProductsRepository;
import com.project.ecommerce.domain.product.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductsServiceImpl implements ProductsService {

    private final ProductsRepository productsRepository;

    @Override
    public ProductsResponseDto getProductsById(Long id) {
        Products products = productsRepository.findById(id).orElseThrow(() -> BusinessException.notFound("상품이 존재하지 않습니다."));

        return ProductsResponseDto.builder()
                .id(products.getId())
                .productName(products.getProductName())
                .price(products.getPrice())
                .stock(products.getStock())
                .build();
    }

    @Override
    public List<ProductsResponseDto> getProductsData() {
        List<Products> products = productsRepository.findAll();

        return products.stream()
                .map(ProductsResponseDto::from)
                .collect(Collectors.toList());
    }
}
