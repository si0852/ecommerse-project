package com.project.ecommerce.domain.payment.repository;

import com.project.ecommerce.domain.dto.payment.status.PaymentStatus;
import com.project.ecommerce.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByOrderIdAndPaymentStatus(String orderId, PaymentStatus paymentStatus);
}
