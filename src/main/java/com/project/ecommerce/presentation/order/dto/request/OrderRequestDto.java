package com.project.ecommerce.presentation.order.dto.request;

import com.project.ecommerce.domain.dto.order.ProductOptionDto;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class OrderRequestDto {

    private long productId;
    private String userId;
    private String productName;
    private List<ProductOptionDto> optionData;

    public List<Long> getProductOptionIds() {
        return optionData.stream()
                .map(ProductOptionDto::getProductOptionId)
                .toList();
    }

    public Map<Long, Integer> quantityMap() {
        return optionData.stream()
                .collect(Collectors.toMap(
                        ProductOptionDto::getProductOptionId,
                        ProductOptionDto::getQuantity
                ));
    }
}
