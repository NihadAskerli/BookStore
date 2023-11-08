package com.example.bookstore.controller;

import com.example.bookstore.models.base.BaseResponse;
import com.example.bookstore.models.response.book.BookResponse;
import com.example.bookstore.models.response.book.ReadBook;
import com.example.bookstore.service.student.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/v1/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    @GetMapping("/read")
    public BaseResponse<List<ReadBook>>getAllReadBook(@RequestHeader(HttpHeaders.AUTHORIZATION)String token){
        return BaseResponse.success(studentService.getAllRead(token));
    }
}
