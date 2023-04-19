package com.example.capstonebackendv2.entity;

import lombok.*;

import jakarta.persistence.*;
import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "product", schema = "retail_management")
@Entity(name = "product")
public class Merchandise {
    @Id
    private String id;
    @Column(name = "name")
    private String description;
    private BigDecimal price;
    @Column(name = "quantity_per_pieces")
    private Integer quantity;
    @Column(name = "pieces_per_box")
    private Integer piecesPerBox;
    @Column(name = "quantity_per_box",insertable = false)
    private Double quantityPerBox;
    @Column(name = "is_active")
    private Boolean isActive;

    @Transient
    private Double markPrice;
    @Transient
    private Double markPercent;
    @Transient
    private Boolean isMarkUp;

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Merchandise c))  return false;
        return c.getDescription().equals(description) &&
                c.getPrice().compareTo(price) == 0 &&
                c.getId().equals(id) &&
                c.getPiecesPerBox().equals(piecesPerBox);
    }
}
