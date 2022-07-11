package com.sandbox;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_user")
    private Long id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String mail;

    @Column
    private String username;

    @Column
    private String password;

}