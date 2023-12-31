package com.example.bookstore.models.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "authors")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Author implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "full_name")
    String fullName;
    String email;
    String password;
    String role;
    Boolean isActive;
    @OneToMany(mappedBy = "author")
    List<Book> books;
    @OneToMany(mappedBy = "author")
    List<Subscribe> subscribes;



}
