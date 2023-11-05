package com.example.bookstore.service.base;

public interface TokenReader <T> {

    T read(String token);

}
