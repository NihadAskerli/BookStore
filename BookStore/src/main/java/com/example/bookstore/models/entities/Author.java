package com.example.bookstore.models.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "authors")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Author implements Serializable {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_author"
    )
    @SequenceGenerator(
            name = "seq_author",
            allocationSize = 1
    )
    Long id;
    String name;
    String email;
    String password;
    String role;
    Boolean isActive;
    @OneToMany(mappedBy = "author")
    List<Book> book;




}
