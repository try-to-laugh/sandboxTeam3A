package com.sandbox.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TransactionsViewParametersDto {

    private String sortParameter;
    private Long page;
    private Long size;
    private String transactionType;
    private List<String> filterBy;
    private Long walletId;
}
