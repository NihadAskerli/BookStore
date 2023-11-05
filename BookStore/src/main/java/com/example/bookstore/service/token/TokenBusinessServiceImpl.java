
package com.example.bookstore.service.token;



import com.example.bookstore.models.entities.Token;
import com.example.bookstore.service.security.student.AccessTokenManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenBusinessServiceImpl implements TokenService {
    private final TokenServiceImpl tokenService;
    private final AccessTokenManager accessTokenManager;

    @Override
    public void saveToken(Token token) {
        tokenService.save(token);
    }

    @Override
    public void deleteToken(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        String email = accessTokenManager.getEmail(token);
        tokenService.delete(email);
    }

    @Override
    public List<Token> getAllToken(String email) {
        return tokenService.getAllToken(email);
    }

    @Override
    public boolean tokenExist(String token,String email) {
        if (null != email) {
            Token checkToken = tokenService.getTokenByEmail(email);
            if (checkToken != null) {
                return checkToken.getToken().stream().anyMatch(s -> s.equals(token));
            }
        }
        return false;
    }

    @Override
    public Token getTokenByEmail(String email) {
        return tokenService.getTokenByEmail(email);
    }
}
