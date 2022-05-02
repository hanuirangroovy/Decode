package com.ssafy.escapesvr.controller;

import com.ssafy.escapesvr.dto.StoreAndThemeResponseDto;
import com.ssafy.escapesvr.dto.ThemeResponseDto;
import com.ssafy.escapesvr.dto.ThemeReviewResponseDto;
import com.ssafy.escapesvr.service.InformationService;
import com.ssafy.escapesvr.service.ThemeReviewService;
import com.ssafy.escapesvr.service.UrlToMultipartService;
import io.swagger.annotations.Api;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/information")
@Api("정보게시판 컨트롤러")
public class InformationController {

    @Autowired
    InformationService informationService;
    @Autowired
    ThemeReviewService themeReviewService;
    @Autowired
    UrlToMultipartService urlToMultipartService;

    @ApiOperation(value = "대분류에 해당하는 소분류 리스트", notes = "대분류 별 소분류 리스트", response = Map.class)
    @GetMapping("/region")
    public ResponseEntity<Map<String, Object>> getDetail(@RequestParam(required = false) @ApiParam(value = "대분류 지역") String largeRegion){
        Map<String, Object> result = new HashMap<>();
        List<String>smallRegions=null;
        HttpStatus httpStatus = null;
        try {
            smallRegions = informationService.getSmallRegion(largeRegion);
            httpStatus = HttpStatus.OK;
        } catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        result.put("smallRegions", smallRegions);
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @ApiOperation(value = "정보게시판 필터링된 리스트 조회", notes = "게시글 리스트를 쿼리스트링으로 받은 옵션에 따라 불러온다", response = Map.class)
    @GetMapping
    public ResponseEntity<Map<String, Object>> getInformationList(@RequestParam(required = false) @ApiParam( value="대분류 지역") String largeRegion,
                                                              @RequestParam(required = false) @ApiParam( value="소분류 지역")  String smallRegion,
                                                              @RequestParam(required = false) @ApiParam( value="장르")String genre,
                                                              @RequestParam @ApiParam( value="최대 인원수") Integer maxNumber, @RequestParam @ApiParam( value="최대 난이도") Integer maxLevel, @RequestParam @ApiParam( value="최대 시간") Integer maxTime,
                                                                  @RequestParam(required = false) @ApiParam( value="혼자 가능 여부 (가능하면 1)") Integer isSingleplay,
                                                                @PageableDefault(size = 10)  Pageable pageable
                                                                  ) {

        Map<String, Object> result = new HashMap<>();

        Page<ThemeResponseDto> informationList = null;
        HttpStatus httpStatus = null;

        try {
            informationList = informationService.getInformationList(largeRegion, smallRegion, genre, maxNumber,maxLevel,maxTime,isSingleplay,pageable);
            httpStatus = HttpStatus.OK;
        } catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
         result.put("informationList", informationList);
         return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @ApiOperation(value = "테마 상세 조회", notes = "테마별 상세 조회 정보를 나타낸다.", response = Map.class)
    @GetMapping("/detail/{themeId}")
    public ResponseEntity<Map<String, Object>> getDetail(@PathVariable @ApiParam( value="테마 아이디",required = true) Integer themeId) {
        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        StoreAndThemeResponseDto storeandtheme = null;
        try {
            //리뷰를 제외한 테마+스토어에 대한 정보
            storeandtheme = informationService.getDetail(themeId);
            httpStatus = HttpStatus.OK;
        } catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        result.put("storeandtheme",storeandtheme);
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

}
