package com.project.ecommerce.domain.dto.payment.status;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PaymentGenerateData {

    private long orderId;
    private String userId;
    private BigDecimal paymentPrice;
}
