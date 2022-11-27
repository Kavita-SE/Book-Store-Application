package com.accolite.BookStore.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "borrow")
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int borrowid;

    @Column(name = "bookId")
    private long bookid;
    @Column(name = "userId")
    private long userdi;
    @Column(name="issue_date_time", columnDefinition = "TIMESTAMP", nullable=false)
    private LocalDateTime issueDateTime;
    @Column(name="return_date_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime returnDateTime;
}

