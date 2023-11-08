package com.example.bookstore.service.subscribe;

import com.example.bookstore.models.payload.subscribe.SubscribePayload;

public interface SubscribeService {
      void saveSubscribe(SubscribePayload subscribePayload,String token);
}
