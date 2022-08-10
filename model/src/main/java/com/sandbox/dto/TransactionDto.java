package com.sandbox.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
public class TransactionDto {
    private Long id;
    private BigDecimal amount;
    private Date date;
    private String payer;
    private String note;
    private Long categoryId;
    private Long typeId;
    private Long walletId;
}
