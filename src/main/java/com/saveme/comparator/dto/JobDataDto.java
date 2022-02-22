package com.saveme.comparator.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class JobDataDto {
    private String url;
    private String recruitmentId;
    private String companyHref;
    private String companyName;
    private String positionTitle;
    private String industryName;
    private String locationName;
    private String jobType;
    private String salaryName;
    private String requiredEducationLevel;
    private String experienceLevel;
    private String expirationDate;
    private Integer applyCnt;
}
