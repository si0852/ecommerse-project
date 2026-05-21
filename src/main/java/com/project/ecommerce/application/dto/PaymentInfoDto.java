package com.project.ecommerce.application.dto;

import com.project.ecommerce.presentation.payment.dto.PaymentInfoRequest;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PaymentInfoDto {

    private String orderId;
    private String paymentKey;
    private BigDecimal amount;

    public static PaymentInfoDto toDto(PaymentInfoRequest req) {
        return new PaymentInfoDto(req.getOrderId(), req.getPaymentKey(), req.getAmount());
    }
}
