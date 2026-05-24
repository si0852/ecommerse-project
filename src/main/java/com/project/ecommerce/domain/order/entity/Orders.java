package com.project.ecommerce.domain.order.entity;

import com.project.ecommerce.application.dto.CartEntityDto;
import com.project.ecommerce.domain.dto.order.OrderData;
import com.project.ecommerce.domain.dto.order.OrderItemData;
import com.project.ecommerce.domain.dto.order.status.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
@EntityListeners(AuditingEntityListener.class)
public class Orders {

    @Id
    private String id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder.Default
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    public void addOrderItem(OrderItem item) {
        this.orderItems.add(item);
        item.setOrders(this);
    }

    public void addTotalPrice(OrderItem item) {
        this.orderItems.add(item);
        item.setOrders(this);

        BigDecimal totalPrice = item.getTotalPrice();
        this.totalPrice = Objects.isNull(this.totalPrice) ? totalPrice : this.totalPrice.add(totalPrice);
    }

    public static Orders toOrder(OrderData order) {
        return Orders.builder()
                .id(order.getOrderId())
                .userId(order.getUserId())
                .totalPrice(order.getTotalPrice())
                .orderStatus(OrderStatus.PENDING)
                .build();
    }

    public static Orders toOrder(CartEntityDto cartOrder) {
        return Orders.builder()
                .id(cartOrder.getOrderId())
                .userId(cartOrder.getUserId())
                .orderStatus(OrderStatus.PENDING)
                .build();
    }

    public void updatePayCompleteStatus() {
        this.orderStatus = OrderStatus.PAYMENT_COMPLETE;
    }
}
