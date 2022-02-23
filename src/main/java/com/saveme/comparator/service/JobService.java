package com.saveme.comparator.service;

import com.saveme.comparator.domain.User;
import com.saveme.comparator.dto.JobDataDto;
import com.saveme.comparator.repository.RecruitionRepository;
import com.saveme.comparator.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.saveme.comparator.service.UserService.convertRecruitionType;

@Service
@RequiredArgsConstructor
public class JobService {

    private final WishRepository wishRepository;

    public List<JobDataDto> getJobDataList(Integer start, String keywords, String locationCode, Integer count, User user) {

        URI uri = UriComponentsBuilder
                .fromUriString("https://oapi.saramin.co.kr")
                .path("/job-search")
                .queryParam("access-key", "wrOLLBw1hOrHGHccjXIza8qM9MyLsnABysjWdCAEi7wMzb5wy")
                .queryParam("start", start)
                .queryParam("keywords", keywords)
                .queryParam("loc_cd", locationCode)
                .queryParam("count", count)
                .queryParam("fields", "count").encode().build().toUri();

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), JSONObject.class);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) jsonParser.parse(responseEntity.getBody().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONArray jobs = (JSONArray) ((JSONObject) jsonObject.get("jobs")).get("job");

        List<JobDataDto> jobDataDtos = new ArrayList<>();

        Date timestamp = new Date();
        for (int i = 0; i < jobs.size(); i++) {
            JSONObject job = (JSONObject) jobs.get(i);
            String url = (String) job.get("url");
            JSONObject company = (JSONObject) job.get("company");
            JSONObject detail = (JSONObject) company.get("detail");
            String companyName = (String) detail.get("name");
            String companyHref = (String) detail.get("href");
            JSONObject postion = (JSONObject) job.get("position");
            String positionTitle = (String) postion.get("title");
            JSONObject industry = (JSONObject) postion.get("industry");
            String industryName = (String) industry.get("name");
            JSONObject location = (JSONObject) postion.get("location");
            String locationName = ((String) location.get("name")).replace("&gt; ", "");
            JSONObject jobType = (JSONObject) postion.get("job-type");
            String jobTypeName = (String) jobType.get("name");
            JSONObject experienceLevel = (JSONObject) postion.get("experience-level");
            String experienceLevelName = (String) experienceLevel.get("name");
            JSONObject requiredEducationLevel = (JSONObject) postion.get("required-education-level");
            String requiredEducationLevelName = (String) requiredEducationLevel.get("name");
            JSONObject salary = (JSONObject) job.get("salary");
            String salaryName = (String) salary.get("name");
            String id = (String) job.get("id");
            Integer applyCnt = Integer.parseInt((String) job.get("apply-cnt"));
            Long expirationTimestamp = Long.parseLong((String) job.get("expiration-timestamp"));
            timestamp = new Date(expirationTimestamp * 1000);
            Boolean isWished = false;

            if (wishRepository.existsByUserAndRecruition_RecruitmentId(user,convertRecruitionType(id))) {
                isWished = true;
            }
            jobDataDtos.add(JobDataDto.builder()
                    .recruitmentId(id)
                    .url(url)
                    .companyName(companyName)
                    .companyHref(companyHref)
                    .positionTitle(positionTitle)
                    .industryName(industryName)
                    .locationName(locationName)
                    .jobType(jobTypeName)
                    .salaryName(salaryName)
                    .requiredEducationLevel(requiredEducationLevelName)
                    .experienceLevel(experienceLevelName)
                    .expirationDate(timestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString())
                    .applyCnt(applyCnt)
                    .wished(isWished)
                    .build());
        }
        return jobDataDtos;
    }

}
