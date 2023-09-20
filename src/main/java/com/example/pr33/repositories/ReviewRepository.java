package com.example.pr33.repositories;

import com.example.pr33.models.Product;
import com.example.pr33.models.Review;
import com.example.pr33.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByNumber(String number);
}
