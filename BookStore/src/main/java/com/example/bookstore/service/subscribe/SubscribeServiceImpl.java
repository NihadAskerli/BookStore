package com.example.bookstore.service.subscribe;

import com.example.bookstore.exception.BaseException;
import com.example.bookstore.models.dto.kafka.KafkaDto;
import com.example.bookstore.models.entities.Author;
import com.example.bookstore.models.entities.Student;
import com.example.bookstore.models.entities.Subscribe;
import com.example.bookstore.models.payload.subscribe.SubscribePayload;
import com.example.bookstore.repo.SubscribeRepo;
import com.example.bookstore.service.author.AuthorService;
import com.example.bookstore.service.jwt.AccessTokenManager;
import com.example.bookstore.service.student.StudentService;
import com.example.bookstore.utils.EmailUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SubscribeServiceImpl implements SubscribeService {
    private final EmailUtil emailUtil;
    private final AuthorService authorService;
    private final ObjectMapper objectMapper;
    private final SubscribeRepo subscribeRepo;
    private final AccessTokenManager accessTokenManager;
    private final StudentService studentService;

    @Override
    public void saveSubscribe(SubscribePayload subscribePayload, String token) {
        Subscribe subscribe = Subscribe.builder().date(LocalDateTime.now()).author(authorService.findAuthorByEmail(subscribePayload.getAuthorEmail(), true)).build();
        Student student = studentService.findStudentByEmail(accessTokenManager.getEmail(token.substring(7)), true);
        subscribe.setStudent(student);
        subscribeRepo.save(subscribe);
    }

    @KafkaListener(groupId = "group_id", topics = "book_notification")
    public void getMessage(String value) {
        System.out.println(value);
        KafkaDto data = null;
        try {
            data = objectMapper.readValue(value, KafkaDto.class);
        } catch (JsonProcessingException e) {
            throw BaseException.unexpected();
        }
        System.out.println(data);
        Author author = authorService.findAuthorByEmail(data.getEmail(), true);
        try {
            emailUtil.sendEmailMessage(data.getMessage(), getEmails(author));
        } catch (MessagingException e) {
            throw BaseException.unexpected();
        }
    }


    //    private method
    private List<String> getEmails(Author author) {
        List<Subscribe> subscribes = subscribeRepo.findAllByAuthor(author);
        return subscribes.parallelStream().filter(Objects::nonNull).map(subscribe -> subscribe.getStudent().getEmail()).toList();
    }


}
