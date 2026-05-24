package com.project.ecommerce.application.dto;

import com.project.ecommerce.presentation.order.dto.request.CartOrderRequestDto;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Builder
public class CartEntityDto {

    List<CartOrderRequestDto> dto;
    String userId;
    String orderId;

    public static CartEntityDto toCartEntity(List<CartOrderRequestDto> dto, String userId) {
        return new CartEntityDto(dto, userId, "");
    }


}
