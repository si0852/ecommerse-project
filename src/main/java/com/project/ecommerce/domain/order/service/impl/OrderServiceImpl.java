package com.project.ecommerce.domain.order.service.impl;

import com.project.ecommerce.common.exception.BusinessException;
import com.project.ecommerce.domain.dto.order.OrderData;
import com.project.ecommerce.domain.dto.order.OrderItemData;
import com.project.ecommerce.domain.dto.order.status.OrderStatus;
import com.project.ecommerce.domain.order.entity.OrderItem;
import com.project.ecommerce.domain.order.entity.Orders;
import com.project.ecommerce.domain.order.repository.OrderItemRepository;
import com.project.ecommerce.domain.order.repository.OrderRepository;
import com.project.ecommerce.domain.order.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    @Override
    public Orders generateOrder(OrderData data) {
        // 주문 생성
        Orders orders = Orders.toOrder(data);

        List<OrderItemData> orderItem = data.getOrderItem();

        for (OrderItemData itemData : orderItem) {
            log.info("itemData: " + itemData.getProductOptionId());
            OrderItem item = OrderItem.builder().productId(itemData.getProductId()).productOptionId(itemData.getProductOptionId())
                    .productName(itemData.getProductName())
                    .quantity(itemData.getQuantity())
                    .totalPrice(itemData.getTotalPrice())
                    .build();
            orders.addOrderItem(item);
        }

        return orderRepository.save(orders);
    }

    @Transactional
    @Override
    public Orders generateOrder(Orders data) {
        return orderRepository.save(data);
    }

    @Override
    public void modifyOrder() {

    }

    @Override
    public void cancelOrder() {

    }

    @Transactional
    @Override
    public Orders selectOrderAndStatus(String orderId, OrderStatus orderStatus) {
        return orderRepository.findByIdAndOrderStatus(orderId, orderStatus).orElseThrow(() -> BusinessException.notFound("주문정보가 존재하지 않습니다."));
    }

    @Override
    public void selectOrders() {

    }
}
