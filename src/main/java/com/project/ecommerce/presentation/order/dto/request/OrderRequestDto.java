package com.project.ecommerce.presentation.order.dto.request;

import com.project.ecommerce.domain.dto.order.ProductOptionDto;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {

    private long productId;
    private String productName;
    private List<ProductOptionDto> optionData;


}
