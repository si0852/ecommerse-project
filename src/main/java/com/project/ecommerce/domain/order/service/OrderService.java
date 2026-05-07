package com.project.ecommerce.domain.order.service;

import com.project.ecommerce.domain.dto.order.OrderData;
import com.project.ecommerce.domain.dto.order.OrderItemData;
import com.project.ecommerce.domain.order.entity.OrderItem;
import com.project.ecommerce.domain.order.entity.Orders;

public interface OrderService {

    Orders generateOrder(OrderData data); // 주문생성

//    OrderItem generateOrderItem(OrderItemData data); // 주문상세생성

    void modifyOrder(); // 주문 수정

    void cancelOrder(); // 주문취소

    void selectOrder(); // 주문조회(상세)

    void selectOrders(); // 주문조회(2개 이상)
}
