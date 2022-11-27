package com.accolite.BookStore.service;

import com.accolite.BookStore.entity.User;
import com.accolite.BookStore.repository.BookRepository;
import com.accolite.BookStore.repository.BorrowRepository;
import com.accolite.BookStore.repository.ReviewRepository;
import com.accolite.BookStore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookStoreServiceImpl implements BookStoreService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private BorrowRepository borrowRepository;

    @Override
    public User createUser(User user)
    {
        return userRepository.save(user);
    }

    @Override
    public ResponseEntity<User> updateUser(long id, User user) {
        Optional<User> userDetails = this.userRepository.findById(id);

        if (userDetails.isPresent()) {
            User updatedDetails = userDetails.get();
            updatedDetails.setUsername(user.getUsername());
            updatedDetails.setUserPhone(user.getUserPhone());
            updatedDetails.setUserMailId(user.getUserMailId());
            updatedDetails.setBooksBorrowed(user.getBooksBorrowed());
            updatedDetails.setWallet(user.getWallet());
            updatedDetails.setSuspendStatus(user.getSuspendStatus());
            return new ResponseEntity<>(userRepository.save(updatedDetails), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Override
    public ResponseEntity<User> suspendUser(long id)
    {
        Optional<User> userDetails = this.userRepository.findById(id);

        if (userDetails.isPresent()) {
            User suspendUser = userDetails.get();
            suspendUser.setSuspendStatus(true);
            return new ResponseEntity<>(userRepository.save(suspendUser), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<User> getUsers(){
        return this.userRepository.findAll();
    }
}
