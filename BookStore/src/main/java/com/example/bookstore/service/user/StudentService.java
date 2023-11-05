
package com.example.bookstore.service.user;

import com.example.bookstore.models.entities.Student;

public interface StudentService {

    Student saveStudent(Student student);

    Student findStudentByEmail(String email,boolean value);

    boolean checkEmail(String email);



}
