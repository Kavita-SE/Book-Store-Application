package com.accolite.BookStore.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "borrow")
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int borrowid;

    @Column(name = "bookid")
    private long bookId;
    @Column(name = "userid")
    private long userId;
    @Column(name="issue_date_time", columnDefinition = "TIMESTAMP", nullable=false)
    private LocalDateTime issueDateTime;
    @Column(name="return_date_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime returnDateTime;
}

