package com.saveme.comparator.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LogInDto {

    private String email;
    private String password;
}
