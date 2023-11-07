package com.example.bookstore.service.author;

import com.example.bookstore.exception.BaseException;
import com.example.bookstore.models.entities.Author;
import com.example.bookstore.models.entities.Book;
import com.example.bookstore.repo.AuthorRepo;
import com.example.bookstore.service.jwt.AccessTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepo authorRepo;
    private final AccessTokenManager accessTokenManager;

    @Override
    public Author saveAuthor(Author author) {
        return authorRepo.save(author);
    }

    @Override
    public Author findAuthorByEmail(String email, boolean value) {
        return authorRepo.findByEmailAndIsActive(email, value).orElseThrow(() -> BaseException.notFound(Author.class.getName(), "email", email));
    }

    @Override
    public boolean checkEmail(String email) {
        return authorRepo.existsByEmail(email);
    }

    @Override
    public void deleteBook(String token, Long id) {
        String email = accessTokenManager.getEmail(token.substring(7));
       Author author=findAuthorByEmail (email, true);
        List<Book>books=author.getBook();
        for (Book book:books
             ) {
            if(book.getId().equals(id)){
                books.remove(book);
            }
        }
        author.setBook(books);
        saveAuthor(author);
    }
}
