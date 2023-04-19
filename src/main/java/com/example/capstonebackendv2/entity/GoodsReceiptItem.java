package com.capstone.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "product_item", schema = "retail_management")
@Entity(name = "product_item")
public class GoodsReceiptItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private String num;
    @Column(name = "prod_id")
    private String productId;
    private String name;
    @Column(name = "quantity_pieces")
    private Integer quantity;
    private BigDecimal price;
    @Column(name = "mark_price")
    private Double markPrice;
    @Column(name = "mark_percent")
    private Double markPercent;
    @Column(name = "is_mark_up")
    private String isMarkUp;
    @Column(name = "unique_id")
    private String uniqueId;
}
