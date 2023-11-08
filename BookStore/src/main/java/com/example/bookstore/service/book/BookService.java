package com.example.bookstore.service.book;

import com.example.bookstore.models.entities.Book;
import com.example.bookstore.models.payload.book.BookPayload;
import com.example.bookstore.models.response.book.BookResponse;
import com.example.bookstore.models.response.student.StudentResponse;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BookResponse> getAllBook();
    void saveBook(BookPayload bookPayload,String token);
    Book findBookByName(String bookName);
    void deleteBook(Long id,String token);
}
