package com.sandbox.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sandbox.entities.User;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class WalletDto implements Serializable {
    private String name;
    @JsonProperty
    private boolean isDefault;
    private String currency;
}
