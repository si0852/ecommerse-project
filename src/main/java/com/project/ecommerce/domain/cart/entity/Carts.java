package com.project.ecommerce.domain.cart.entity;

import com.project.ecommerce.common.exception.BusinessException;
import com.project.ecommerce.domain.dto.cart.status.CartStatus;
import com.project.ecommerce.domain.product.entity.ProductOption;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(
        name = "carts",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_user_product_option",
                        columnNames = {"userId", "productOptionId", "cartStatus"}
                )
        }
)
public class Carts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

//    @Column(nullable = false)
//    private Long productOptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_option_id")
    private ProductOption productOption;

    @Min(1)
    @Column(nullable = false, columnDefinition = "INT UNSIGNED CHECK (quantity>0)")
    private int quantity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CartStatus cartStatus = CartStatus.ACTIVE;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public static Carts toCarts(String userId,  int quantity) {
        if (quantity <= 0) {
            throw BusinessException.badRequest("수량은 1개 이상이어야 합니다.");
        }

        return Carts.builder()
                .userId(userId)
                .quantity(quantity)
                .cartStatus(CartStatus.ACTIVE)
                .build();
    }

    public void updateQuantity(int newQuantity) {
        if (newQuantity <= 0) {
            throw  BusinessException.badRequest("수량은 1개 이상이어야 합니다.");
        }
        this.quantity = newQuantity;
    }

    public void updateStatus() {
        this.cartStatus = CartStatus.ORDERED;
    }
}
