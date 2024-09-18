package com.example.spring_boot.models;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column
    public Long id;

    @Column(nullable = false, unique = true)
    public String name;

    @Column
    public String email;

    @Column(nullable = false)
    public String password;

    @Column
    public int age;

    @ManyToOne
    @JoinColumn(name = "address_id")
    public Address address;

    public User(String email, String password, String name, int age) {
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }
}
