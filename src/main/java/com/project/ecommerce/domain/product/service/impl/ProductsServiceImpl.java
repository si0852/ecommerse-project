package com.project.ecommerce.domain.product.service.impl;

import com.project.ecommerce.common.exception.BusinessException;
import com.project.ecommerce.domain.dto.order.ProductOptionDto;
import com.project.ecommerce.domain.product.entity.ProductOption;
import com.project.ecommerce.domain.product.repository.ProductsOptionRepository;
import com.project.ecommerce.presentation.product.dto.response.ProductsDetailsResponseDto;
import com.project.ecommerce.presentation.product.dto.response.ProductsResponseDto;
import com.project.ecommerce.domain.product.entity.Products;
import com.project.ecommerce.domain.product.repository.InventoryRepository;
import com.project.ecommerce.domain.product.repository.ProductsRepository;
import com.project.ecommerce.domain.product.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductsServiceImpl implements ProductsService {

    private final ProductsRepository productsRepository;
    private final InventoryRepository inventoryRepository;
    private final ProductsOptionRepository productsOptionRepository;

    @Override
    public ProductsDetailsResponseDto getProductsById(Long id) {
        Products products = productsRepository.findById(id).orElseThrow(() -> BusinessException.notFound("상품이 존재하지 않습니다."));

        return ProductsDetailsResponseDto.from(products);
    }

    @Override
    public List<ProductsResponseDto> getProductsData() {
        List<Products> products = productsRepository.findAll();

        return products.stream()
                .map(ProductsResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void decreaseStock(List<ProductOptionDto> dtos) {
        for (ProductOptionDto data : dtos) {
            int result = inventoryRepository.decreaseStock(data.getProductOptionId(), data.getQuantity());
            if (result == 0) {
                throw BusinessException.OutOfStockException("재고가 부족하거나 상품 정보가 올바르지 않습니다. (ID: " + data.getProductOptionId() + ")");
            }
        }

    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductOption> getProductsOptionData(List<Long> id) {
        return productsOptionRepository.findAllByIdIn(id);
    }

    @Override
    public ProductOption getProductOptionData(Long optionId) {
        return productsOptionRepository.findByOptionId(optionId).orElse(null);
    }


}
