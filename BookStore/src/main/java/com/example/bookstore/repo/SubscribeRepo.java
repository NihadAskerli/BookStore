package com.example.bookstore.repo;

import com.example.bookstore.models.entities.Author;
import com.example.bookstore.models.entities.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscribeRepo extends JpaRepository<Subscribe,Long> {
    List<Subscribe> findAllByAuthor(Author author);
}
