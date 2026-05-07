package com.project.ecommerce.application;

import com.project.ecommerce.domain.dto.order.OrderData;
import com.project.ecommerce.domain.dto.order.OrderItemData;
import com.project.ecommerce.domain.order.entity.Orders;
import com.project.ecommerce.domain.order.service.OrderService;
import com.project.ecommerce.domain.payment.service.PaymentService;
import com.project.ecommerce.domain.product.entity.ProductOption;
import com.project.ecommerce.domain.product.service.ProductsService;
import com.project.ecommerce.presentation.order.dto.request.OrderRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderService orderService;
    private final ProductsService productsService;
    private final PaymentService paymentService;

    public void generateOrderService(OrderRequestDto dto) {

        BigDecimal totalPrice = BigDecimal.ZERO;
        
        List<ProductOption> productsOptionData = productsService.getProductsOptionData(dto.getProductOptionIds());
        Map<Long, Integer> quantity = dto.quantityMap();

        List<OrderItemData> orderItemsData = new ArrayList<>();

        for (ProductOption option : productsOptionData) {
            Integer stock = quantity.get(option.getId());
            BigDecimal eachPrice = option.getAdditionalPrice().add(option.getProducts().getPrice()).multiply(BigDecimal.valueOf(stock));
            totalPrice.add(eachPrice);

            orderItemsData.add(OrderItemData.builder().productId(option.getProducts().getId()).productName(option.getProducts().getProductName()).quantity(stock).totalPrice(eachPrice).build());
        }

        OrderData orderData = OrderData.builder().userId(dto.getUserId()).totalPrice(totalPrice).build();

        Orders orders = orderService.generateOrder(orderData);




    }

}
