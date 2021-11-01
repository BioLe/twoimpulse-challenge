package com.twoimpulse.challenge.book;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity(name = "book")
@Table(name = "book")
@Getter
@Setter
@EqualsAndHashCode
public class Book {

    @Id
    @Column(name = "b_isbn")
    private String isbn;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    @Column(name = "book_cover")
    private byte[] bookCover;

    public Book(){}

    public Book(String isbn, String title, String description, byte[] bookCover){
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.bookCover = bookCover;
    }

}
