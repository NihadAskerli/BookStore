
package com.example.bookstore.service.student;

import com.example.bookstore.exception.BaseException;
import com.example.bookstore.models.entities.Read;
import com.example.bookstore.models.entities.Student;
import com.example.bookstore.models.response.book.ReadBook;
import com.example.bookstore.repo.StudentRepo;
import com.example.bookstore.service.jwt.AccessTokenManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class StudentServiceImpl  implements StudentService{

    private final StudentRepo studentRepo;
    private final AccessTokenManager accessTokenManager;





    @Override
    public Student saveStudent(Student student) {
        return  studentRepo.save(student);
    }

    @Override
    public Student findStudentByEmail(String email, boolean value) {
        return     studentRepo.findByEmailAndIsActive(email,value).orElseThrow(
                () -> BaseException.notFound(Student.class.getSimpleName(), "email", email)
        );
    }

    public boolean checkEmail(String email) {
        return studentRepo.existsByEmail(email);
    }
    @Override
    public List<ReadBook> getAllRead(String token) {
        String email=accessTokenManager.getEmail(token.substring(7));
        List<Read> reads=findStudentByEmail(email,true).getRead();
        return reads.stream().map(this::getRead).filter(read -> read.getEndDate()==null).map(this::convertReadBook).toList();
    }

    //    private method
    private ReadBook convertReadBook(Read read){
        return ReadBook.builder().beginDate(read.getBeginDate()).authorName(read.getBook().getAuthor().getFullName()).name(read.getBook().getName()).build();
    }
    private Read getRead(Read read){
        if(read.getEndDate()==null){
            return read;
        }else{
            return Read.builder().endDate(LocalDate.now()).build();
        }
    }



}
