package com.accolite.BookStore.repository;

import com.accolite.BookStore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
