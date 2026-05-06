package com.project.ecommerce.domain.dto.order;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderData {

    private String userId;
    private BigDecimal totalPrice;
}
