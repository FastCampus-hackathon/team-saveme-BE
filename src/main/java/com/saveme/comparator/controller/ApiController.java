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


       return new ResponseEntity<>(new Data<>( searchService.getJobDataList(start,keywords,locationCode,count)),HttpStatus.OK);
//        return new ResponseEntity<>(new Data<>(searchService.getJobDataList(start,keywords,locationCode,count)),HttpStatus.OK);
    }
}