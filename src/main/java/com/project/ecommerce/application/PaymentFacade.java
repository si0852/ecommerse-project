package com.project.ecommerce.application;

import com.project.ecommerce.application.dto.PaymentInfoDto;
import com.project.ecommerce.presentation.payment.dto.PaymentInfoRequest;
import com.project.ecommerce.common.exception.BusinessException;
import com.project.ecommerce.domain.dto.order.status.OrderStatus;
import com.project.ecommerce.domain.order.entity.Orders;
import com.project.ecommerce.domain.order.service.OrderService;
import com.project.ecommerce.domain.payment.entity.Payment;
import com.project.ecommerce.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component@RequiredArgsConstructor
public class PaymentFacade {

    private final PaymentService paymentService;
    private final OrderService orderService;

    @Value("${toss.secret-key}")
    private String tossSecretKey;

    @Transactional
    public void confirmPayment(PaymentInfoRequest request) {

        Orders orders = orderService.selectOrderAndStatus(request.getOrderId(), OrderStatus.PENDING);
        if (orders.getTotalPrice().compareTo(request.getAmount()) != 0) throw BusinessException.badRequest("주문금액이 맞지않습니다.");

        paymentService.processPaymentSuccess(PaymentInfoDto.toDto(request));

        Payment paymentInfo = paymentService.getPaymentInfo(request.getOrderId());
        paymentInfo.updateStatus(request.getPaymentMethod());

    }
}
