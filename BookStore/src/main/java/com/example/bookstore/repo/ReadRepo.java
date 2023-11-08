package com.example.bookstore.repo;

import com.example.bookstore.models.entities.Book;
import com.example.bookstore.models.entities.Read;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReadRepo extends JpaRepository<Read,Long> {
    List<Read> findAllByBook(Book book);
}
