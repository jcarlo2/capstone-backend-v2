package com.example.capstonebackendv2.entity;

import com.example.capstonebackendv2.helper.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Table(name = "product_category",schema = "retail_management")
@Entity(name = "product_category")
@Getter
@AllArgsConstructor @NoArgsConstructor
public class MerchandiseCategory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String no;
    private String id;
    @Enumerated(EnumType.STRING)
    private Category category;
}
