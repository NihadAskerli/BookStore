package com.example.bookstore.repo;

import com.example.bookstore.models.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student,Long> {
    Optional<Student> findByEmailAndIsActive(String email, boolean isActive);
    boolean existsByEmail(String email);
}
