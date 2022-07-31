package com.sandbox.dto;


import com.sandbox.entity.Currency;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class WalletDto implements Serializable {
    private  String name;
    private  String isDefault;
    private  Currency currency;

    }

