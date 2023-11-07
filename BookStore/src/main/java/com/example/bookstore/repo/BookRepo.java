package com.example.bookstore.repo;

import com.example.bookstore.models.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepo extends JpaRepository<Book,Long> {
    Optional<Book>findBookByName(String name);
}
