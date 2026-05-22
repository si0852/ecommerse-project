package com.project.ecommerce.domain.cart.repository.impl;

import com.project.ecommerce.domain.dto.cart.CartResponseDto;
import com.project.ecommerce.domain.cart.entity.QCarts;
import com.project.ecommerce.domain.cart.repository.CartRepositoryCustom;
import com.project.ecommerce.domain.dto.cart.status.CartStatus;
import com.project.ecommerce.domain.product.entity.QProductOption;
import com.project.ecommerce.domain.product.entity.QProducts;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CartsRepositoryImpl implements CartRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CartResponseDto> getCartDetails(String userId) {

        QCarts cart = QCarts.carts;
        QProductOption po = QProductOption.productOption;
        QProducts p = QProducts.products;

        return queryFactory
                .select(Projections.constructor(CartResponseDto.class,
                        p.id, cart.id,p.productName, p.description,
                        po.optionName, cart.quantity,  p.price,  po.additionalPrice
                ))
                .from(cart)
                .join(cart.productOption, po)
                .join(po.products, p)
                .where(
                        cart.userId.eq(userId),
                        cart.cartStatus.eq(CartStatus.ACTIVE)
                )
                .fetch();

    }
}
