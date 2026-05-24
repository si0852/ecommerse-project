package com.project.ecommerce.presentation.order.dto.request;

import com.project.ecommerce.domain.dto.order.ProductOptionDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
public class OrderGenerateDto {

    private long productId;
    private String userId;
    private String productName;
    private List<ProductOptionDto> optionData;

    public static OrderGenerateDto toOrderGenerateDto(OrderRequestDto dto, String userId) {
        return new OrderGenerateDto(dto.getProductId(), userId, dto.getProductName(), dto.getOptionData());
    }

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
