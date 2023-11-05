package com.example.bookstore.models.response;

import org.springframework.http.HttpStatus;

public interface ResponseMessages {

    String key();

    String message();

    HttpStatus status();

}
