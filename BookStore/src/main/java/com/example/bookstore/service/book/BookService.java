package com.example.bookstore.service.book;

import com.example.bookstore.models.payload.book.BookPayload;
import com.example.bookstore.models.response.book.BookResponse;
import com.example.bookstore.models.response.student.StudentResponse;

import java.util.List;

public interface BookService {
    List<BookResponse> getAllBook();
    void saveBook(BookPayload bookPayload,String token);
    List<StudentResponse> getAllStudentByBook(String bookName);
}
