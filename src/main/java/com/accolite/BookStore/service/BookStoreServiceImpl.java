package com.accolite.BookStore.service;

import com.accolite.BookStore.entity.Book;
import com.accolite.BookStore.entity.BookInventory;
import com.accolite.BookStore.entity.Borrow;
import com.accolite.BookStore.entity.User;
import com.accolite.BookStore.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Autowired
    private BookInventoryRepo bookInventoryRepo;

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

    @Override
    public ResponseEntity<User> addMoneyToWallet(long id,int amount)
    {
        Optional<User> userDetails = this.userRepository.findById(id);

        if (userDetails.isPresent() && (amount%500 == 0)) {
            User updatedUser = userDetails.get();
            updatedUser.setWallet(updatedUser.getWallet()+amount);
            return new ResponseEntity<>(userRepository.save(updatedUser), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Book createBook(Book book)
    {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getBooks(){
        return this.bookRepository.findAll();
    }
    @Override
    public ResponseEntity<HttpStatus> borrowBook(long userId, long bookId){

        //System.out.println(uId + " " + bookId);
        Optional<User> user = userRepository.findById(userId);
        Optional<Book> book = bookRepository.findById(bookId);
        Optional<BookInventory> bookInventory = bookInventoryRepo.findById(bookId);

        User userDetails = user.get();

        Book bookDetails = book.get();
        System.out.println("BOOK---------------------"+bookDetails.getBookName());
        System.out.println("USER---------------------"+userDetails.getUsername());
        BookInventory bookInventoryDetails = bookInventory.get();
        System.out.println("BOOK---------------------"+bookDetails.getBookName());
        System.out.println("USER---------------------"+userDetails.getUsername());
        System.out.println("COPIES---------------------"+bookInventoryDetails.getCopies());
        Borrow borrowBook = new Borrow();
        if(user.isPresent() && book.isPresent())
        {
            double minimum_balance=0.3*(double)(bookDetails.getPrice());
            //lastrent book wala condition missing
            if(bookInventoryDetails.getCopies()>0 && minimum_balance <= userDetails.getWallet() && userDetails.getBooksBorrowed()<3 && userDetails.getSuspendStatus())
            {
                bookInventoryDetails.setCopies(bookInventoryDetails.getCopies()-1);
                userDetails.setWallet(userDetails.getWallet()-(int)(0.2*(double)(bookDetails.getPrice())));
                userDetails.setBooksBorrowed(userDetails.getBooksBorrowed()+1);

                borrowBook.setUserId(userDetails.getUserId());
                borrowBook.setBookId(bookDetails.getBookId());
                borrowBook.setIssueDateTime(LocalDateTime.now());
                borrowBook.setReturnDateTime(null);
                borrowRepository.save(borrowBook);
                bookInventoryRepo.save(bookInventoryDetails);
                userRepository.save(userDetails);

                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
