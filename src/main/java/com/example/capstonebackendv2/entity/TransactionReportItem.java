package com.capstone.backend.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "transaction_report_item", schema = "retail_management")
@Entity(name = "transaction_report_item")
public class TransactionReportItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String num;
    @Column(name = "prod_id")
    private String productId;
    private String name;
    private BigDecimal price;
    private Integer sold;
    @Column(name = "sold_total")
    private String soldTotal;
    @Column(name = "discount_percentage")
    private Double discountPercentage;
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    @Column(name = "unique_id")
    private String uniqueId;
}
