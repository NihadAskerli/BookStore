package com.example.bookstore.service.base;

import com.example.bookstore.models.dto.jwt.JwtDto;
import io.jsonwebtoken.Claims;

public interface JwtBase {

    String generate(JwtDto jwtDto);
    Claims read(String token);


}

