package com.sandbox.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "currencies")
@Data
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private String name;

}
