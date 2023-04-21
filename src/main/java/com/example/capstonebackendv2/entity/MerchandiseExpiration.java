package com.example.capstonebackendv2.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @ToString
@Table(name = "product_expiration", schema = "retail_management")
@Entity(name = "product_expiration")
public class MerchandiseExpiration {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String no;
    private String id;
    private String name;
    private String timestamp;
    @Column(name = "is_active", insertable = false)
    private String isActive;
    private Integer quantity;
    @Column(name = "report_id")
    private String reportId;
}
