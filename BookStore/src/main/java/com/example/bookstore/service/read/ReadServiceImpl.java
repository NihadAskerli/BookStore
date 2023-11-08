package com.example.bookstore.service.read;

import com.example.bookstore.models.entities.Read;
import com.example.bookstore.models.entities.Student;
import com.example.bookstore.models.payload.read.ReadPayload;
import com.example.bookstore.models.response.student.StudentResponse;
import com.example.bookstore.repo.ReadRepo;
import com.example.bookstore.service.book.BookService;
import com.example.bookstore.service.jwt.AccessTokenManager;
import com.example.bookstore.service.student.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadServiceImpl implements ReadService {
    private final ReadRepo readRepo;
    private final BookService bookService;
    private final StudentService studentService;
    private final AccessTokenManager accessTokenManager;

    @Override
    public List<StudentResponse> getAllStudentByBook(String bookName) {
        System.out.println(bookService.findBookByName(bookName));
        return readRepo.findAllByBook(bookService.findBookByName(bookName)).stream()
                .map(Read::getStudent).map(this::convertStudentResponse).toList();
    }

    @Override
    public void saveRead(ReadPayload readPayload,String token) {
        System.out.println(readPayload.getBookName());
        System.out.println(bookService.findBookByName(readPayload.getBookName()));
        Read read=Read.builder().beginDate(LocalDate.now()).book(bookService.findBookByName(readPayload.getBookName())).build();
        Student student=studentService.findStudentByEmail(accessTokenManager.getEmail(token.substring(7)),true);
        read.setStudent(student);
        readRepo.save(read);
    }

    //private method
    private StudentResponse convertStudentResponse(Student student) {
        return StudentResponse.builder().fullName(student.getFullName()).build();
    }
}
