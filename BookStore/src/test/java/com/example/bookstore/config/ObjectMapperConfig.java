package com.example.bookstore.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@TestConfiguration
public class ObjectMapperConfig {
    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
