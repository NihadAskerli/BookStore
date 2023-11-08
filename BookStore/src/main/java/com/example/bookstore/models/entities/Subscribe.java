package com.example.bookstore.models.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "subscribes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Subscribe {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_read"
    )
    @SequenceGenerator(
            name = "seq_read",
            allocationSize = 1
    )
    Long id;
    LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "student_id",referencedColumnName = "id")
    Student student;
    @ManyToOne
    @JoinColumn(name = "author_id",referencedColumnName = "id")
    Author author;


}
