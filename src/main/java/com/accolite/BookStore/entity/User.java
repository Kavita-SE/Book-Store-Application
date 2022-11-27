package com.accolite.BookStore.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private long userId;
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "user_mailID",unique = true)
    private String userMailId;
    @Column(name = "user_phone",unique = true)
    private long userPhone;
    @Column(name = "wallet")
    private double wallet;
    @Column (name = "suspend_status")
    private boolean suspendStatus;
    @Column (name = "books_borrowed")
    private int booksBorrowed;

    public boolean getSuspendStatus() {
        return suspendStatus;
    }
}
