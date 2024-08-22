package com.example.spring_boot.repositories;

import com.example.spring_boot.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository <Address, Long> {
}
