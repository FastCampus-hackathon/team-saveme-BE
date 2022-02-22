package com.saveme.comparator.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
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
    private LocalDate expirationDate;
    private Integer applyCnt;
}
