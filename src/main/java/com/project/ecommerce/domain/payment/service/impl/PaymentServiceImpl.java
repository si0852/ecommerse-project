package com.project.ecommerce.domain.payment.service.impl;

import com.project.ecommerce.domain.dto.payment.status.PaymentGenerateData;
import com.project.ecommerce.domain.payment.entity.Payment;
import com.project.ecommerce.domain.payment.repository.PaymentRepository;
import com.project.ecommerce.domain.payment.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    @Override
    public Payment generatePayment(PaymentGenerateData data) {
        Payment payment = Payment.toPayment(data);
        return paymentRepository.save(payment);
    }
}
