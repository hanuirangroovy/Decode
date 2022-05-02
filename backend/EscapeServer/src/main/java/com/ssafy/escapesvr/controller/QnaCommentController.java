package com.ssafy.escapesvr.controller;


import com.ssafy.escapesvr.dto.QnaCommentRequestDto;
import com.ssafy.escapesvr.dto.QnaCommentResponseDto;
import com.ssafy.escapesvr.dto.QnaCommentUpdateRequestDto;
import com.ssafy.escapesvr.service.QnaCommentService;
import com.ssafy.escapesvr.service.QnaService;
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
@RequestMapping("/qnaComment")
@Api("Qna 게시판 댓글 컨트롤러")
public class QnaCommentController {

    private final QnaCommentService qnaCommentService;

    private final QnaService qnaService;


    //댓글 작성
    @ApiOperation(value = "Qna 게시글 댓글 작성", notes = "1:1 문의 게시글에 댓글을 작성한다", response = Map.class)
    @PostMapping
    public ResponseEntity<String> insertQnaComment(@RequestBody @ApiParam(value = "댓글 작성 모델") @Valid QnaCommentRequestDto qnaCommentRequestDto) {
        HttpStatus status = null;
        try {
            qnaCommentService.insertQnaComment(qnaCommentRequestDto);
            status = HttpStatus.OK;
        } catch (RuntimeException e) {
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(status);
    }


    //게시글 댓글 전체조회
    @ApiOperation(value = "유저게시판 댓글 전체 리스트 조회", notes = "전체 게시글 댓글 리스트를 불러온다", response = Map.class)
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllQnaCommentList(@PageableDefault(size=5) @SortDefault.SortDefaults({@SortDefault(sort="createdAt", direction = Sort.Direction.DESC)})Pageable pageable) {

        Map<String, Object> result = new HashMap<>();
        Page<QnaCommentResponseDto> qnaCommentList = null;
        HttpStatus httpStatus = null;

        try {
            qnaCommentList = qnaCommentService.getQnaComment(pageable);
            httpStatus = HttpStatus.OK;
            result.put("success", true);

        } catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("success", false);

        }

        result.put("qnaCommentList",qnaCommentList);

        return new ResponseEntity<Map<String, Object>>(result, httpStatus);

    }

    //게시글 댓글 조회
    @ApiOperation(value = "Qna 게시글 댓글 조회", notes = "해당 게시글(QnaId)의 모든 댓글을 조회한다", response = Map.class)
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getQnaCommentList(@PathVariable @ApiParam(value = "게시글 번호", required = true) Long id) {

        Map<String, Object> result = new HashMap<>();
        List<QnaCommentResponseDto> qnaCommentList = null;
        HttpStatus status = null;
        try {
            qnaCommentList = qnaCommentService.getQnaCommentList(id);
            status = HttpStatus.OK;
        } catch (RuntimeException e) {
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        result.put("commentList", qnaCommentList);

        return new ResponseEntity<>(result, status);
    }

    //자신이 쓴 댓글 조회
    @ApiOperation(value = "자신이 쓴 qna 댓글 리스트", notes = "자신이 쓴 qna 댓글 리스트을 반환한다.")
    @GetMapping("/profile/{userId}")
    public ResponseEntity<Map<String, Object>> getMyQnaCommentList(@PathVariable("userId") @ApiParam(value = "회원번호") Integer userId, @PageableDefault(sort="createdAt",direction = Sort.Direction.DESC,size=5) Pageable pageable) {

        Map<String, Object> result = new HashMap<>();
        Page<QnaCommentResponseDto> myQnaCommentList = null;
        HttpStatus status = null;
        try {
            myQnaCommentList = qnaCommentService.getMyQnaCommentList(userId, pageable);
            status = HttpStatus.OK;
        } catch (RuntimeException e) {
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        result.put("myQnaCommentList",myQnaCommentList);

        return new ResponseEntity<>(result, status);
    }

    //댓글 수정
    @ApiOperation(value = "Qna 게시글 댓글 수정", notes = "댓글(qnaCommentId가 일치하는)을 수정한다", response = Map.class)
    @PutMapping("/{id}")
    public ResponseEntity<String> updateQnaComment(@PathVariable @ApiParam(value = "게시글 번호", required = true) final Long id, @RequestBody @ApiParam(value = "댓글 수정 모델") QnaCommentUpdateRequestDto qnaCommentUpdateRequestDto) {
        HttpStatus status = null;
        try {
            qnaCommentService.updateQnaComment(qnaCommentUpdateRequestDto, id);
            status = HttpStatus.OK;
        } catch (RuntimeException e) {
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(status);
    }

    //댓글 삭제
    @ApiOperation(value = "Qna 게시글 댓글 삭제", notes = "댓글(qnaCommentId가 일치하는)을 삭제한다", response = Map.class)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQnaComment(@PathVariable("id") @ApiParam(value = "댓글 번호", required = true) Long id) {
        HttpStatus status = null;
        try {
            qnaCommentService.deleteQnaComment(id);
            status = HttpStatus.OK;
        } catch (RuntimeException e) {
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(status);
    }


}
