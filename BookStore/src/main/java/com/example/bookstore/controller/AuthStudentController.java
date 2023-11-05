package com.example.bookstore.controller;

import com.example.bookstore.models.base.BaseResponse;
import com.example.bookstore.models.payload.auth.LoginPayload;
import com.example.bookstore.response.auth.LoginResponse;
import com.example.bookstore.service.base.AuthBusinessService;
import com.example.bookstore.service.security.student.AuthBusinessServiceStudentImpl;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
public class AuthStudentController {
    private final AuthBusinessService authBusinessService;
    public AuthStudentController(AuthBusinessServiceStudentImpl authBusinessServiceStudent){
        authBusinessService=authBusinessServiceStudent;
    }

    @PostMapping("/login")
    public BaseResponse<LoginResponse> login(@RequestBody
                                             @Valid LoginPayload payload) {
        return BaseResponse.success(authBusinessService.login(payload));
    }

}
