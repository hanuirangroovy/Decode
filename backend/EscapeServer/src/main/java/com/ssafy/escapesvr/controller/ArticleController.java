package com.ssafy.escapesvr.controller;


import com.ssafy.escapesvr.dto.ArticleRequestDto;
import com.ssafy.escapesvr.dto.ArticleResponseDto;
import com.ssafy.escapesvr.dto.SearchDto;
import com.ssafy.escapesvr.service.ArticleServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/article")
@Api("유저게시판 컨트롤러")
public class ArticleController {

    private final ArticleServiceImpl articleService;
    
    //게시글 생성
    @ApiOperation(value = "유저게시글 생성", notes = "게시글을 작성한다", response = Map.class)
    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@RequestBody @ApiParam(value = "게시글에 대한 정보", required = true) @Valid ArticleRequestDto articleRequestDto) {
        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        try {
            ArticleResponseDto articleResponseDto = articleService.save(articleRequestDto);
            httpStatus = HttpStatus.CREATED;
            result.put("article", articleResponseDto);
            result.put("success", true);
        } catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("success", false);
        }
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    //게시글 전체조회
    @ApiOperation(value = "유저게시글 전체 리스트 조회", notes = "게시글 리스트를 불러온다", response = Map.class)
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllArticleList(@PageableDefault(size=5) @SortDefault.SortDefaults({@SortDefault(sort="createdAt", direction = Sort.Direction.DESC)})Pageable pageable) {

        Map<String, Object> result = new HashMap<>();
        Page<ArticleResponseDto> articleList = null;
        HttpStatus httpStatus = null;

        try {
            articleList = articleService.getAllArticle(pageable);
            httpStatus = HttpStatus.OK;
            result.put("success", true);

        } catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("success", false);

        }

        result.put("articleList", articleList);

        return new ResponseEntity<Map<String, Object>>(result, httpStatus);

    }

    //게시글 검색
    @ApiOperation(value = "유저게시판 게시글 검색", notes = "유저게시판 게시글 검색한 리스트를 불러온다", response = Map.class)
    @GetMapping("/board")
    public ResponseEntity<Map<String, Object>> postList(@PageableDefault(size=5) @SortDefault.SortDefaults({@SortDefault(sort="createdAt", direction = Sort.Direction.DESC)})Pageable pageable , @RequestParam(required = false) @ApiParam(value = "대분류 지역") String largeRegion , @RequestParam(required = false) @ApiParam(value = "지역 정보(지역소분류 ex)강남)") String smallRegion)  {

        Map<String, Object> result = new HashMap<>();
        Page<ArticleResponseDto> articleList = null;
        HttpStatus httpStatus = null;

        try {
            articleList = articleService.postList(largeRegion , smallRegion ,pageable);
            httpStatus = HttpStatus.OK;
            result.put("success", true);

        } catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("success", false);

        }

        result.put("articleList", articleList);

        return new ResponseEntity<Map<String, Object>>(result, httpStatus);

    }


    //게시글 번호(id)에 해당하는 게시글 조회
    @ApiOperation(value = "유저게시글 해당 게시물 조회", notes = "게시글 번호(id)에 해당하는 게시글을 불러온다", response = Map.class)
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getArticle(@PathVariable @ApiParam(value = "게시글 번호", required = true) Long id) {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        ArticleResponseDto article = null;

        try {
            article = articleService.getArticle(id);
            httpStatus = HttpStatus.OK;
            result.put("success", true);

        } catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("success", false);

        }
        result.put("article", article);
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);

    }

    //유저 별 게시글 조회
    @ApiOperation(value = "자신(회원)이 쓴 유저게시글 리스트", notes = "(프로필페이지에서 확인) 회원이 자신이 작성한 유저게시판의 모든 게시글 리스트를 불러온다")
    @GetMapping("/profile/{userId}")
    public ResponseEntity<Map<String, Object>> getMyArticleList(@PathVariable @ApiParam(value = "회원번호") Integer userId, @PageableDefault(sort="createdAt",direction = Sort.Direction.DESC,size=3) Pageable pageable) {

        Map<String, Object> result = new HashMap<>();
        Page<ArticleResponseDto> myArticleList = null;
        HttpStatus status = null;
        try {
            myArticleList = articleService.getMyArticleList(userId, pageable);
            status = HttpStatus.OK;
        } catch (RuntimeException e) {
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        result.put("myArticleList", myArticleList);

        return new ResponseEntity<>(result, status);
    }


    //게시글수정
    @PutMapping ("/{id}")
    @ApiOperation(value = "유저게시글 수정", notes = "게시글 번호(id)에 해당하는 게시글을 수정한다", response = Map.class)
    public ResponseEntity<Map<String, Object>> updateArticle(@PathVariable @ApiParam(value = "게시글 번호", required = true) final Long id, @RequestBody @ApiParam(value = "게시글 정보", required = true) @Valid  final ArticleRequestDto articleRequestDto) {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        try {
            ArticleResponseDto articleResponseDto = articleService.updateArticle(articleRequestDto, id);

            httpStatus = HttpStatus.OK;
            result.put("article", articleResponseDto);
            result.put("success", true);
        } catch (RuntimeException  e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("success", false);
        }
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);

    }


    //게시글 삭제
    @DeleteMapping("/{id}")
    @ApiOperation(value = "유저게시글 삭제", notes = "게시글 번호(id)에 해당하는 게시글을 삭제한다", response = Map.class)
    public ResponseEntity<Map<String, Object>> deleteArticle(@PathVariable @ApiParam(value = "게시글 번호", required = true) final Long id) {
        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        try {
            articleService.deleteArticle(id);

            httpStatus = HttpStatus.OK;
            result.put("success", true);
        } catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("success", false);
        }
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }


    @ApiOperation(value = "게시글 추천 수 증가", notes = "게시글 번호(id)에 해당하는 게시물 추천 수를 증가시킨다", response = Map.class)
    @PostMapping("/recommend/{id}")
    public ResponseEntity<Map<String, Object>> recommendArticle(@PathVariable @ApiParam(value = "게시글 번호", required = true) final Long id){
        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        Integer recommend = null;
        try {
            recommend = articleService.recommendArticle(id);
            httpStatus = HttpStatus.OK;
            result.put("success", true);
        }catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("success", false);
        }
        result.put("recommend", recommend);
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);

    }

    @ApiOperation(value = "게시글 추천 취소", notes = "게시글 번호(id)에 해당하는 게시물 추천을 취소한다.(추천 수 감소)", response = Map.class)
    @DeleteMapping("/recommend/{id}")
    public ResponseEntity<Map<String, Object>> cancelRecommendArticle(@PathVariable @ApiParam(value = "게시글 번호", required = true) final Long id){
        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        Integer recommend = null;
        try {
            articleService.cancelRecommendArticle(id);
            httpStatus = HttpStatus.OK;
            result.put("success", true);
        }catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("success", false);
        }
        result.put("recommend", recommend);
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);

    }

    @ApiOperation(value = "게시글 신고 수 증가", notes = "게시글 번호(id)에 해당하는 게시물 신고 수를 증가시킨다", response = Map.class)
    @PostMapping("/report/{id}")
    public ResponseEntity<Map<String, Object>> reportArticle(@PathVariable @ApiParam(value = "게시글 번호", required = true) final Long id){
        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        Integer report = null;
        try {
            articleService.reportArticle(id);
            httpStatus = HttpStatus.OK;
            result.put("success", true);
        }catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("success", false);
        }
        result.put("report", report);
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);

    }

    @ApiOperation(value = "게시글 신고 취소", notes = "게시글 번호(id)에 해당하는 게시물 신고를 취소한다.(신고 수 감소)", response = Map.class)
    @DeleteMapping("/report/{id}")
    public ResponseEntity<Map<String, Object>> cancleReportArticle(@PathVariable @ApiParam(value = "게시글 번호", required = true) final Long id){
        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        Integer report = null;
        try {
            articleService.cancleReportArticle(id);
            httpStatus = HttpStatus.OK;
            result.put("success", true);
        }catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("success", false);
        }
        result.put("report", report);
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);

    }




}
