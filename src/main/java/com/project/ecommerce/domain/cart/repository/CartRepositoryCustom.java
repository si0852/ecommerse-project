package com.project.ecommerce.domain.cart.repository;

import com.project.ecommerce.domain.dto.cart.CartResponseDto;

import java.util.List;

public interface CartRepositoryCustom {

    List<CartResponseDto> getCartDetails(String userId);

}
