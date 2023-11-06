
package com.example.bookstore.service.student;

import com.example.bookstore.exception.BaseException;
import com.example.bookstore.models.entities.Student;
import com.example.bookstore.repo.StudentRepo;
import com.example.bookstore.service.jwt.AccessTokenManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepo studentRepo;
    private final AccessTokenManager accessTokenManager;


    @Override
    public Student saveStudent(Student user) {
        return studentRepo.save(user);
    }

    @Override
    public Student findStudentByEmail(String email,boolean value) {
        return studentRepo.findByEmailAndIsActive(email,value).orElseThrow(
                () -> BaseException.notFound(Student.class.getSimpleName(), "email", email)
        );
    }

    @Override
    public boolean checkEmail(String email) {
        return studentRepo.existsByEmail(email);
    }




}
