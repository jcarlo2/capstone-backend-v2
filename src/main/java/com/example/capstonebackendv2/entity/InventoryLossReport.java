package com.example.capstonebackendv2.entity;

import lombok.*;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "null_report", schema = "retail_management")
@Entity(name = "null_report")
public class InventoryLossReport {
    @Id
    private String id;
    private String user;
    @Column(name = "total_amount")
    private BigDecimal total;
    @Column(insertable = false,updatable = false)
    private String date;
    @Column(name = "date_time",insertable = false,updatable = false)
    private String timestamp;
    private String link;
    @Column(name = "is_valid",insertable = false)
    private Boolean isValid;
    @Column(name = "is_archived",insertable = false)
    private Boolean isArchived;
    private String reason;
}
