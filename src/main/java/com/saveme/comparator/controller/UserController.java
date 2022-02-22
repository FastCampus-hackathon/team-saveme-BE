package com.saveme.comparator.controller;

import com.saveme.comparator.config.security.CustomUserDetails;
import com.saveme.comparator.domain.User;
import com.saveme.comparator.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;


    @GetMapping("/users/me")
    public String getMe(Authentication auth){

        return"sdsd";
    }


    @PostMapping("auth/signup")
    public ResponseEntity<?> registerUser() {

        try {

            User user = User.builder()
                    .email("test@gmail.com")
                    .password(bCryptPasswordEncoder.encode("1234"))
                    .role("user")
                    .build();

            userService.createUser(user);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
