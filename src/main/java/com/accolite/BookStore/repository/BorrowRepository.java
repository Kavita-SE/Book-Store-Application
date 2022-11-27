package com.accolite.BookStore.repository;

import com.accolite.BookStore.entity.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRepository extends JpaRepository <Borrow, Long> {
}
