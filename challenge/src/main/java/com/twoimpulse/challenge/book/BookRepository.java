package com.twoimpulse.challenge.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

    @Query("SELECT b FROM book b WHERE b.isbn = ?1")
    Optional<Book> findByISBN(String isbn);
}
