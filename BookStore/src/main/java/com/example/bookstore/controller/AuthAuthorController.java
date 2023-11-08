package com.example.bookstore.controller;

import com.example.bookstore.models.base.BaseResponse;
import com.example.bookstore.models.payload.auth.LoginPayload;
import com.example.bookstore.models.payload.auth.OtpPayload;
import com.example.bookstore.models.payload.auth.RefreshTokenPayload;
import com.example.bookstore.models.payload.auth.RegisterPayload;
import com.example.bookstore.models.response.auth.LoginResponse;
import com.example.bookstore.models.response.auth.RegisterResponse;
import com.example.bookstore.service.base.AuthBusinessService;
import com.example.bookstore.service.security.author.AuthBusinessServiceAuthorImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/auth")
public class AuthAuthorController {
    private final AuthBusinessService authBusinessService;
    public AuthAuthorController(AuthBusinessServiceAuthorImpl authBusinessServiceAuthor){
        authBusinessService=authBusinessServiceAuthor;
    }
    @PostMapping("/login")
    public BaseResponse<LoginResponse> login(@RequestBody
                                             @Valid LoginPayload payload) {
        return BaseResponse.success(authBusinessService.login(payload));
    }
    @PostMapping("/otp")
    public BaseResponse<Void> updatePassword(@RequestBody
                                             @Valid OtpPayload otpPayload) {
        authBusinessService.verifyOtp(otpPayload);
        return BaseResponse.success();
    }
    @PostMapping("/logout")
    public BaseResponse<Void> logout(HttpServletRequest httpServletRequest) {
        authBusinessService.logout(httpServletRequest);
        return BaseResponse.success();
    }
    @PostMapping("/register")
    public BaseResponse<RegisterResponse> register(@RequestBody
                                                   @Valid RegisterPayload registerPayload) {
        return BaseResponse.success(authBusinessService.register(registerPayload));
    }
    @PostMapping("/token/refresh")
    public BaseResponse<LoginResponse> refresh(@RequestBody
                                               RefreshTokenPayload payload) {
        return BaseResponse.success(authBusinessService.refresh(payload));
    }
}
