package com.example.spring_boot;

import jakarta.persistence.*;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column
    public Long id;
    @Column
    public String street;
    @Column
    public String city;
    @Column
    public String zipCode;
}
