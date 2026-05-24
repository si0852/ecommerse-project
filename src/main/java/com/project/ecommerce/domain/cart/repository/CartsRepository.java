package com.project.ecommerce.domain.cart.repository;

import com.project.ecommerce.domain.cart.entity.Carts;
import com.project.ecommerce.domain.dto.cart.status.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartsRepository extends JpaRepository<Carts, Long>, CartRepositoryCustom {

    Optional<Carts> findByUserIdAndProductOptionIdAndCartStatus(
            String userId,
            Long productOptionId,
            CartStatus cartStatus
    );

    Optional<Carts> findById(Long id);

}
