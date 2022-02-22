package com.saveme.comparator.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.saveme.comparator.domain.Data;
import com.saveme.comparator.dto.JobDataDto;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(value = "/api" , produces = "application/json; charset=utf8")
public class ApiController {
    @GetMapping("/jobs/list")
    public ResponseEntity<Data<String>> getJobsList(@RequestParam("keywords") String keywords,
                                                    @RequestParam("loc_cd") String locationCode,
                                                    @RequestParam("start") Integer start,
                                                    @RequestParam("count") Integer count) throws JsonProcessingException, ParseException {

        URI uri = UriComponentsBuilder
                .fromUriString("https://oapi.saramin.co.kr")
                .path("/job-search")
                .queryParam("access-key","MStxmvSqpmOLBN9L9hZqpeeqwqHdyZucIwBT8LNf0FcPvQ5FDye6")
                .queryParam("start", start)
                .queryParam("keywords", keywords)
                .queryParam("loc_cd", locationCode)
                .queryParam("count", count)
                .queryParam("fields", "posting-date,expiration-date,count").encode().build().toUri();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> s = restTemplate.exchange(uri, HttpMethod.GET,new HttpEntity<>(new HttpHeaders()), String.class);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(s.getBody());

        JSONObject jobObj = (JSONObject) jsonObject.get("jobs");
        JSONArray jobList = (JSONArray) jobObj.get("job");


        List<JobDataDto> jobDataDtoList = new ArrayList<>();
        for(int i = 0; i < jobList.size(); i++){

            JSONObject getJob = (JSONObject) jobList.get(i);

            JSONObject url = (JSONObject)getJob.get("url");
            JSONObject recruitment = (JSONObject)getJob.get("recruitment");
            JSONObject salary = (JSONObject)getJob.get("salary");
            jobDataDtoList.add(
                    JobDataDto
                    .builder()
                            .url(url.toString())
                            .recruitmentId()
                            .companyHref()
                            .companyName()
                            .positionTitle()
                            .industryName()
                            .locationName()
                            .jobType()
                            .salaryName(salary.get("name").toString())
                            .requiredEducationLevel()
                            .postingDate()
                            .expirationDate()
                            .applyCnt()
                    .build()
            );
        }

        return null;
    }
}