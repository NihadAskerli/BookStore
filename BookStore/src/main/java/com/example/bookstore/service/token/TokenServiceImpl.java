package com.example.bookstore.service.token;

import com.example.bookstore.models.entities.Token;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl  {


    private final String hashReference = "Token";
    @Resource(name = "template")
    private HashOperations<String, String, Token> hashOperations;


    public void save(Token token) {
        hashOperations.put(hashReference, token.getEmail(), token);
    }


    public void delete(String email) {
        hashOperations.delete(hashReference, email);
    }


    public List<Token> getAllToken(String email) {
        return hashOperations.values(hashReference);
    }

    public Token getTokenByEmail(String email){
        return hashOperations.get(hashReference,email);
    }



}
