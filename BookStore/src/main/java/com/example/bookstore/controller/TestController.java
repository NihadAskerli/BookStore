package com.example.bookstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {
    @GetMapping("/get")
    public String test(){
        return "student daxil oldu";
    }
    @GetMapping("/test2")
    public String tests(){
        return "author daxil oldu";
    }
}
