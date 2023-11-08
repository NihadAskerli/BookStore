package com.example.bookstore.controller;

import com.example.bookstore.models.base.BaseResponse;
import com.example.bookstore.service.author.AuthorServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/author")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorServiceImpl authorService;

}
