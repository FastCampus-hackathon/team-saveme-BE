package com.saveme.comparator.domain;


import com.saveme.comparator.dto.JobDataDto;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Entity
public class Recruition {

    @Id
    private Long recruitmentId;

    private String url;
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

    @OneToMany(mappedBy = "recruition")
    private final List<Wish> wishList = new ArrayList<>();

    public static Recruition createRecruition(JobDataDto jobDataDto){

        return Recruition
                .builder()
                .url(jobDataDto.getUrl())
                .locationName(jobDataDto.getLocationName())
                .recruitmentId(Long.parseLong(jobDataDto.getRecruitmentId()))
                .companyHref(jobDataDto.getCompanyHref())
                .companyName(jobDataDto.getCompanyName())
                .positionTitle(jobDataDto.getPositionTitle())
                .industryName(jobDataDto.getIndustryName())
                .jobType(jobDataDto.getJobType())
                .salaryName(jobDataDto.getSalaryName())
                .requiredEducationLevel(jobDataDto.getRequiredEducationLevel())
                .experienceLevel(jobDataDto.getExperienceLevel())
                .expirationDate(jobDataDto.getExpirationDate())
                .applyCnt(jobDataDto.getApplyCnt())
                .build();
    }

}
