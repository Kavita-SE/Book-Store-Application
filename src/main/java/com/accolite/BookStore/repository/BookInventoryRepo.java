package com.accolite.BookStore.repository;

import com.accolite.BookStore.entity.Book;
import com.accolite.BookStore.entity.BookInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookInventoryRepo extends JpaRepository<BookInventory, Long> {

}
