package com.example.bookstore.models.dto.jwt;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class JwtDto {
    Long id;
    String email;
    String role;
    boolean rememberMe;
}
