package com.sandbox.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.sandbox.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WalletDto implements Serializable {
    private  String name;
    @JsonProperty(value = "Default")
    private  boolean _default;
    private  Currency currency;

    }

