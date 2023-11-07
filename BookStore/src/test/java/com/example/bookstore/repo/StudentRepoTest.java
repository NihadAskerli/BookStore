package com.example.bookstore.repo;

import com.example.bookstore.models.entities.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class StudentRepoTest {
    @Autowired
    private StudentRepo studentRepo;

    @Test
    public void findByEmailAndIsActive() {

            Student student = Student.builder().fullName("Test").email("test@gmail.com").isActive(true).role("ROLE_STUDENT").build();
            studentRepo.save(student);
            Student student1 = studentRepo.findByEmailAndIsActive("test@gmail.com",true).get();
            Assertions.assertThat(student1).isNotNull();
            Assertions.assertThat(student1.getId()).isGreaterThan(0);

    }

}
