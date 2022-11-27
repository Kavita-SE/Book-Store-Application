package com.accolite.BookStore.service;

import com.accolite.BookStore.entity.*;
import com.accolite.BookStore.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        BookInventory bookInventoryDetails = bookInventory.get();
        Borrow borrowBook = new Borrow();
        if(user.isPresent() && book.isPresent())
        {
            double minimum_balance=0.3*(double)(bookDetails.getPrice());
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

    @Override
    public ResponseEntity<HttpStatus> addCopies(BookInventory bc) {
        Optional<BookInventory> bi = this.bookInventoryRepo.findById(bc.getBId());
        if (bi.isPresent()) {
            BookInventory copiesAdd = bi.get();
            copiesAdd.setCopies(bi.get().getCopies() + bc.getCopies());

            bookInventoryRepo.save(copiesAdd);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            bookInventoryRepo.save(bc);
            return new ResponseEntity<>(HttpStatus.OK);

        }
    }

    @Override
    public ResponseEntity<HttpStatus> returnBook(int borrowId){

        //System.out.println(uId + " " + bookId);
        Optional<Borrow> borrowed = this.borrowRepository.findById((long) borrowId);


        //Optional<BookCopies> bc=bookCopiesRepository.findById(bookId);

        //User user=u.get();
        Borrow b = borrowed.get();

        //Book book=b.get();

        //System.out.println("BOOK---------------------"+book.getBookName());
        //System.out.println("USER---------------------"+user.getUsername());
        //BookCopies bookCopies=bc.get();
        //System.out.println("COPIES---------------------"+bookCopies.getCopies());
        //Rented r=new Rented();

        if(borrowed.isPresent())
        {
            Optional<Book> book = bookRepository.findById(b.getBookId());
            Book bookDetails = book.get();
            double rent_amount=0.1*(double)bookDetails.getPrice();
            double security_deposit=0.2*(double)bookDetails.getPrice();

            Optional<BookInventory> bc=bookInventoryRepo.findById(b.getBookId());
            BookInventory bookCopies=bc.get();

            Optional<User> u=userRepository.findById(b.getUserId());
            User user=u.get();

            user.setWallet(user.getWallet()+security_deposit-rent_amount);
            bookCopies.setCopies(bookCopies.getCopies()+1);
            user.setBooksBorrowed(user.getBooksBorrowed()-1);

            b.setReturnDateTime(LocalDateTime.now());

            borrowRepository.save(b);
            bookInventoryRepo.save(bookCopies);
            userRepository.save(user);


        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<HttpStatus> reviewBook(long uId, long bookId, String review) {

        Optional<User> u = userRepository.findById(uId);
        Optional<Book> b = bookRepository.findById(bookId);
        User user=u.get();
        Book book=b.get();
        Review r=new Review();

        if(u.isPresent() && b.isPresent())
        {
            if(ifRented(uId,bookId)) {
                r.setUserId(user.getUserId());
                r.setBookId(book.getBookId());
                r.setUserReview(review);
                reviewRepository.save(r);
                return new ResponseEntity<>(HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<HttpStatus> likeBook(long uId, long bookId) {
        Optional<User> u = userRepository.findById(uId);
        Optional<Book> b = bookRepository.findById(bookId);
        //User user=u.get();
        Book book=b.get();
        if(u.isPresent() && b.isPresent())
        {
            if(ifRented(uId,bookId)) {
                book.setLikes(book.getLikes() + 1);
                bookRepository.save(book);
                return new ResponseEntity<>(HttpStatus.OK);

            }
            else{
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean ifRented(long uId, long bookId) {
        Optional<User> user = userRepository.findById(uId);
        Optional<Book> book = bookRepository.findById(bookId);
        if(user.isPresent() && book.isPresent()){
            List<Borrow> list= new ArrayList<>();
            list=this.borrowRepository.findAll();
            for (Borrow b:list) {
                if(b.getUserId()==uId && b.getBookId()==bookId){
                    return true;
                }
            }
            return false;
        }
        return false;
    }
}
