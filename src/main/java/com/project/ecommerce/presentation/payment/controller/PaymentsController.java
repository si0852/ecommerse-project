package com.project.ecommerce.presentation.payment.controller;

import com.project.ecommerce.application.PaymentFacade;
import com.project.ecommerce.application.dto.TossApprovalResponse;
import com.project.ecommerce.common.response.ApiResponse;
import com.project.ecommerce.domain.product.service.ProductsService;
import com.project.ecommerce.presentation.payment.dto.PaymentConfirmResponse;
import com.project.ecommerce.presentation.payment.dto.PaymentInfoRequest;
import com.project.ecommerce.presentation.product.dto.response.ProductsDetailsResponseDto;
import com.project.ecommerce.presentation.product.dto.response.ProductsResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentsController {

    private final PaymentFacade paymentFacade;

    @PostMapping("/confirm")
    public ResponseEntity<ApiResponse<PaymentConfirmResponse>> confirmPayments(@RequestBody PaymentInfoRequest request) {
        return ResponseEntity.status(200).body(ApiResponse.created(paymentFacade.confirmPayment(request)));
    }

}
