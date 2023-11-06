
package com.example.bookstore.service.security.student;

import com.example.bookstore.exception.BaseException;
import com.example.bookstore.models.dto.jwt.JwtDto;
import com.example.bookstore.models.entities.Student;
import com.example.bookstore.models.entities.Token;
import com.example.bookstore.models.payload.auth.LoginPayload;
import com.example.bookstore.models.payload.auth.OtpPayload;
import com.example.bookstore.models.payload.auth.RegisterPayload;
import com.example.bookstore.response.auth.LoginResponse;
import com.example.bookstore.response.auth.RegisterResponse;
import com.example.bookstore.service.base.AuthBusinessService;
import com.example.bookstore.service.jwt.AccessTokenManager;
import com.example.bookstore.service.jwt.RefreshTokenManager;
import com.example.bookstore.service.token.TokenService;
import com.example.bookstore.service.student.StudentService;
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

import static com.example.bookstore.models.response.ErrorResponseMessages.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthBusinessServiceStudentImpl implements AuthBusinessService {
    private final AuthenticationManager authenticationManager;
    private final AccessTokenManager accessTokenManager;
    private final RefreshTokenManager  refreshTokenManager;
    private final StudentService studentService;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final OtpUtil otpUtil;
    private final EmailUtil emailUtil;

    @Override
    public LoginResponse login(LoginPayload payload) {
        LoginResponse loginResponse = prepareLoginResponse(payload.getEmail(), payload.isRememberMe());
        if(studentService.findStudentByEmail(loginResponse.getEmail(),true).getRole().equals("ROLE_STUDENT")){
            authenticate(payload);
            return loginResponse;
        }else throw BaseException.of(PERMISSION_ERROR);

    }



    @Override
    public void logout(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        String email = accessTokenManager.getEmail(token);
        tokenService.deleteToken(email);

    }



    @Override
    public RegisterResponse register(RegisterPayload registerPayload) {
        return convertRegisterResponse(registerPayload);
    }

    @Override
    public void verifyOtp(OtpPayload otpPayload) {
        Token token=tokenService.getTokenByEmail(otpPayload.getEmail());

        if(token.getOtp().equals(otpPayload.getOtp())){
            Student student=studentService.findStudentByEmail(token.getEmail(),false);
            student.setIsActive(true);
            studentService.saveStudent(student);
        }else{
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
            throw BaseException.of(PASSWORD_INCORRECT);
        }
    }

    private LoginResponse prepareLoginResponse(String email, boolean rememberMe) {
            Student student =studentService.findStudentByEmail(email,true);
        LoginResponse  response = LoginResponse.builder()
                .accessToken(accessTokenManager.generate(JwtDto.builder().role(student.getRole()).email(student.getEmail()).id(student.getId()).build()))
                .refreshToken(refreshTokenManager.generate(JwtDto.builder().rememberMe(rememberMe).role(student.getRole()).email(student.getEmail()).id(student.getId()).build()))
                .email(student.getEmail())
                .build();
        Token token=Token.builder().email(response.getEmail()).token(List.of(response.getAccessToken())).build();
            tokenService.saveToken(token);
        return response;
    }

    private RegisterResponse convertRegisterResponse(RegisterPayload registerPayload) {
        if (studentService.checkEmail(registerPayload.getEmail())) {
            throw BaseException.of(EMAIL_ALREADY_REGISTERED);
        }
        Student student = objectMapper.convertValue(registerPayload, Student.class);
        student.setRole("ROLE_STUDENT");
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        student.setIsActive(false);
        Token token=Token.builder().otp(otpUtil.generateOtp()).email(student.getEmail()).build();
        tokenService.saveToken(token);
        try {
            emailUtil.sendOtpEmail(token.getEmail(),token.getOtp());
        } catch (MessagingException e) {
          throw BaseException.of(UNEXPECTED);
        }
        return objectMapper.convertValue(studentService.saveStudent(student), RegisterResponse.class);
    }

}
