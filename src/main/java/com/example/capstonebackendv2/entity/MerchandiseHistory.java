package com.capstone.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_history", schema = "retail_management")
@Entity(name = "product_history")
public class MerchandiseHistory {
    @Id @Column(name = "no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String num;
    private String id;
    private String name;
    private BigDecimal price;
    @Column(name = "mark")
    private Double markPrice;
    @Column(name = "mark_percent")
    private Double markPercent;
    @Column(name = "is_mark_up")
    private String isMarkUp;
    @Column(name = "created_at", updatable = false, insertable = false)
    private String createdAt;
}
