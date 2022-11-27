package com.accolite.BookStore.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.*;

@Data
@Entity
@Table(name = "Review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reviewid")
    int reviewId;

    @Column(name = "bookid")
    long bookId;

    @Column(name = "userid")
    long userId;

    @Column(name = "user_review")
    String userReview;

}
