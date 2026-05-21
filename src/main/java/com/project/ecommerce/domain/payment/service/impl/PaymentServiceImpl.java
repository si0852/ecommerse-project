package com.project.ecommerce.domain.payment.service.impl;

import com.project.ecommerce.application.dto.PaymentInfoDto;
import com.project.ecommerce.common.exception.BusinessException;
import com.project.ecommerce.domain.dto.payment.status.PaymentGenerateData;
import com.project.ecommerce.domain.dto.payment.status.PaymentStatus;
import com.project.ecommerce.domain.payment.entity.Payment;
import com.project.ecommerce.domain.payment.repository.PaymentRepository;
import com.project.ecommerce.domain.payment.service.PaymentService;
import com.project.ecommerce.infra.toss.TossPaymentClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final TossPaymentClient client;

    @Transactional
    @Override
    public Payment generatePayment(PaymentGenerateData data) {
        Payment payment = Payment.toPayment(data);
        return paymentRepository.save(payment);
    }

    @Transactional(readOnly = true)
    @Override
    public Payment getPaymentInfo(String orderId) {
        return paymentRepository.findByOrderIdAndPaymentStatus(orderId, PaymentStatus.PENDING).orElseThrow(() -> BusinessException.notFound("결제정보가 존재하지 않습니다"));
    }

    @Transactional
    @Override
    public void processPaymentSuccess(PaymentInfoDto dto) {
        client.confirmPayment(dto);
    }
}
