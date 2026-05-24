package com.project.ecommerce.application.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TossApprovalResponse {

    private String paymentKey;
    private String type;
    private String orderId;
    private String method;
    private String approvedAt;
}

