package com.example.pr33.repositories;

import com.example.pr33.models.Category;
import com.example.pr33.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
