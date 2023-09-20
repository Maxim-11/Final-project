package com.example.pr33.repositories;

import com.example.pr33.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Rating findByRate(double rate);
}
