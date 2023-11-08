package com.example.bookstore.controller;

import com.example.bookstore.models.base.BaseResponse;
import com.example.bookstore.models.payload.read.ReadPayload;
import com.example.bookstore.models.response.student.StudentResponse;
import com.example.bookstore.service.read.ReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/read")
@RequiredArgsConstructor
public class ReadController {
    private final ReadService readService;

    @GetMapping("/name")
    public BaseResponse<List<StudentResponse>> getAllReader(@RequestParam("name") String name) {
        return BaseResponse.success(readService.getAllStudentByBook(name));
    }

    @PostMapping
    public BaseResponse<Void> getAllReader(@RequestBody ReadPayload readPayload, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        readService.saveRead(readPayload, token);
        return BaseResponse.success();
    }
}
