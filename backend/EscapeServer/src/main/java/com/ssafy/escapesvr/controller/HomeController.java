package com.ssafy.escapesvr.controller;

import com.ssafy.escapesvr.service.HomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
@Api("홈 컨트롤러")
public class HomeController {

    @Autowired
    HomeService homeService;

    @ApiOperation(value = "테마수, 지점수 개수 조회", notes = "테마,지점수 개수를 조회한다.", response = Map.class)
    @GetMapping
    public ResponseEntity<Map<String, Object>> getCount(){
        Map<String, Object> result = new HashMap<>();
        int[]count=new int[2];
        HttpStatus httpStatus = null;
        try {
            count= homeService.getCount();
            httpStatus = HttpStatus.OK;
        } catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        result.put("count", count);
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @ApiOperation(value = "비회원일때 추천 테마", notes = "비회원일때 추천하는 테마를 조회한다.", response = Map.class)
    @GetMapping("/nonmember")
    public ResponseEntity<Map<String,Object>> getNonMember(){
        Map<String, Object> result = new HashMap<>();
        List<Integer>themes=new ArrayList<>();
        HttpStatus httpStatus = null;
        try {
           themes= homeService.getNonMember();
            httpStatus = HttpStatus.OK;
        } catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        result.put("themes", themes);
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }


}
