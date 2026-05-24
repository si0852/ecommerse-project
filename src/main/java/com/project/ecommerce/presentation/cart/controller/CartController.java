package com.project.ecommerce.presentation.cart.controller;

import com.project.ecommerce.application.CartFacade;
import com.project.ecommerce.common.response.ApiResponse;
import com.project.ecommerce.domain.dto.cart.CartDto;
import com.project.ecommerce.domain.dto.cart.CartResponseDto;
import com.project.ecommerce.presentation.cart.dto.request.CartRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartFacade cartFacade;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> addCartData(@RequestBody CartRequestDto dto, @AuthenticationPrincipal UserDetails userDetails) {
        String userId = userDetails.getUsername();
        cartFacade.addCart(CartDto.toCartDto(userId,dto.getProductOptionId(), dto.getQuantity()));
        return ResponseEntity.status(200).body(ApiResponse.ok("추가가 완료되었습니다."));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CartResponseDto>>> getCartDetails(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String userId = userDetails.getUsername();
        return ResponseEntity.status(200).body(ApiResponse.ok(cartFacade.getCartDetailsInfo(userId)));
    }

    @PatchMapping("/{cartId}")
    public ResponseEntity<ApiResponse<Void>> deleteCartItem(@PathVariable Long cartId) {
        cartFacade.cartRemoveStatus(cartId);
        return ResponseEntity.status(200).body(ApiResponse.ok("삭제가 완료되었습니다."));
    }

}
