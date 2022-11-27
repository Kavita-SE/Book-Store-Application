package com.accolite.BookStore.service;

import com.accolite.BookStore.entity.Book;
import com.accolite.BookStore.entity.BookInventory;
import com.accolite.BookStore.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookStoreService {

        User createUser(User user);
        ResponseEntity<User> updateUser(long id, User user);
        ResponseEntity<User> suspendUser(long id);
        List<User> getUsers();
        ResponseEntity<User> addMoneyToWallet(long id,int amount);


        Book createBook(Book book);
        List<Book> getBooks();
        ResponseEntity<HttpStatus> borrowBook(long uId, long bookId);

        ResponseEntity<HttpStatus> addCopies(BookInventory bc);
        ResponseEntity<HttpStatus> returnBook(int rentedId);

        ResponseEntity<HttpStatus> reviewBook(long uId, long bookId, String review);

        ResponseEntity<HttpStatus> likeBook(long uId, long bookId);

}
