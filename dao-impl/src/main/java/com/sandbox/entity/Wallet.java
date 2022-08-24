package com.sandbox.entity;


import com.sandbox.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "wallets")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Wallet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    private String name;

    @Column
    private BigDecimal balance;

    @Column(name = "is_default")
    private boolean defaultWallet;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "is_archived")
    private boolean archiveWallet;
}
