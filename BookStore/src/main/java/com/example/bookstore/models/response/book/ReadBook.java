package com.example.bookstore.models.response.book;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReadBook {
    String name;
    LocalDate beginDate;
    String authorName;
}
