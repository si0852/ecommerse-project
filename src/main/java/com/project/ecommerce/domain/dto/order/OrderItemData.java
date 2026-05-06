package com.project.ecommerce.domain.dto.order;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderItemData {

    private long orderId;
    private long productId;
    private long productOptionId;
    private String productName;
    private int quantity;
    private BigDecimal totalPrice;
}
