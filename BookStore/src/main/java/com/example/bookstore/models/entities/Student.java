package com.example.bookstore.models.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "students")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Student implements Serializable {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_student"
    )
    @SequenceGenerator(
            name = "seq_student",
            allocationSize = 1
    )
    Long id;
    @Column(name = "full_name")
    String fullName;
    String email;
    String password;
    String role;
    Boolean isActive;
    @OneToMany(mappedBy = "student")
    List<Read> read;
    @OneToMany(mappedBy = "student")
    List<Subscribe>subscribes;

}
