package com.example.bookstore.service.book;

import com.example.bookstore.exception.BaseException;
import com.example.bookstore.models.entities.Author;
import com.example.bookstore.models.entities.Book;
import com.example.bookstore.models.entities.Student;
import com.example.bookstore.models.payload.book.BookPayload;
import com.example.bookstore.models.response.book.BookResponse;
import com.example.bookstore.models.response.student.StudentResponse;
import com.example.bookstore.repo.BookRepo;
import com.example.bookstore.service.author.AuthorService;
import com.example.bookstore.service.jwt.AccessTokenManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {
    private final BookRepo bookRepo;
    private final ObjectMapper objectMapper;
    private final AccessTokenManager accessTokenManager;
    private final AuthorService authorService;

    @Override
    public List<BookResponse> getAllBook() {
        return bookRepo.findAll().stream().map(this::convertBookResponse).toList();
    }

    @Override
    public void saveBook(BookPayload bookPayload, String token) {
        String email=accessTokenManager.getEmail(token.substring(7));
        Book book = objectMapper.convertValue(bookPayload, Book.class);
        Author author=authorService.findAuthorByEmail(email,true);
        book.setAuthor(author);
        bookRepo.save(book);
    }

    @Override
    public List<StudentResponse> getAllStudentByBook(String bookName) {
        List<Student> students=bookRepo.findBookByName(bookName)
                .orElseThrow(() -> BaseException.notFound(Book.class.getName(),"bookName",bookName)).getStudents();
        return students.stream().map(this::convertStudentResponse).toList();
    }

    //    private method
    private BookResponse convertBookResponse(Book book) {
        return BookResponse.builder().name(book.getName()).authorName(book.getAuthor().getFullName()).build();
    }
    private StudentResponse convertStudentResponse(Student student) {
        return StudentResponse.builder().fullName(student.getFullName()).build();
    }
}
