package com.example.bookstore.repo;

import com.example.bookstore.models.entities.Author;
import com.example.bookstore.models.entities.Student;
import com.example.bookstore.models.entities.Subscribe;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SubscribeRepoTest {
    @Autowired
    private SubscribeRepo subscribeRepo;

    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private AuthorRepo authorRepo;


    @Test
    public void findAllByAuthorTest() {
        Author author = Author.builder().fullName("Nihad Esgerli").email("esgerlinihad7@gmail.com").role("ROLE_STUDENT").isActive(true).password("34343").build();
        authorRepo.save(author);
        Student student = Student.builder().fullName("Test").email("test@gmail.com").isActive(true).role("ROLE_STUDENT").build();
        studentRepo.save(student);
        Subscribe subscribe1=subscribeRepo.save(Subscribe.builder().date(LocalDateTime.now()).student(student).author(author).build());
        List<Subscribe> list = subscribeRepo.findAllByAuthor(author);
        Assertions.assertThat(list).anyMatch(subscribe -> subscribe.getStudent().equals(student));

    }

}
