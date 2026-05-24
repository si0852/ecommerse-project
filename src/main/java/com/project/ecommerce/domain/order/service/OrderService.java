package com.project.ecommerce.domain.order.service;

import com.project.ecommerce.domain.dto.order.OrderData;
import com.project.ecommerce.domain.dto.order.OrderItemData;
import com.project.ecommerce.domain.dto.order.status.OrderStatus;
import com.project.ecommerce.domain.order.entity.OrderItem;
import com.project.ecommerce.domain.order.entity.Orders;

public interface OrderService {

    Orders generateOrder(OrderData data); // 주문생성

    Orders generateOrder(Orders data); // 주문생성

//    OrderItem generateOrderItem(OrderItemData data); // 주문상세생성

    void modifyOrder(); // 주문 수정

    void cancelOrder(); // 주문취소

    Orders selectOrderAndStatus(String order, OrderStatus orderStatus); // 주문조회(상세)

    void selectOrders(); // 주문조회(2개 이상)
}
