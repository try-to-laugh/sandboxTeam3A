package com.sandbox.entities;

import lombok.Data;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "wallets")
@Data
public class Wallet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private BigDecimal balance;

    @Column(nullable = false)
    private boolean isDefault;

    @ManyToOne(optional = false, cascade = CascadeType.DETACH)
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    private Currency currency;

    @ManyToOne(optional = false, cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
