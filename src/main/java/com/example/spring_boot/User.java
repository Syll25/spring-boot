package com.example.spring_boot;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column
    public Long id;
    @Column
    public String name;
    @Column
    public String email;
    @Column
    public String password;

    @ManyToOne
    @JoinColumn(name = "address_id")
    public Address address;

}
