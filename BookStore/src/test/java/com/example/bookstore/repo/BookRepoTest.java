package com.example.bookstore.repo;

import com.example.bookstore.models.entities.Author;
import com.example.bookstore.models.entities.Book;
import com.example.bookstore.models.entities.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BookRepoTest {
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private AuthorRepo authorRepo;


    @Test
    public void findByNameTest() {
        Author author = Author.builder().fullName("Nihad Esgerli").email("esgerlinihad7@gmail.com").role("ROLE_STUDENT").isActive(true).password("34343").build();
        Author author1=authorRepo.save(author);
        Book book = new Book(null, "Test", author1, null);
        bookRepo.save(book);
        Book book1 = bookRepo.findBookByName("Test").get();
        Assertions.assertThat(book1).isNotNull();
        Assertions.assertThat(book1.getId()).isGreaterThan(0);
    }


}
