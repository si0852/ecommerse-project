package com.project.ecommerce.application;

import com.project.ecommerce.application.dto.PaymentInfoDto;
import com.project.ecommerce.application.dto.TossApprovalResponse;
import com.project.ecommerce.domain.cart.service.CartService;
import com.project.ecommerce.presentation.payment.dto.PaymentConfirmResponse;
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
    private final CartService cartService;

    @Value("${toss.secret-key}")
    private String tossSecretKey;

    @Transactional
    public PaymentConfirmResponse confirmPayment(PaymentInfoRequest request) {

        Orders orders = orderService.selectOrderAndStatus(request.getOrderId(), OrderStatus.PENDING);
        if (orders.getTotalPrice().compareTo(request.getAmount()) != 0) throw BusinessException.badRequest("주문금액이 맞지않습니다.");

        TossApprovalResponse tossApprovalResponse = paymentService.processPaymentSuccess(PaymentInfoDto.toDto(request));

        Payment paymentInfo = paymentService.getPaymentInfo(request.getOrderId());

//        cartService.get
        orders.updatePayCompleteStatus();
        paymentInfo.updateStatus(request.getPaymentMethod(), tossApprovalResponse.getPaymentKey(), tossApprovalResponse.getApprovedAt());

        return PaymentConfirmResponse.builder().paymentId(paymentInfo.getId()).amount(paymentInfo.getPaymentPrice()).status(paymentInfo.getPaymentStatus().toString())
                .paymentKey(tossApprovalResponse.getPaymentKey()).orderId(paymentInfo.getOrderId()).paidAt(tossApprovalResponse.getApprovedAt()).build();
//        return TossApprovalResponse.builder().paymentKey(paymentInfo.getPaymentKey()).type(paymentInfo.getPaymentStatus().toString())
//                .orderId(paymentInfo.getOrderId()).method(paymentInfo.getPaymentMethod().toString())
//                .approvedAt(paymentInfo.getApprovedAt()).build();
    }
}
