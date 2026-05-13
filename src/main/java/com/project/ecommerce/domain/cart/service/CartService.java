package com.project.ecommerce.domain.cart.service;

import com.project.ecommerce.domain.cart.entity.Carts;
import com.project.ecommerce.domain.dto.cart.status.CartStatus;

import java.util.Optional;

public interface CartService {

    Optional<Carts> findByUserIdAndProductOptionIdAndCartStatus(String userId, Long productOptionId, CartStatus cartStatus);

    void save(Carts cart);
}
