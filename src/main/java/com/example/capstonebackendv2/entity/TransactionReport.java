package com.capstone.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
    private String isValid;
    @Column(name = "is_archived",insertable = false,updatable = false)
    private String isArchived;
    @Column(name = "total_amount")
    private String totalAmount;
    @Column(name = "old_id",insertable = false,updatable = false)
    private String oldId;
    private BigDecimal credit;
    @Column(insertable = false)
    private int bir;
}
