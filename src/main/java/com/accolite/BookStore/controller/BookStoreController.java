package com.accolite.BookStore.controller;

import com.accolite.BookStore.entity.Book;
import com.accolite.BookStore.entity.BookInventory;
import com.accolite.BookStore.entity.User;
import com.accolite.BookStore.service.BookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookStoreController {
    @Autowired
    private BookStoreService bookStoreService;

    /* --------         User's related TestCases            -------- */
    @PostMapping("/users/add")
    private ResponseEntity<User> saveUser(@RequestBody User user) {
        return ResponseEntity.ok().body(this.bookStoreService.createUser(user));
    }

    @PutMapping("/users/updateUser/{uId}")
    private ResponseEntity<User> updateUser(@PathVariable int uId, @RequestBody User user){

        return bookStoreService.updateUser(uId,user);
    }

    @PutMapping("/users/suspendUser/{userId}")
    private ResponseEntity<User> suspendUser(@PathVariable int userId, @RequestBody User user){
        return bookStoreService.suspendUser(userId);
    }

    @GetMapping("users/listUsers")
    private ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok().body(this.bookStoreService.getUsers());
    }

    @PutMapping("/users/addMoneytoWallet/{userId}/{amount}")
    public ResponseEntity<User> addMoneyToWallet(@PathVariable long userId, @PathVariable int amount) {
        return bookStoreService.addMoneyToWallet(userId,amount);
    }

    /* --------         Book's related TestCases            -------- */

    @PostMapping("/books/addBook")
    private ResponseEntity<Book> saveBook(@RequestBody Book book) {
        return ResponseEntity.ok().body(this.bookStoreService.createBook(book));
    }

    @GetMapping("/books/listBooks")
    private ResponseEntity<List<Book>> getAllBooks(){
        return ResponseEntity.ok().body(this.bookStoreService.getBooks());
    }
    @PutMapping("/users/borrowBook/{userId}/{bookId}")
    private ResponseEntity<HttpStatus> borrowBook(@PathVariable long userId, @PathVariable long bookId){
        return bookStoreService.borrowBook(userId,bookId);
    }

    @PostMapping("/books/addcopies")
    private ResponseEntity<HttpStatus> addCopies( @RequestBody BookInventory bookInventory){
        bookStoreService.addCopies(bookInventory);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/books/returnBook/{borrowId}")
    private ResponseEntity<HttpStatus> returnBook(@PathVariable int borrowId){
        return bookStoreService.returnBook(borrowId);
    }

    @PostMapping("/books/reviewBook/{userId}/{bookId}")
    private ResponseEntity<HttpStatus> reviewBook(@PathVariable int userId,@PathVariable int bookId, @RequestBody String review){
        return bookStoreService.reviewBook(userId,bookId,review);
    }

    @PostMapping("/books/like/{userId}/{bookId}")
    private ResponseEntity<HttpStatus> likeBook(@PathVariable int userId, @PathVariable int bookId){
        return bookStoreService.likeBook(userId,bookId);
    }

}
