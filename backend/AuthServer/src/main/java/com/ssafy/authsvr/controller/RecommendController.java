package com.ssafy.authsvr.controller;

import com.ssafy.authsvr.payload.response.RecommendGenderAgeResponse;
import com.ssafy.authsvr.payload.response.RecommendGenreResponse;
import com.ssafy.authsvr.payload.response.RecommendLikeResponse;
import com.ssafy.authsvr.service.RecommendService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/recommend")
public class RecommendController {

    private final RecommendService recommendService;

    @ApiOperation(value = " 개인별 추천 페이지 조회", notes = "개인별 추천 페이지")
    @GetMapping("/like/{userId}")
    public ResponseEntity<RecommendLikeResponse> recommendLikeFind(@PathVariable Integer userId){
        log.info("recommendLikeFind");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(recommendService.findRecommendLike(userId));
    }

    @ApiOperation(value = " 성별/나이별 추천 페이지 조회", notes = "성별/나이별 추천 페이지")
    @GetMapping("/genderAge/{userId}")
    public ResponseEntity<RecommendGenderAgeResponse> recommendGenreAgeFind(@PathVariable Integer userId){
        log.info("recommendGenreAgeFind");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(recommendService.findRecommendGenderAge(userId));
    }

    @ApiOperation(value = " 장르별 추천 페이지 조회", notes = "장르별 추천 페이지")
    @GetMapping("/genre/{userId}")
    public ResponseEntity<RecommendGenreResponse> recommendGenreFind(@PathVariable Integer userId){
        log.info("recommendGenreFind");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(recommendService.findRecommendGenre(userId));
    }
}
