package com.example.bookstore.service.author;

import com.example.bookstore.exception.BaseException;
import com.example.bookstore.models.entities.Author;
import com.example.bookstore.repo.AuthorRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepo  authorRepo;
    @Override
    public Author saveAuthor(Author author) {
        return authorRepo.save(author);
    }

    @Override
    public Author findAuthorByEmail(String email, boolean value) {
        return authorRepo.findByEmailAndIsActive(email,value).orElseThrow(() -> BaseException.notFound(Author.class.getName(),"email",email));
    }

    @Override
    public boolean checkEmail(String email) {
        return authorRepo.existsByEmail(email);
    }
}
