package com.example.bookstore.repo;

import com.example.bookstore.models.entities.Author;
import com.example.bookstore.models.entities.Book;
import com.example.bookstore.models.entities.Read;
import com.example.bookstore.models.entities.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ReadRepoTest {
    @Autowired
    private ReadRepo readRepo;
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private AuthorRepo authorRepo;
    @Autowired
    private BookRepo bookRepo;
    @Test
    public void findAllByBookTest() {
        Student student = Student.builder().fullName("Test").email("test@gmail.com").isActive(true).role("ROLE_STUDENT").build();
        Student student1=studentRepo.save(student);
        Student student3 = Student.builder().fullName("Test2").email("test3@gmail.com").isActive(true).role("ROLE_STUDENT").build();
        Student student2=studentRepo.save(student3);
        Author author = Author.builder().fullName("Nihad Esgerli").email("esgerlinihad7@gmail.com").role("ROLE_STUDENT").isActive(true).password("34343").build();
        Author author1=authorRepo.save(author);
        Book book = new Book(null, "Test", author1, null);
        Book book1=bookRepo.save(book);
        Read read=Read.builder().book(book1).endDate(LocalDate.now()).beginDate(LocalDate.now()).student(student1).build();
        Read read1=readRepo.save(read);
        Read read2=Read.builder().book(book1).endDate(LocalDate.now()).beginDate(LocalDate.now()).student(student2).build();
        Read read3=readRepo.save(read2);
       List<Read> list= readRepo.findAllByBook(book1);
       Assertions.assertThat(list).anyMatch(read::equals);
       Assertions.assertThat(list).contains(read);
    }

}
