package com.accolite.BookStore.entity;

import lombok.Data;
import javax.persistence.*;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="bookid", nullable=false)
    private long bookId;

    @Column(name="bookname", nullable=false)
    private String bookName;

    @Column(name="author", nullable=false)
    private String author;

    @Column(name="price", nullable=false)
    private double price;

    @Column(name="category", nullable=false)
    private String category;

    @Column(name="likes")
    private int likes;

    @Column(name="log", columnDefinition = "TIMESTAMP")
    private LocalDateTime localDateTime;


}
