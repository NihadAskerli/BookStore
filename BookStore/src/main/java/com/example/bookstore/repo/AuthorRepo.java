package com.example.bookstore.repo;

import com.example.bookstore.models.entities.Author;
import com.example.bookstore.models.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepo extends JpaRepository<Author, Long> {
    Optional<Author> findByEmailAndIsActive(String email, boolean isActive);

    boolean existsByEmail(String email);
}
