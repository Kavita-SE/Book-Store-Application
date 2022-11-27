package com.accolite.BookStore.repository;

import com.accolite.BookStore.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
