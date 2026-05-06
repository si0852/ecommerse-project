package com.project.ecommerce.domain.order.repository;

import com.project.ecommerce.domain.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
}
