package com.ssafy.escapesvr.controller;


import com.ssafy.escapesvr.dto.ArticleCommentRequestDto;
import com.ssafy.escapesvr.dto.ArticleCommentResponseDto;
import com.ssafy.escapesvr.dto.ArticleCommentUpdateRequestDto;
import com.ssafy.escapesvr.dto.ArticleResponseDto;
import com.ssafy.escapesvr.service.ArticleCommentService;
import com.ssafy.escapesvr.service.ArticleService;
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
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
@Api("유저게시판 댓글 컨트롤러")
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;

    private final ArticleService articleService;


    //유저게시판 댓글 작성
    @ApiOperation(value = "유저게시글 댓글 작성", notes = "게시글에 댓글을 작성한다", response = Map.class)
    @PostMapping
    public  ResponseEntity<Map<String, Object>>  insertArticleComment(@RequestBody @ApiParam(value = "댓글 작성 모델")  @Valid ArticleCommentRequestDto articleCommentRequestDto) {
        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        try {
            ArticleCommentResponseDto articleCommentResponseDto  = articleCommentService.insertArticleComment(articleCommentRequestDto);
            result.put("articleComment", articleCommentResponseDto);
            result.put("success", true);
            httpStatus = HttpStatus.OK;
        } catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("success", false);
        }
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
      //  return new ResponseEntity<>(status);
    }

    //게시글 댓글 전체조회
    @ApiOperation(value = "유저게시판 댓글 전체 리스트 조회", notes = "전체 게시글 댓글 리스트를 불러온다", response = Map.class)
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllArticleCommentList(@PageableDefault(size=5) @SortDefault.SortDefaults({@SortDefault(sort="createdAt", direction = Sort.Direction.DESC)})Pageable pageable) {

        Map<String, Object> result = new HashMap<>();
        Page<ArticleCommentResponseDto> articleCommentList = null;
        HttpStatus httpStatus = null;

        try {
            articleCommentList = articleCommentService.getArticleComment(pageable);
            httpStatus = HttpStatus.OK;
            result.put("success", true);

        } catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("success", false);

        }

        result.put("articleCommentList", articleCommentList);

        return new ResponseEntity<Map<String, Object>>(result, httpStatus);

    }


    //해당 게시글의 모든 댓글 조회
    @ApiOperation(value = "유저 게시글 댓글 조회", notes = "해당 게시글(id)의 모든 댓글을 조회한다", response = Map.class)
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getArticleCommentList(@PathVariable @ApiParam(value = "게시글 번호", required = true) Long id) {

        Map<String, Object> result = new HashMap<>();
        List<ArticleCommentResponseDto> articleCommentList = null;
        HttpStatus status = null;
        try {
            articleCommentList = articleCommentService.getArticleCommentList(id);
            status = HttpStatus.OK;
        } catch (RuntimeException e) {
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        result.put("commentList", articleCommentList);

        return new ResponseEntity<>(result, status);
    }

    //자신이 쓴 유저게시판 댓글 조회
    @ApiOperation(value = "자신이 쓴 유저게시판 댓글 리스트", notes = "자신이 쓴 유저게시판 댓글 리스트을 반환한다.")
    @GetMapping("/profile/{userId}")
    public ResponseEntity<Map<String, Object>> getMyArticleCommentList(@PathVariable("userId") @ApiParam(value = "회원번호") Integer userId, @PageableDefault(sort="createdAt",direction = Sort.Direction.DESC,size=3) Pageable pageable) {

        Map<String, Object> result = new HashMap<>();
        Page<ArticleCommentResponseDto> myArticleCommentList = null;
        HttpStatus status = null;
        try {
            myArticleCommentList = articleCommentService.getMyArticleCommentList(userId, pageable);
            status = HttpStatus.OK;
        } catch (RuntimeException e) {
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        result.put("myArticleCommentList",myArticleCommentList);

        return new ResponseEntity<>(result, status);
    }


    //댓글 수정
    @ApiOperation(value = "유저게시글 댓글 수정", notes = "댓글(commentId가 일치하는)을 수정한다", response = Map.class)
    @PutMapping("/{id}")
    public ResponseEntity<String> updateArticleComment(@PathVariable @ApiParam(value = "게시글 번호", required = true) final Long id, @RequestBody @ApiParam(value = "댓글 수정 모델") @Valid ArticleCommentUpdateRequestDto articleCommentUpdateRequestDto) {
        HttpStatus status = null;
        try {
            articleCommentService.updateArticleComment(articleCommentUpdateRequestDto, id);
            status = HttpStatus.OK;
        } catch (RuntimeException e) {
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(status);
    }

    //댓글 삭제
    @ApiOperation(value = "유저게시글 댓글 삭제", notes = "댓글(commentId가 일치하는)을 삭제한다", response = Map.class)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArticleComment(@PathVariable("id") @ApiParam(value = "댓글 번호", required = true) Long id) {

        HttpStatus status = null;
        try {
            articleCommentService.deleteArticleComment(id);
            status = HttpStatus.OK;
        } catch (RuntimeException e) {
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(status);
    }

}
