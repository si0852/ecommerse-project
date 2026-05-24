package com.project.ecommerce.domain.product.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
@EntityListeners(AuditingEntityListener.class)
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String productName;

//    @Column(nullable = false)
//    private long categoryId;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

//    @Column(nullable = false)
//    private long stock;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL)
    private List<ProductOption> productOptions = new ArrayList<>();

//    @LastModifiedDate
//    @Column(nullable = false)
//    private LocalDateTime updatedAt;

    public int getTotalStock() {
        if (this.productOptions == null) {
            return 0;
        }

        return this.productOptions.stream()
                .map(ProductOption::getInventory)
                .filter(Objects::nonNull)
                .mapToInt(Inventory::getQuantity)
                .sum();
    }
}
