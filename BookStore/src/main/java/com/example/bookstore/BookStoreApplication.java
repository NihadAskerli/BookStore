package com.example.bookstore;

import com.example.bookstore.models.dto.kafka.KafkaDto;
import com.example.bookstore.utils.EmailUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
@RequiredArgsConstructor
public class BookStoreApplication  implements CommandLineRunner {
private final ObjectMapper objectMapper;

    public static void main(String[] args) {
        SpringApplication.run(BookStoreApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        String message = "yeni bir kitab yaradildi " +
//                "adi-" +"test " +
//                "muellif adi-" + "test";
////        objectMapper.writeValue(new File("test.json"),kafkaDto);
//        String kafkaDto=KafkaDto.builder().message(message).email("test@gmail.com").build().toString();
//        KafkaDto kafkaDto1=objectMapper.readValue(kafkaDto,KafkaDto.class);
//        System.out.println(kafkaDto1);
    }
}
