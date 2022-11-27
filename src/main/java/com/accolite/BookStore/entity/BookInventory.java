package com.accolite.BookStore.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.*;

@Data
@Entity
@Table(name = "book_inventory")
public class BookInventory {

    @Id
    @Column(name="bookid", nullable=false, unique = true)
    private long bId;

    @Column(name = "book_copies", nullable=false)
    private int copies;

}
