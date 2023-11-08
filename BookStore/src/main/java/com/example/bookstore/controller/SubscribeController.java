package com.example.bookstore.controller;

import com.example.bookstore.models.base.BaseResponse;
import com.example.bookstore.models.payload.book.BookPayload;
import com.example.bookstore.models.payload.subscribe.SubscribePayload;
import com.example.bookstore.service.subscribe.SubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/subscribe")
@RequiredArgsConstructor
public class SubscribeController {
    private final SubscribeService subscribeService;
    @PostMapping
    public BaseResponse<Void> saveBook(@RequestBody SubscribePayload subscribePayload, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        subscribeService.saveSubscribe(subscribePayload, token);
        return BaseResponse.success();
    }
}
