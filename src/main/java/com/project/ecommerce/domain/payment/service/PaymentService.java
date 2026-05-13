package com.project.ecommerce.domain.payment.service;

import com.project.ecommerce.domain.dto.payment.status.PaymentGenerateData;
import com.project.ecommerce.domain.payment.entity.Payment;

public interface PaymentService {

    Payment generatePayment(PaymentGenerateData data);
}
