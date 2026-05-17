package com.project.ecommerce.presentation.order.controller;

import com.project.ecommerce.application.OrderFacade;
import com.project.ecommerce.application.dto.CartEntityDto;
import com.project.ecommerce.common.response.ApiResponse;
import com.project.ecommerce.presentation.order.dto.request.CartOrderRequestDto;
import com.project.ecommerce.presentation.order.dto.request.OrderRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderFacade orderFacade;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> generateOrderData(@RequestBody OrderRequestDto dto) {
        orderFacade.generateOrderService(dto);
        return ResponseEntity.status(200).body(ApiResponse.ok("주문이 완료되었습니다."));
    }

    @PostMapping("/cart")
    public ResponseEntity<ApiResponse<Void>> generateCartOrderData(@AuthenticationPrincipal UserDetails userDetails, @RequestBody List<CartOrderRequestDto> dto) {
        orderFacade.generateCartOrderService(CartEntityDto.toCartEntity(dto, userDetails.getUsername()));
        return ResponseEntity.status(200).body(ApiResponse.ok("주문이 완료되었습니다."));
    }
}
