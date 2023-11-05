package com.example.bookstore.models.entities;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token implements Serializable {
    @Column(unique = true)
    private String email;
    private String otp;
    private List<String> token;
}
