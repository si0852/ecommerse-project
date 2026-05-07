package com.project.ecommerce.domain.order.service.impl;

import com.project.ecommerce.domain.dto.order.OrderData;
import com.project.ecommerce.domain.dto.order.OrderItemData;
import com.project.ecommerce.domain.order.entity.OrderItem;
import com.project.ecommerce.domain.order.entity.Orders;
import com.project.ecommerce.domain.order.repository.OrderItemRepository;
import com.project.ecommerce.domain.order.repository.OrderRepository;
import com.project.ecommerce.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public Orders generateOrder(OrderData data) {
        // 주문 생성
        Orders orders = Orders.toOrder(data);

        List<OrderItemData> orderItem = data.getOrderItem();

        for (OrderItemData itemData : orderItem) {
            OrderItem item = OrderItem.builder().productId(itemData.getProductId()).productOptionId(itemData.getProductOptionId())
                    .productName(itemData.getProductName())
                    .quantity(itemData.getQuantity())
                    .totalPrice(itemData.getTotalPrice())
                    .build();
            orders.addOrderItem(item);
        }

        return orderRepository.save(orders);
    }

    @Override
    public void modifyOrder() {

    }

    @Override
    public void cancelOrder() {

    }

    @Override
    public void selectOrder() {

    }

    @Override
    public void selectOrders() {

    }
}
