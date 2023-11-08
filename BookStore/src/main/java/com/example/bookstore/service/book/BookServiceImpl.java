package com.example.bookstore.service.book;

import com.example.bookstore.exception.BaseException;
import com.example.bookstore.models.dto.kafka.KafkaDto;
import com.example.bookstore.models.entities.Author;
import com.example.bookstore.models.entities.Book;
import com.example.bookstore.models.payload.book.BookPayload;
import com.example.bookstore.models.response.book.BookResponse;
import com.example.bookstore.repo.BookRepo;
import com.example.bookstore.service.author.AuthorService;
import com.example.bookstore.service.jwt.AccessTokenManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.bookstore.models.response.error.ErrorResponseMessages.PERMISSION_ERROR;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {
    private final BookRepo bookRepo;
    private final ObjectMapper objectMapper;
    private final AccessTokenManager accessTokenManager;
    private final AuthorService authorService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public List<BookResponse> getAllBook() {
        return bookRepo.findAll().stream().map(this::convertBookResponse).toList();
    }

    @Override
    public void saveBook(BookPayload bookPayload, String token) {
        String email = accessTokenManager.getEmail(token.substring(7));
        Book book = objectMapper.convertValue(bookPayload, Book.class);
        Author author = authorService.findAuthorByEmail(email, true);
        book.setAuthor(author);
        Book book1 = bookRepo.save(book);
        String message = "new  a book created  " +
                "book name- " + book1.getName() +" "+
                "author name- " + book1.getAuthor().getFullName();
        kafkaTemplate.send("book_notification",KafkaDto.builder().message(message).email(email).build().toString());

    }

    @Override
    public Book findBookByName(String bookName) {
        return bookRepo.findBookByName(bookName)
                .orElseThrow(() -> BaseException.notFound(Book.class.getName(), "bookName", bookName));
    }

    @Override
    public void deleteBook(Long id, String token) {
        Book book=bookRepo.findById(id).orElseThrow(() -> BaseException.notFound(Book.class.getSimpleName(),"data","null"));

        String email=accessTokenManager.getEmail(token.substring(7));

        if(book.getAuthor().getEmail().equals(email)){
            bookRepo.delete(book);
        }else{
            throw  BaseException.of(PERMISSION_ERROR);
        }

    }


    //    private method
    private BookResponse convertBookResponse(Book book) {
        return BookResponse.builder().name(book.getName()).authorName(book.getAuthor().getFullName()).build();
    }

}
