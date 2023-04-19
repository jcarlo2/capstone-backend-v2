package com.example.capstonebackendv2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_report", schema = "retail_management")
@Entity(name = "product_report")
public class GoodsReceiptReport {
    @Id
    private String id;
    private String user;
    @Column(insertable = false, updatable = false)
    private String date;
    @Column(name = "date_time", insertable = false,updatable = false)
    private String timestamp;
    @Column(name = "is_valid",insertable = false)
    private Boolean isValid;
    @Column(name = "is_archived",insertable = false)
    private Boolean isArchived;
    private String reason;
    private String link;
}
