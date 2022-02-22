package com.saveme.comparator.controller;

import com.saveme.comparator.config.security.CustomUserDetails;
import com.saveme.comparator.domain.User;
import com.saveme.comparator.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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


@Api(tags = {"회원가입용 컨트롤러입니다."})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    @ApiOperation(value="회원가입",notes="유저 회원가입. 데이터는 디폴트로 컨트롤러에 고정 시켜놨습니다.")
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
