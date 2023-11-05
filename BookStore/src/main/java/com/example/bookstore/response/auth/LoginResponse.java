package com.example.bookstore.response.auth;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginResponse {

    String accessToken;
//    implementasiya etmek olar methodlar komentlenibdir sadece teleblerde yoxdur deye etmedim
//    String refreshToken;
    String email;








}
