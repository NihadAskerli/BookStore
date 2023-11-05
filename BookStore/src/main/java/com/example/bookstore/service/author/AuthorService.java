package com.example.bookstore.service.author;

import com.example.bookstore.models.entities.Author;
import com.example.bookstore.models.entities.Student;

public interface AuthorService {
    Author saveAuthor(Author author);

    Author findAuthorByEmail(String email,boolean value);

    boolean checkEmail(String email);
}
