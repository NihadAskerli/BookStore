package com.example.bookstore.service.token;

import com.example.bookstore.models.entities.Token;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface TokenService {
    void saveToken(Token token);

    void deleteToken(HttpServletRequest httpServletRequest);
//
    List<Token> getAllToken(String email);
    boolean tokenExist(String token,String email);
    Token getTokenByEmail(String email);
}
