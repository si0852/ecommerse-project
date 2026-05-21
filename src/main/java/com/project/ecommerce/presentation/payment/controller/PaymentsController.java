package com.project.ecommerce.presentation.payment.controller;

import com.project.ecommerce.application.PaymentFacade;
import com.project.ecommerce.common.response.ApiResponse;
import com.project.ecommerce.domain.product.service.ProductsService;
import com.project.ecommerce.presentation.payment.dto.PaymentInfoRequest;
import com.project.ecommerce.presentation.product.dto.response.ProductsDetailsResponseDto;
import com.project.ecommerce.presentation.product.dto.response.ProductsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentsController {

    private final PaymentFacade paymentFacade;

    @PostMapping("/confirm")
    public ResponseEntity<ApiResponse<Void>> confirmPayments(@RequestBody PaymentInfoRequest request) {
        paymentFacade.confirmPayment(request);
        return ResponseEntity.status(200).body(ApiResponse.ok());
    }

}
