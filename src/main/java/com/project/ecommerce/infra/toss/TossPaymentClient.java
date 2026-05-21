package com.project.ecommerce.infra.toss;

import com.project.ecommerce.application.dto.PaymentInfoDto;
import com.project.ecommerce.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.Base64;

@Component
@RequiredArgsConstructor
public class TossPaymentClient {

    @Value("${toss.secret-key}")
    private String tossSecretKey;

    private final RestClient tossRestClient = RestClient.builder()
            .baseUrl("https://api.tosspayments.com/v1/payments")
            .build();

    public void confirmPayment(PaymentInfoDto dto) {
        String authHeaderValue = "Basic " + Base64.getEncoder().encodeToString((tossSecretKey + ":").getBytes());

        try {
            tossRestClient.post()
                    .uri("/confirm")
                    .header("Authorization", authHeaderValue)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(dto)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException e) {
            String errorBody = e.getResponseBodyAsString();
            throw BusinessException.HttpRequestConfirmError("토스 결제 승인 실패: " + errorBody);
        } catch (Exception e) {
            throw BusinessException.InternalServerError("토스 통신 중 알 수 없는 오류 발생" + e.getMessage());
        }
    }
}
