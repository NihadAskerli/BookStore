
package com.example.bookstore.service.user;

import com.example.bookstore.exception.BaseException;
import com.example.bookstore.models.entities.Student;
import com.example.bookstore.repo.StudentRepo;
import com.example.bookstore.service.security.student.AccessTokenManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


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

//    @Override
//    public User updateRole(String email, String role) {
//        User user = findUserByEmail(email,true);
//        user.setRole(role);
//        return userRepository.save(user);
//    }
//
//    @Override
//    public List<CarResponse> myRents(HttpServletRequest httpServletRequest) {
//        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
//        User user = findUserByEmail(accessTokenManager.getEmail(token),true);
//        return user.getRent().stream().map(rent -> carBusinessService.convertCarResponse(rent.getCar())).toList();
//    }
//
//    @Override
//    @Transactional
//    public String updateProfile(MultipartFile multipartFile, HttpServletRequest httpServletRequest) {
//        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
//        User user = findUserByEmail(accessTokenManager.getEmail(token),true);
//            user.setPhoto(photoService.save(multipartFile, user));
//        return saveStudent(user).getPhoto().getUrl();
//
//    }
//
//    @Override
//    public void updateDrivingLicense(MultipartFile multipartFile, HttpServletRequest httpServletRequest) {
//        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
//        User user = findUserByEmail(accessTokenManager.getEmail(token),true);
//        user.setDrivingLicenseUrl(photoService.getPhoto(multipartFile, user).getUrl());
//        saveStudent(user);
//
//    }
//
//    @Override
//    public UserResponse findUserByEmail(HttpServletRequest httpServletRequest) {
//        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
//        User user = findUserByEmail(accessTokenManager.getEmail(token),true);
//        return UserResponse.builder().profileUrl(user.getPhoto().getUrl()).build();
//    }


}
