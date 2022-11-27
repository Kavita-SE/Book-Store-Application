package com.accolite.BookStore.service;

import com.accolite.BookStore.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookStoreService {

        User createUser(User prod);
        ResponseEntity<User> updateUser(long id, User user);
        ResponseEntity<User> suspendUser(long id);
        List<User> getUsers();
        /*Product updateProd(Product prod);
        List<Product> getProducts();
        Product getProdById(long prodId);
        void deleteProd(long prodId);*/
}
