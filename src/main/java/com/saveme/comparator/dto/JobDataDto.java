package com.saveme.comparator.dto;

import com.saveme.comparator.domain.Wish;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    public static JobDataDto createJobDataDtoWithWish(Wish w) {

        return JobDataDto
                .builder()
                .url(w.getRecruition().getUrl())
                .recruitmentId(w.getRecruition().getRecruitmentId().toString())
                .companyHref(w.getRecruition().getCompanyHref())
                .positionTitle(w.getRecruition().getPositionTitle())
                .industryName(w.getRecruition().getIndustryName())
                .locationName(w.getRecruition().getLocationName())
                .jobType(w.getRecruition().getJobType())
                .salaryName(w.getRecruition().getSalaryName())
                .requiredEducationLevel(w.getRecruition().getRequiredEducationLevel())
                .experienceLevel(w.getRecruition().getExperienceLevel())
                .expirationDate(w.getRecruition().getExpirationDate())
                .applyCnt(w.getRecruition().getApplyCnt())
                .build();
    }
}
