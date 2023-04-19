package com.example.capstonebackendv2.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "null_item", schema = "retail_management")
@Entity(name = "null_item")
public class InventoryLossItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private String num;
    private String id;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private Double discount;
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    @Column(name = "report_id")
    private String reportId;
    private String reason;
}
