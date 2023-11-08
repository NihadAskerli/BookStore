
package com.example.bookstore.service.student;

import com.example.bookstore.models.entities.Student;
import com.example.bookstore.models.response.book.BookResponse;
import com.example.bookstore.models.response.book.ReadBook;

import java.util.List;

public interface StudentService {

    Student saveStudent(Student student);

    Student findStudentByEmail(String email, boolean value);

    boolean checkEmail(String email);

    List<ReadBook> getAllRead(String token);

}
