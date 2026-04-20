package com.project.ecommerce.domain.product.repository;

import com.project.ecommerce.domain.product.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductsRepository extends JpaRepository<Products, Long> {

    Optional<Products> findById(Long id);
}
