package com.project.ecommerce.domain.product.repository;

import com.project.ecommerce.domain.product.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductsRepository extends JpaRepository<Products, Long> {

    @Query("select distinct p from Products p " +
            "join fetch p.productOptions o " +
            " join fetch o.inventory " +
            "where p.id = :productId")
    Optional<Products> findById(@Param("productId") Long id);
}
