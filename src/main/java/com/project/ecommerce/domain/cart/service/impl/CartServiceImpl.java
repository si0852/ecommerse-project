package com.project.ecommerce.domain.cart.service.impl;

import com.project.ecommerce.domain.dto.cart.CartResponseDto;
import com.project.ecommerce.domain.cart.entity.Carts;
import com.project.ecommerce.domain.cart.repository.CartsRepository;
import com.project.ecommerce.domain.cart.service.CartService;
import com.project.ecommerce.domain.dto.cart.status.CartStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartsRepository cartsRepository;

    @Transactional(readOnly = true)
    @Override
    public Optional<Carts> findByUserIdAndProductOptionIdAndCartStatus(String userId, Long productOptionId, CartStatus cartStatus) {
        return cartsRepository.findByUserIdAndProductOptionIdAndCartStatus(userId, productOptionId, cartStatus);
    }

    @Transactional
    @Override
    public void save(Carts cart) {
        cartsRepository.save(cart);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CartResponseDto> getCartDetails(String userId) {
        return cartsRepository.getCartDetails(userId);
    }
}
