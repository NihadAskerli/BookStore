package com.example.bookstore.repo;

import com.example.bookstore.models.entities.Author;
import com.example.bookstore.models.entities.Book;
import com.example.bookstore.models.entities.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AuthorRepoTest {
    @Autowired
    private AuthorRepo authorRepo;


    @Test
    public void saveAuthor() {
        Author author = Author.builder().fullName("Nihad Esgerli").email("esgerlinihad7@gmail.com").role("ROLE_STUDENT").isActive(true).password("34343").build();
        Author author1=authorRepo.save(author);
        Assertions.assertThat(author1).isNotNull();
        Assertions.assertThat(author1.getId()).isGreaterThan(0);
    }
    @Test
    public void findByEmailAndIsActive() {
        Author author = Author.builder().fullName("Nihad Esgerli").email("esgerlinihad7@gmail.com").role("ROLE_STUDENT").isActive(true).password("34343").build();
        authorRepo.save(author);
        Author author2 = authorRepo.findByEmailAndIsActive("esgerlinihad7@gmail.com",true).get();
        Assertions.assertThat(author2).isNotNull();
        Assertions.assertThat(author2.getId()).isGreaterThan(0);
    }
    @Test
    public void existEmail() {
        Author author = Author.builder().fullName("Nihad Esgerli").email("esgerlinihad7@gmail.com").role("ROLE_STUDENT").isActive(true).password("34343").build();
        authorRepo.save(author);
        boolean email = authorRepo.existsByEmail("esgerlinihad7@gmail.com");
        Assertions.assertThat(email).isTrue();
        Assertions.assertThat(email).isNotEqualTo(false);
    }

}
