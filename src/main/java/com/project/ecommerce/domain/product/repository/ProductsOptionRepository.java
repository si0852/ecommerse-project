package com.project.ecommerce.domain.product.repository;

import com.project.ecommerce.domain.product.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductsOptionRepository extends JpaRepository<ProductOption, Long> {

    @Query("select po from ProductOption po join fetch po.product where po.id in :ids")
    List<ProductOption> findAllByIdIn(List<Long> ids);
}
