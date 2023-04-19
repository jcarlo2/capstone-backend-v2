package com.example.capstonebackendv2.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_discount_history", schema = "retail_management")
@Entity(name = "product_discount_history")
public class MerchandiseDiscountHistory {
    @Id @Column(name = "no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String num;
    private String id;
    private Double discount;
    private Integer quantity;
    @Column(name = "archived_at",insertable = false)
    private String timestamp;
}
