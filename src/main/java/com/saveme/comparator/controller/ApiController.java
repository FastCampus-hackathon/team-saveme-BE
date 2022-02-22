package com.saveme.comparator.controller;



import com.saveme.comparator.domain.Data;
import com.saveme.comparator.dto.JobDataDto;

import com.saveme.comparator.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api" , produces = "application/json; charset=utf8")
public class ApiController {

    private final SearchService searchService;


    @GetMapping("/jobs/list")
    public ResponseEntity<Data<List<JobDataDto>>> getJobsList(@RequestParam("keywords") String keywords,
                                                    @RequestParam("loc_cd") String locationCode,
                                                    @RequestParam("start") Integer start,
                                                    @RequestParam("count") Integer count) {

        URI uri = UriComponentsBuilder
                .fromUriString("https://oapi.saramin.co.kr")
                .path("/job-search")
                .queryParam("access-key","MStxmvSqpmOLBN9L9hZqpeeqwqHdyZucIwBT8LNf0FcPvQ5FDye6")
                .queryParam("start", start)
                .queryParam("keywords", keywords)
                .queryParam("loc_cd", locationCode)
                .queryParam("count", count)
                .queryParam("fields", "count").encode().build().toUri();

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(uri, HttpMethod.GET,new HttpEntity<>(new HttpHeaders()), JSONObject.class);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) jsonParser.parse(responseEntity.getBody().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONArray jobs = (JSONArray)((JSONObject)jsonObject.get("jobs") ).get("job");

        List<JobDataDto> jobDataDtos = new ArrayList<>();
        for(int i = 0 ; i < jobs.size() ; i++ ){
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
            String locationName = (String) location.get("name");
            JSONObject jobType = (JSONObject) postion.get("job-type");
            String jobTypeName = (String) jobType.get("name");
            JSONObject experienceLevel = (JSONObject) postion.get("experience-level");
            String experienceLevelName = (String) experienceLevel.get("name");
            JSONObject requiredEducationLevel = (JSONObject) postion.get("required-education-level");
            String requiredEducationLevelName = (String) requiredEducationLevel.get("name");
            JSONObject salary = (JSONObject) job.get("salary");
            String salaryName = (String) salary.get("name");
            String id = (String) job.get("id");
            Integer applyCnt = Integer.parseInt( (String) job.get("apply-cnt"));
            Long expirationTimestamp = Long.parseLong((String) job.get("expiration-timestamp"));
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
                    .applyCnt(applyCnt)
                    .build());
        }

        return new ResponseEntity<>(new Data<>(jobDataDtos), HttpStatus.OK);



//        return new ResponseEntity<>(new Data<>(searchService.getJobDataList(start,keywords,locationCode,count)),HttpStatus.OK);
    }
}