package com.example.bookstore.service.token;

import com.example.bookstore.models.entities.Token;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {


    private final String hashReference = "Token";
    @Resource(name = "template")
    private HashOperations<String, String, Token> hashOperations;



    @Override
    public void saveToken(Token token) {
        hashOperations.put(hashReference, token.getEmail(), token);
    }

    @Override
    public void deleteToken(String email) {
        hashOperations.delete(hashReference, email);

    }

    public List<Token> getAllToken(String email) {
        return hashOperations.values(hashReference);
    }

    @Override
    public boolean tokenExist(String token, String email) {
        if (null != email) {
            Token checkToken = getTokenByEmail(email);
            if (checkToken != null) {
                return checkToken.getToken().stream().anyMatch(s -> s.equals(token));
            }
        }
        return false;
    }

    public Token getTokenByEmail(String email){
        return hashOperations.get(hashReference,email);
    }



}
