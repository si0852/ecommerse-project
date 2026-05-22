package com.project.ecommerce.application;

import com.project.ecommerce.domain.dto.cart.CartDto;
import com.project.ecommerce.domain.dto.cart.CartResponseDto;
import com.project.ecommerce.common.exception.BusinessException;
import com.project.ecommerce.domain.cart.entity.Carts;
import com.project.ecommerce.domain.cart.service.CartService;
import com.project.ecommerce.domain.dto.cart.status.CartStatus;
import com.project.ecommerce.domain.product.entity.ProductOption;
import com.project.ecommerce.domain.product.service.ProductsService;
import com.project.ecommerce.domain.user.service.UserDetailsService;
import com.project.ecommerce.presentation.cart.dto.request.CartRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartFacade {

    private final CartService cartService;
    private final ProductsService productsService;
    private final UserDetailsService userDetailsService;

    @Transactional
    public void addCart(CartDto dto) {
        dto.checkQuantity();

        Optional<Carts> optionCart = cartService.findByUserIdAndProductOptionIdAndCartStatus(dto.getUserId(), dto.getProductOptionId(), CartStatus.ACTIVE);
        ProductOption productOptionData = productsService.getProductOptionData(dto.getProductOptionId());

        int addedQuantity = dto.getQuantity();
        int currentInCart = optionCart.map(Carts::getQuantity).orElse(0);
        int targetQuantity = addedQuantity + currentInCart;


        int currentQuantity = productOptionData.getInventory().getQuantity();
        if (currentQuantity < targetQuantity) {
            throw BusinessException.OutOfStockException("재고가 부족합니다.");
        }

        optionCart.ifPresentOrElse(
                carts -> carts.updateQuantity(targetQuantity),
                () -> {
                    Carts carts = Carts.toCarts(dto.getUserId(),  targetQuantity);
                    carts.setProductOption(productOptionData);
                    cartService.save(carts);
                }
        );
    }

    @Transactional
    public List<CartResponseDto> getCartDetailsInfo(String userId) {
        userDetailsService.loadUserByUsername(userId);
        List<CartResponseDto> cartDetails = cartService.getCartDetails(userId);
        return CollectionUtils.isEmpty(cartDetails) ? List.of() : cartDetails;
    }

    @Transactional
    public void cartRemoveStatus(long cartId) {
        Carts cart = cartService.findByCartId(cartId);
        cart.deleteUpdateStatus();
    }
}
