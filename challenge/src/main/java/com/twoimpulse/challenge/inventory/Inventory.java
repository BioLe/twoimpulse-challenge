package com.twoimpulse.challenge.inventory;

import com.twoimpulse.challenge.book.Book;
import com.twoimpulse.challenge.library.Library;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity(name = "inventory")
@Table(name = "inventory")
@Setter
@Getter
@ToString
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "i_id")
    private long inventoryId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "b_isbn", nullable = false)
    private Book book;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "l_id", nullable = false)
    private Library library;

    public Inventory(){}

    public Inventory(Book book, Library library){
        this.book = book;
        this.library = library;
    }
}
