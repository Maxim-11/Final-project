package com.example.pr33.repositories;

import com.example.pr33.models.Address;
import com.example.pr33.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findByStreet(String street);
}
