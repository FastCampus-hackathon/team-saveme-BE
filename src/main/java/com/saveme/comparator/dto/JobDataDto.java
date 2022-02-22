package com.saveme.comparator.dto;

import com.saveme.comparator.domain.Wish;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class JobDataDto {

    @ApiModelProperty(value="사람인 공고 url",required = true)
    private String url;
    @ApiModelProperty(value="사람인 공고 id",required = true)
    private String recruitmentId;
    @ApiModelProperty(value="회사 info url",required = true)
    private String companyHref;
    @ApiModelProperty(value="회사명",required = true)
    private String companyName;
    @ApiModelProperty(value="타이틀",example = "대기업/차량관련사/내비게이션 SW개발...",required = true)
    private String positionTitle;
    @ApiModelProperty(value="산업이름",example = "솔루션/SI/ERP/CRM",required = true)
    private String industryName;
    @ApiModelProperty(value="지역이름",example = "서울 강남구",required = true)
    private String locationName;
    @ApiModelProperty(value="직업 종류",example = "정규직",required = true)
    private String jobType;
    @ApiModelProperty(value="연봉",example = "연봉",required = true)
    private String salaryName;
    @ApiModelProperty(value="학위",required = true)
    private String requiredEducationLevel;
    @ApiModelProperty(value="년차",required = true)
    private String experienceLevel;
    @ApiModelProperty(value="마감일자",required = true)
    private String expirationDate;
    @ApiModelProperty(value="지원자 수",required = true)
    private Integer applyCnt;
    @ApiModelProperty(value="로그인 User가 즐겨찾기에 추가했는지",required = true)
    private boolean wished;


    public static JobDataDto createJobDataDtoWithWish(Wish w) {

        return JobDataDto
                .builder()
                .url(w.getRecruition().getUrl())
                .recruitmentId(w.getRecruition().getRecruitmentId().toString())
                .companyHref(w.getRecruition().getCompanyHref())
                .companyName(w.getRecruition().getCompanyName())
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
