package com.example.spring_boot.repositories;

import com.example.spring_boot.models.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void savesEntity() {
        Address address = prepareAddress("124 Main St", "Vancouver", "123456");
        addressRepository.save(address);

        assertNotNull(address.id);
    }

    @Test
    public void findsEntityById() {
        Address address = prepareAddress("124 Main St", "Vancouver", "123456");
        addressRepository.save(address);

        Optional<Address> foundAddress = addressRepository.findById(address.id);

        assertTrue(foundAddress.isPresent());
        assertEquals(address.id, foundAddress.get().id);
        assertEquals(address.street, foundAddress.get().street);
    }

    private static Address prepareAddress(String street, String city, String zipCode) {
        Address address = new Address();
        address.street = street;
        address.city = city;
        address.zipCode = zipCode;
        return address;
    }
}
