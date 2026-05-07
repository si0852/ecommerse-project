package com.project.ecommerce.domain.product.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
@EntityListeners(AuditingEntityListener.class)
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @Column(nullable = false)
//    private long productOptionId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_option_id", unique = true)
    ProductOption productOption;

    @Column(nullable = false)
    private int quantity;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
