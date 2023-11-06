
package com.example.bookstore.service.userdetails;



import com.example.bookstore.models.entities.Author;
import com.example.bookstore.models.entities.Student;
import com.example.bookstore.models.security.LoggedInUserDetails;
import com.example.bookstore.service.author.AuthorServiceImpl;
import com.example.bookstore.service.student.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final StudentService studentService;
    private final AuthorServiceImpl authorService;
    @Override
    public UserDetails loadUserByUsername(String username) {
        if (studentService.checkEmail(username)) {
            Student student = studentService.findStudentByEmail(username,true);
            List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
            authorityList.add(new SimpleGrantedAuthority(student.getRole()));
            return new LoggedInUserDetails(
                    student.getEmail(), student.getPassword(), authorityList
            );
        } else if (authorService.checkEmail(username)) {
            System.out.println(username);
            Author author = authorService.findAuthorByEmail(username,true);
            List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
            authorityList.add(new SimpleGrantedAuthority(author.getRole()));
            return new LoggedInUserDetails(
                    author.getEmail(), author.getPassword(), authorityList
            );

        }else{
            return null;
        }

    }

    public void setAuthentication(String email) {
        UserDetails userDetails = loadUserByUsername(email);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userDetails, userDetails.getAuthorities(), userDetails.getAuthorities())
        );
    }


}
