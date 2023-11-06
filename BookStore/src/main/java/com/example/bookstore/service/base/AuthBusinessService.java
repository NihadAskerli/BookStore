
package com.example.bookstore.service.base;


import com.example.bookstore.models.payload.auth.LoginPayload;
import com.example.bookstore.models.payload.auth.OtpPayload;
import com.example.bookstore.models.payload.auth.RegisterPayload;
import com.example.bookstore.response.auth.LoginResponse;
import com.example.bookstore.response.auth.RegisterResponse;
import jakarta.servlet.http.HttpServletRequest;


public interface AuthBusinessService {

    LoginResponse login(LoginPayload payload);


    void logout(HttpServletRequest httpServletRequest);



    RegisterResponse register(RegisterPayload registerPayload);


    void verifyOtp(OtpPayload otpPayload);




}
