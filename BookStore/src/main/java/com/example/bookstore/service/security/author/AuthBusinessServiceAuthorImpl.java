
package com.example.bookstore.service.security.author;


import com.example.bookstore.exception.BaseException;
import com.example.bookstore.models.dto.jwt.JwtDto;
import com.example.bookstore.models.entities.Author;
import com.example.bookstore.models.entities.Token;
import com.example.bookstore.models.payload.auth.LoginPayload;
import com.example.bookstore.models.payload.auth.OtpPayload;
import com.example.bookstore.models.payload.auth.RegisterPayload;
import com.example.bookstore.models.response.auth.LoginResponse;
import com.example.bookstore.models.response.auth.RegisterResponse;
import com.example.bookstore.service.author.AuthorService;
import com.example.bookstore.service.base.AuthBusinessService;
import com.example.bookstore.service.jwt.AccessTokenManager;
import com.example.bookstore.service.jwt.RefreshTokenManager;
import com.example.bookstore.service.token.TokenService;
import com.example.bookstore.utils.EmailUtil;
import com.example.bookstore.utils.OtpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.bookstore.models.response.error.ErrorResponseMessages.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthBusinessServiceAuthorImpl implements AuthBusinessService {
    private final AuthorService authorService;
    private final AuthenticationManager authenticationManager;
    private final AccessTokenManager accessTokenManager;
    private final RefreshTokenManager refreshTokenManager;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final OtpUtil otpUtil;
    private final EmailUtil emailUtil;

    @Override
    public LoginResponse login(LoginPayload payload) {
        if (authorService.findAuthorByEmail(payload.getEmail(), true).getRole().equals("ROLE_AUTHOR")){
            LoginResponse loginResponse = prepareLoginResponse(payload.getEmail(), payload.isRememberMe());
            authenticate(payload);
            return loginResponse;
        } else throw BaseException.of(PERMISSION_ERROR);
    }

    @Override
    public void logout(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        String email = accessTokenManager.getEmail(token);
        tokenService.deleteToken(email);
    }



    @Override
    public RegisterResponse register(RegisterPayload registerPayload) {
        return convertRegisterResponseCompany(registerPayload);
    }


    @Override
    public void verifyOtp(OtpPayload otpPayload) {
        Token token = tokenService.getTokenByEmail(otpPayload.getEmail());
        if (token.getOtp().equals(otpPayload.getOtp())) {
            Author author = authorService.findAuthorByEmail(token.getEmail(), false);
            author.setIsActive(true);
            authorService.saveAuthor(author);
        } else {
            throw BaseException.of(CONFLICT);
        }
    }




    // private util methods

    private void authenticate(LoginPayload request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw BaseException.of(PASSWORD_INCORRECT);
        }
    }

    private LoginResponse prepareLoginResponse(String email, boolean rememberMe) {
        Author author = authorService.findAuthorByEmail(email, true);
        LoginResponse response = LoginResponse.builder()
                .accessToken(accessTokenManager.generate(JwtDto.builder().role(author.getRole()).email(author.getEmail()).id(author.getId()).build()))
                .refreshToken(refreshTokenManager.generate(JwtDto.builder().rememberMe(rememberMe).role(author.getRole()).email(author.getEmail()).id(author.getId()).build()))
                .email(author.getEmail())
                .build();
        Token token = Token.builder().email(response.getEmail()).token(List.of(response.getAccessToken(),response.getRefreshToken())).build();
        tokenService.saveToken(token);
        return response;
    }

    private RegisterResponse convertRegisterResponseCompany(RegisterPayload registerPayload) {
        if (authorService.checkEmail(registerPayload.getEmail())) {
            throw BaseException.of(EMAIL_ALREADY_REGISTERED);
        }
        Author author = objectMapper.convertValue(registerPayload, Author.class);
        author.setRole("ROLE_AUTHOR");
        author.setIsActive(false);
        author.setPassword(passwordEncoder.encode(author.getPassword()));
        Token token = Token.builder().email(author.getEmail()).otp(otpUtil.generateOtp()).build();
        tokenService.saveToken(token);
        try {
            emailUtil.sendOtpEmail(token.getEmail(), token.getOtp());
        } catch (MessagingException e) {
            throw BaseException.of(UNEXPECTED);
        }
        return objectMapper.convertValue(authorService.saveAuthor(author), RegisterResponse.class);
    }
}