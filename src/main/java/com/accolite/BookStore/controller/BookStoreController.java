package com.accolite.BookStore.controller;

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

    @GetMapping("/listUsers")
    private ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok().body(this.bookStoreService.getUsers());
    }

    @PutMapping("/user/addmoney/{id}/{amount}")
    public ResponseEntity<User> addMoneyUser(@PathVariable long userId, @PathVariable int amount) {
        return bookStoreService.addMoneyUser(userId,amount);
    }
}
