package com.project.ecommerce.presentation.payment.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PaymentConfirmResponse {
    private long paymentId;
    private BigDecimal amount;
    private String status;

    private String paymentKey;
    private String orderId;
    private String paidAt;
}
