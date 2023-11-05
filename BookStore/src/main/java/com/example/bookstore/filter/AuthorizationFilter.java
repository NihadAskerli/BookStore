
package com.example.bookstore.filter;


import com.company.yellowgo.service.security.company.AccessTokenManagerCompany;
import com.company.yellowgo.service.security.company.AuthBusinessServiceCompany;
import com.company.yellowgo.service.security.user.AccessTokenManager;
import com.company.yellowgo.service.security.user.AuthBusinessService;
import com.company.yellowgo.service.token.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

import static com.company.yellowgo.constants.TokenConstants.PREFIX;


@Component
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private final AccessTokenManager accessTokenManager;
    private final AccessTokenManagerCompany accessTokenManagerCompany;

    private final AuthBusinessServiceCompany authBusinessServiceCompany;
    private final AuthBusinessService authBusinessService;
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println(token);
        if (Objects.nonNull(token) && token.startsWith(PREFIX)) {
            String decodeToken = token.substring(7);
            String email=accessTokenManager.getEmail(decodeToken);
            if (null !=email  && tokenService.tokenExist(decodeToken,email)) {
                authBusinessService.setAuthentication(
                        accessTokenManager.getEmail(
                                decodeToken
                        )
                );

            }
            else if (null !=accessTokenManagerCompany.getEmail(decodeToken)  && tokenService.tokenExist(decodeToken,accessTokenManagerCompany.getEmail(decodeToken))) {
                authBusinessServiceCompany.setAuthentication(
                        accessTokenManagerCompany.getEmail(
                                decodeToken
                        )
                );
            }

        }

        filterChain.doFilter(request, response);
    }
}
