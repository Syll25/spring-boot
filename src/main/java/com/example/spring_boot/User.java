package com.example.spring_boot;

@Entity(tableName = "users")
public class User {

  @Column
  public String name;
  @Column
  public String password;
  @Column
  public String email;
  @Column
  public String address;
}
