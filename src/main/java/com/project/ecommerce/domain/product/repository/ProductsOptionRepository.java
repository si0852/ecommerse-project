package com.project.ecommerce.domain.product.repository;

import com.project.ecommerce.domain.product.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductsOptionRepository extends JpaRepository<ProductOption, Long> {

    @Query("select po from ProductOption po join fetch po.products where po.id in :ids")
    List<ProductOption> findAllByIdIn(List<Long> ids);

    @Query("select po from ProductOption po join fetch po.inventory where po.id in :optionId")
    Optional<ProductOption> findByOptionId(@Param("optionId") Long id);
}
