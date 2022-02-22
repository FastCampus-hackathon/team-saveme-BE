package com.saveme.comparator.controller;



import com.saveme.comparator.domain.Data;
import com.saveme.comparator.dto.JobDataDto;

import com.saveme.comparator.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api" , produces = "application/json; charset=utf8")
public class ApiController {

    private final JobService jobService;


    @GetMapping("/jobs/list")
    public ResponseEntity<Data<List<JobDataDto>>> getJobsList(@RequestParam("keywords") String keywords,
                                                    @RequestParam("loc_cd") String locationCode,
                                                    @RequestParam("start") Integer start,
                                                    @RequestParam("count") Integer count) {


       return new ResponseEntity<>(new Data<>( jobService.getJobDataList(start,keywords,locationCode,count)),HttpStatus.OK);
//        return new ResponseEntity<>(new Data<>(searchService.getJobDataList(start,keywords,locationCode,count)),HttpStatus.OK);
    }
}