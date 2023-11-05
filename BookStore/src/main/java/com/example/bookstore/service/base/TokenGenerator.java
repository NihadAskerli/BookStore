package com.example.bookstore.service.base;

public interface TokenGenerator<T>  {

    String generate(T obj);


}

