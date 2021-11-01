package com.twoimpulse.challenge.book;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.nio.file.Files;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {

    @Autowired
    private BookRepository underTest;

    @Test
    void itShouldfindBookByISBN() {

        // given
        String isbn = "9780553471076";

        Book hobbit = new Book(
                isbn,
                "The Hobbit",
                "The Hobbit tells the famous story of Bilbo Baggins...",
                null
        );
        underTest.save(hobbit);

        // when
        Optional<Book> expected = underTest.findByISBN(isbn);

        // then
        assertThat(expected.get()).isEqualTo(hobbit);
    }
}