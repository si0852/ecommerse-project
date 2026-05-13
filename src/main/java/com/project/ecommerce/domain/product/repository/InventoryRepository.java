package com.project.ecommerce.domain.product.repository;

import com.project.ecommerce.domain.product.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Inventory i " +
            "SET i.quantity = i.quantity - :reqQuantity " +
            "WHERE i.productOption.id = :productOptionId and i.quantity >= :reqQuantity")
    int decreaseStock(@Param("productOptionId") Long productOptionId, @Param("reqQuantity")int reqQuantity);
}
