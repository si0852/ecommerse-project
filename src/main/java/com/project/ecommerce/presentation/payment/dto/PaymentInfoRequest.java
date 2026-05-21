package com.project.ecommerce.presentation.payment.dto;

import com.project.ecommerce.domain.dto.payment.status.PaymentMethod;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentInfoRequest {

    private String orderId;
    private String paymentKey;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;

}
