package com.project.ecommerce.domain.order.repository;

import com.project.ecommerce.domain.dto.order.status.OrderStatus;
import com.project.ecommerce.domain.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    Optional<Orders> findByIdAndOrderStatus(String id, OrderStatus orderStatus);
}
