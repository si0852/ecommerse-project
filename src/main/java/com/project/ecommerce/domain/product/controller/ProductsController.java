package com.project.ecommerce.domain.product.controller;

import com.project.ecommerce.common.response.ApiResponse;
import com.project.ecommerce.domain.dto.product.response.ProductsResponseDto;
import com.project.ecommerce.domain.product.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products/api/v1")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductsService productsService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductsResponseDto>>> getProductsData() {
        return ResponseEntity.status(200).body(ApiResponse.ok(productsService.getProductsData()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductsResponseDto>> getProductsDataById(@PathVariable("id") Long productId){
        return ResponseEntity.status(200).body(ApiResponse.ok(productsService.getProductsById(productId)));
    }
}
