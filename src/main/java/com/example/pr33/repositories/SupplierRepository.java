package com.example.pr33.repositories;

import com.example.pr33.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Supplier findBySname(String sname);
}
