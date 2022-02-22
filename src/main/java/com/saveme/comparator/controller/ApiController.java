package com.saveme.comparator.controller;

import com.saveme.comparator.domain.Data;
import com.saveme.comparator.dto.JobDataDto;


import com.saveme.comparator.dto.WishSetDto;
import com.saveme.comparator.service.UserService;

import com.saveme.comparator.service.JobService;
import com.saveme.comparator.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api", produces = "application/json; charset=utf8")
public class ApiController {


    private final UserService userService;
    private final JobService jobService;


    @GetMapping("/jobs/list")
    public ResponseEntity<Data<List<JobDataDto>>> getJobsList(@RequestParam("keywords") String keywords,
                                                              @RequestParam("loc_cd") String locationCode,
                                                              @RequestParam("start") Integer start,
                                                              @RequestParam("count") Integer count) {


        return new ResponseEntity<>(new Data<>(jobService.getJobDataList(start, keywords, locationCode, count)), HttpStatus.OK);
    }

    @PostMapping("/users/wish")
    public ResponseEntity<?> addWish(Authentication auth, @RequestBody JobDataDto jobDataDto) {

        userService.addWishJob(auth, jobDataDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/users/wishList")
    public ResponseEntity<Data<List<JobDataDto>>> getWishList(Authentication auth) {

        return new ResponseEntity<>(new Data<>(userService.getWishList(auth)), HttpStatus.OK);
    }

    @PostMapping("/users/wish-set/save")
    public ResponseEntity<?> saveWishSet (@RequestBody WishSetDto wishSetDto) {
        userService.createWishSet(wishSetDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}