package com.project.ecommerce.application;

import com.project.ecommerce.application.dto.CartEntityDto;
import com.project.ecommerce.common.exception.BusinessException;
import com.project.ecommerce.common.util.GeneratorUtil;
import com.project.ecommerce.domain.dto.order.OrderData;
import com.project.ecommerce.domain.dto.order.OrderItemData;
import com.project.ecommerce.domain.dto.payment.status.PaymentGenerateData;
import com.project.ecommerce.domain.order.entity.OrderItem;
import com.project.ecommerce.domain.order.entity.Orders;
import com.project.ecommerce.domain.order.service.OrderService;
import com.project.ecommerce.domain.payment.service.PaymentService;
import com.project.ecommerce.domain.product.entity.ProductOption;
import com.project.ecommerce.domain.product.entity.Products;
import com.project.ecommerce.domain.product.service.ProductsService;
import com.project.ecommerce.presentation.order.dto.request.CartOrderRequestDto;
import com.project.ecommerce.presentation.order.dto.request.OrderGenerateDto;
import com.project.ecommerce.presentation.order.dto.response.OrderResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderFacade {

    private final OrderService orderService;
    private final ProductsService productsService;
    private final PaymentService paymentService;

    @Transactional
    public OrderResponseDto generateOrderService(OrderGenerateDto dto) {

        productsService.decreaseStock(dto.getOptionData());

        BigDecimal totalPrice = BigDecimal.ZERO;
        
        List<ProductOption> productsOptionData = productsService.getProductsOptionData(dto.getProductOptionIds());
        if (productsOptionData.isEmpty()) {
            throw BusinessException.notExists("존재하지 않는 상품정보입니다.");
        }
        Map<Long, Integer> quantity = dto.quantityMap();

        List<OrderItemData> orderItemsData = new ArrayList<>();

        for (ProductOption option : productsOptionData) {
            Integer stock = quantity.get(option.getId());
            BigDecimal eachPrice = option.getAdditionalPrice().add(option.getProducts().getPrice()).multiply(BigDecimal.valueOf(stock));
            totalPrice = totalPrice.add(eachPrice);

            orderItemsData.add(OrderItemData.builder().productId(option.getProducts().getId()).productName(option.getProducts().getProductName()).quantity(stock).totalPrice(eachPrice).build());
        }

        OrderData orderData = OrderData.builder().orderId(GeneratorUtil.generateOrderNo()).userId(dto.getUserId()).totalPrice(totalPrice).orderItem(orderItemsData).build();

        Orders orders = orderService.generateOrder(orderData);

        PaymentGenerateData paymentData = PaymentGenerateData.builder().userId(orders.getUserId())
                .orderId(orders.getId())
                .paymentPrice(totalPrice).build();

        paymentService.generatePayment(paymentData);

        return OrderResponseDto.builder().orderId(orders.getId()).totalPrice(orders.getTotalPrice().intValue()).build();

    }

    @Transactional
    public OrderResponseDto generateCartOrderService(CartEntityDto dto) {
        List<CartOrderRequestDto> requestDto = dto.getDto();

        productsService.multiDecreaseStock(requestDto);

        dto.setOrderId(GeneratorUtil.generateOrderNo());
        Orders order = Orders.toOrder(dto);

        for (CartOrderRequestDto orderData : requestDto) {
            ProductOption productOptionData = productsService.getProductOptionData(orderData.getProductOptionId());
            Products products = productOptionData.getProducts();
            if (Objects.isNull(productOptionData)) {
                throw BusinessException.notExists("존재하지 않는 상품정보입니다.");
            }

            // OrderItem -> productOption price + product price
            BigDecimal totalPrice = productOptionData.getAdditionalPrice().add(products.getPrice()).multiply(BigDecimal.valueOf(orderData.getQuantity()));

            OrderItem orderItem = OrderItem.builder().productId(products.getId())
                    .productOptionId(productOptionData.getId())
                    .productName(orderData.getProductName())
                    .quantity(orderData.getQuantity())
                    .totalPrice(totalPrice)
                    .build();

            order.addTotalPrice(orderItem);
        }

        Orders orders = orderService.generateOrder(order);

        PaymentGenerateData paymentData = PaymentGenerateData.builder().userId(orders.getUserId())
                .orderId(orders.getId())
                .paymentPrice(orders.getTotalPrice()).build();

        paymentService.generatePayment(paymentData);


        return OrderResponseDto.builder().orderId(orders.getId()).totalPrice(orders.getTotalPrice().intValue()).build();

    }

}
