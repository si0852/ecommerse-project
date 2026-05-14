package com.project.ecommerce.domain.product.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
@EntityListeners(AuditingEntityListener.class)
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @Column(nullable = false)
//    private long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Products products;

    @Column(nullable = false)
    private String optionName;

    @Column(nullable = false)
    private BigDecimal additionalPrice;

    @OneToOne(mappedBy = "productOption", cascade = CascadeType.ALL)
    private Inventory inventory;
}
