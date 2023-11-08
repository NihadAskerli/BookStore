package com.example.bookstore.service.read;

import com.example.bookstore.models.entities.Student;
import com.example.bookstore.models.payload.read.ReadPayload;
import com.example.bookstore.models.response.student.StudentResponse;

import java.util.List;

public interface ReadService {
        List<StudentResponse> getAllStudentByBook(String bookName);
        void saveRead(ReadPayload readPayload,String token);
//        void deleteBook(Long id,)

}
