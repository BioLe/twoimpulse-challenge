package com.twoimpulse.challenge.book;

import com.twoimpulse.challenge.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public Book getBook(String isbn) {

        Optional<Book> fetchBook = bookRepository.findByISBN(isbn);
        if(!fetchBook.isPresent()){
            throw new EntityNotFoundException("No book found with ISBN = " + isbn);
        }

        return fetchBook.get();
    }
}
