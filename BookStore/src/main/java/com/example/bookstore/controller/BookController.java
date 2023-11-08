package com.example.bookstore.controller;

import com.example.bookstore.models.base.BaseResponse;
import com.example.bookstore.models.payload.book.BookPayload;
import com.example.bookstore.models.response.book.BookResponse;
import com.example.bookstore.service.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public BaseResponse<List<BookResponse>> getAllBook() {
        return BaseResponse.success(bookService.getAllBook());
    }

    @PostMapping
    public BaseResponse<Void> saveBook(@RequestBody BookPayload bookPayload, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        bookService.saveBook(bookPayload, token);
        return BaseResponse.success();
    }
    @DeleteMapping
    public BaseResponse<Void>deleteBook(@RequestParam("id")Long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        bookService.deleteBook(id,token);
        return BaseResponse.success();
    }
}
