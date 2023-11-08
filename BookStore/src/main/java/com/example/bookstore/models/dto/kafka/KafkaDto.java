package com.example.bookstore.models.dto.kafka;

import jakarta.websocket.OnOpen;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KafkaDto {
    String email;
    String message;

    @Override
    public String toString() {
        return "{\"email\":\""+email+"\",\"message\":\""+message+"\"}";
    }

    }

