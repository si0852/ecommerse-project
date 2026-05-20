package com.project.ecommerce.domain.dto.order;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class OrderData {

    private String userId;
    private String orderId;
    private BigDecimal totalPrice;
    List<OrderItemData> orderItem;
}
