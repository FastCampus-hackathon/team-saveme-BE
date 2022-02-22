package com.saveme.comparator.controller;

import com.saveme.comparator.domain.Data;
import com.saveme.comparator.domain.User;
import com.saveme.comparator.dto.JobDataDto;


import com.saveme.comparator.dto.WishSetDto;
import com.saveme.comparator.service.UserService;

import com.saveme.comparator.service.JobService;
import com.saveme.comparator.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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


@Api(tags = {"Main Controller 입니다."})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api", produces = "application/json; charset=utf8")
public class ApiController {


    private final UserService userService;
    private final JobService jobService;


    @ApiOperation(value="검색 데이터 가져오기",notes="검색 조건을 통해 데이터를 검색합니다.")
    @GetMapping("/jobs/list")
    public ResponseEntity<Data<List<JobDataDto>>> getJobsList(
            @ApiParam(value="검색어", required = true)
            @RequestParam("keywords") String keywords,
            @ApiParam(value="지역 코드", required = true)
            @RequestParam("loc_cd") String locationCode,
            @ApiParam(value="페이지 index 0부터 시작", required = true)
            @RequestParam("start") Integer start,
            @ApiParam(value="페이지 당 가져올 데이터 수", required = true)
            @RequestParam("count") Integer count,
            @ApiParam(value="토큰을 통한 인가", required = true)
            Authentication auth) {


        return new ResponseEntity<>(new Data<>(jobService.getJobDataList(
                start, keywords, locationCode, count,userService.getUserByAuth(auth))), HttpStatus.OK);
    }

    @ApiOperation(value="즐겨찾기 추가,삭제",notes="한 번 클릭은 즐겨찾기 추가, 두 번 클릭은 즐겨찾기 삭제")
    @PostMapping("/users/wish")
    public ResponseEntity<?> toggleWish(Authentication auth, @RequestBody JobDataDto jobDataDto) {

        userService.addWishJob(auth, jobDataDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value="즐겨찾기 리스트 조회",notes="로그인한 유저의 즐겨찾기 리스트 조회")
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