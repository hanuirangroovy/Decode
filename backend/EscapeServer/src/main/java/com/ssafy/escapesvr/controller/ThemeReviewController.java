package com.ssafy.escapesvr.controller;

import com.ssafy.escapesvr.dto.*;
import com.ssafy.escapesvr.service.ThemeReviewService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/review")
public class ThemeReviewController {

    @Autowired
    ThemeReviewService themeReviewService;

    //테마에 해당하는 리뷰
    @ApiOperation(value = "테마에 해당하는 리뷰 조회", notes = "테마 아아디에 해당하는 리뷰들을 불러온다",response=Map.class)
    @GetMapping("/{themeId}")
    public ResponseEntity<Map<String, Object>> getThemeReviewList(@PathVariable @ApiParam( value="테마 아이디",required = true) Integer themeId, @PageableDefault(sort="createdAt",direction = Sort.Direction.DESC,size=5) Pageable pageable) {
        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        Page<ThemeReviewResponseDto> reviews=null;
        try {
            //리뷰리스트에 대한 정보
            reviews=themeReviewService.getThemeReviewList(themeId,pageable);
            // 그 테마 아이디에 해당하는 리뷰들을 리스트형태로 가지고와서 -> 리뷰에서 만들어주기
            httpStatus = HttpStatus.OK;
        } catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        }
        result.put("review",reviews);

        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    // auth서버 전달- 회원 아이디에 해당하는 리뷰
    @ApiOperation(value = "내가 작성한 리뷰 조회 - myprofile 리뷰 조회", notes = "회원 아아디에 해당하는 리뷰들을 불러온다",response=Map.class)
    @GetMapping("/myreview/{userId}")
    public ResponseEntity<Map<String, Object>> getMyReviewList(@PathVariable @ApiParam( value="회원 아이디",required = true) Integer userId, @PageableDefault(sort="createdAt",direction = Sort.Direction.DESC,size=5) Pageable pageable){
        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        Page<MyReviewResponseDto> reviews=null;
        try{
            reviews=themeReviewService.getMyReviewList(userId,pageable);
            httpStatus = HttpStatus.OK;
        } catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        result.put("review",reviews);
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    // auth서버 전달 - 포스터들
    @ApiOperation(value = "내가 깬 테마의 포스터 - myprofile 포스터들", notes = "회원 아아디가 깬 장르 개수를 불러온다.",response=Map.class)
    @GetMapping("/poster/{userId}")
    public ResponseEntity<Map<String, Object>> getPoster(@PathVariable @ApiParam( value="회원 아이디",required = true) Integer userId,@PageableDefault(sort="createdAt",direction = Sort.Direction.DESC,size=8) Pageable pageable){
        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        Page<PosterResponseDto>posters=null;
        try{
            posters=themeReviewService.getPosters(userId,pageable);
            httpStatus = HttpStatus.OK;
        }catch (RuntimeException e){
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        result.put("posters",posters);
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }


    // auth서버 전달- 회원 아이디가 깬 장르 개수
    @ApiOperation(value = "내가 깬 장르 - myprofile ", notes = "회원 아아디가 깬 장르 개수를 불러온다.",response=Map.class)
    @GetMapping("/mygenre/{userId}")
    public ResponseEntity<Map<String, Object>> getMyGenre(@PathVariable @ApiParam( value="회원 아이디",required = true) Integer userId){
        int[]genre=new int[10];
        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        try{
            genre=themeReviewService.getMyGenre(userId);
            httpStatus = HttpStatus.OK;
        }catch (RuntimeException e){
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        result.put("genre",genre);
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }



    // 리뷰 작성
    @ApiOperation(value = "리뷰 작성", notes = "테마에 리뷰를 작성한다.")
    @PostMapping
    public ResponseEntity<String> insertReview(@RequestBody @ApiParam(value = "댓글 작성 모델")ReviewRequestDto reviewRequestDto){
        HttpStatus status = null;
        try {
            themeReviewService.insertReview(reviewRequestDto);
            status = HttpStatus.OK;
        } catch (RuntimeException e) {
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(status);

    }

    // 리뷰 삭제
    @ApiOperation(value = "리뷰 삭제", notes = "테마에 리뷰를 삭제한다.")
    @DeleteMapping("/{themeReviewId}")
    ResponseEntity<String> deleteReview(@PathVariable("themeReviewId") @ApiParam(value = "리뷰 번호", required = true) Integer themeReviewId ){
        HttpStatus status = null;
        try {
            themeReviewService.deleteReview(themeReviewId);
            status = HttpStatus.OK;
        } catch (RuntimeException e) {
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(status);
    }

    }


