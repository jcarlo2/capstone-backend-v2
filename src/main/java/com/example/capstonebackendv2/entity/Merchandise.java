package com.capstone.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product", schema = "retail_management")
@Entity(name = "product")
public class Merchandise {
    @Id
    private String id;
    private String name;
    private BigDecimal price;
    @Column(name = "quantity_per_pieces")
    private Integer quantityPerPieces;
    @Column(name = "pieces_per_box")
    private Integer piecesPerBox;
    @Column(name = "quantity_per_box",insertable = false)
    private Double quantityPerBox;
    @Column(name = "is_active")
    private String isActive;

    @Transient
    private Double markPrice;
    @Transient
    private Double markPercent;
    @Transient
    private String isMarkUp;

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Merchandise c))  return false;
        return c.getName().equals(name) &&
                c.getPrice().compareTo(price) == 0 &&
                c.getId().equals(id) &&
                c.getPiecesPerBox().equals(piecesPerBox);
    }
}
