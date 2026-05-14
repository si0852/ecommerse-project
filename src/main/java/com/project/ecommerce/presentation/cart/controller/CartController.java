package com.project.ecommerce.presentation.cart.controller;

import com.project.ecommerce.application.CartFacade;
import com.project.ecommerce.application.OrderFacade;
import com.project.ecommerce.common.response.ApiResponse;
import com.project.ecommerce.presentation.cart.dto.request.CartRequestDto;
import com.project.ecommerce.presentation.order.dto.request.OrderRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts/api/v1")
@RequiredArgsConstructor
public class CartController {

    private final CartFacade cartFacade;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> addCartData(@RequestBody CartRequestDto dto) {
        cartFacade.addCart(dto);
        return ResponseEntity.status(200).body(ApiResponse.ok("추가가 완료되었습니다."));
    }
}
