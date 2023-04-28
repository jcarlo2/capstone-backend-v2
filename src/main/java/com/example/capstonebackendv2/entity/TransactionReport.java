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
@Table(name = "transaction_report", schema = "retail_management")
@Entity(name = "transaction_report")
@ToString
public class TransactionReport {
    @Id
    private String id;
    private String user;
    @Column(insertable = false,updatable = false)
    private String date;
    @Column(name = "date_time",insertable = false,updatable = false)
    private String timestamp;
    @Column(name = "is_valid",insertable = false,updatable = false)
    private Boolean isValid;
    @Column(name = "is_archived",insertable = false,updatable = false)
    private Boolean isArchived;
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    private BigDecimal credit;
    private BigDecimal payment;
}
