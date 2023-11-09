package com.example.bookstore.models.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "all_reads")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Read {
    @Id

    Long id;
    LocalDate beginDate;
    LocalDate endDate;
    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    Student student;
    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    Book book;

}
