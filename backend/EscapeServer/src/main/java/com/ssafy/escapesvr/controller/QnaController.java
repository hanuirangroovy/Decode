package com.ssafy.escapesvr.controller;



import com.ssafy.escapesvr.dto.NoticeResponseDto;
import com.ssafy.escapesvr.dto.QnaRequestDto;
import com.ssafy.escapesvr.dto.QnaResponseDto;
import com.ssafy.escapesvr.service.QnaServiceImpl;
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
@RequestMapping("/qna")
@Api("1:1문의 컨트롤러")
public class QnaController {

    private final QnaServiceImpl qnaService;


    //등록,수정,삭제,조회

    //Qna랑 공지사항 작성 : isNotice가 0이면 qna, 1이면 공지사항
    @ApiOperation(value = "1:1 문의 작성", notes = "회원이 1:1 문의글(isNotice =0)을 작성한다")
    @PostMapping
    public ResponseEntity<String> insertQna(@RequestBody @ApiParam(value = "문의글 작성 모델") @Valid QnaRequestDto qnaRequestDto) {

        HttpStatus status = null;
        try {
            qnaService.insertQna(qnaRequestDto);
            status = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(status);

    }

    @ApiOperation(value = "모든 1:1 문의 조회", notes = "1:1 문의 리스트를 조회한다")
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllQnaList(@PageableDefault(size=5) @SortDefault.SortDefaults({@SortDefault(sort="isNotice", direction = Sort.Direction.DESC), @SortDefault(sort="createdAt", direction = Sort.Direction.DESC)}) Pageable pageable) {

        //(@PathVariable @PageableDefault(sort="createdAt",direction = Sort.Direction.DESC,size=5) Pageable pageable)
        //Sort.by(Sort.Direction.DESC, "isNotice","createdAt"),

        Map<String, Object> result = new HashMap<>();
        Page<QnaResponseDto> qnaList = null;
        HttpStatus status = null;
        try {
            qnaList = qnaService.getQnaList(pageable);
            status = HttpStatus.OK;
        } catch (RuntimeException e) {
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        result.put("qnaList", qnaList);

        return new ResponseEntity<>(result, status);
    }

    @ApiOperation(value = "자신(회원)이 쓴 qna 문의 리스트", notes = "회원이 프로필에서 확인하는 자신의 qna 문의 리스트")
    @GetMapping("/profile/{userId}")
    public ResponseEntity<Map<String, Object>> getMyQnaList(@PathVariable @ApiParam(value = "회원번호") Integer userId, @PageableDefault(sort="createdAt",direction = Sort.Direction.DESC,size=3) Pageable pageable) {
        Map<String, Object> result = new HashMap<>();
        Page<QnaResponseDto> myQnaList = null;
        HttpStatus status = null;
        try {
            myQnaList = qnaService.getMyQnaList(userId, pageable);
            status = HttpStatus.OK;
        } catch (RuntimeException e) {
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        result.put("myQnaList", myQnaList);

        return new ResponseEntity<>(result, status);
    }

    //게시글 번호(id)에 해당하는 게시글 조회
    @ApiOperation(value = "1:1 문의 게시판 해당 게시물 조회", notes = "게시글 번호(id)에 해당하는 게시글을 불러온다", response = Map.class)
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getQna(@PathVariable @ApiParam(value = "게시글 번호", required = true) Long id) {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        QnaResponseDto qna = null;

        try {
            qna = qnaService.getQna(id);
            httpStatus = HttpStatus.OK;
            result.put("success", true);

        } catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("success", false);

        }
        result.put("qna", qna);
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);

    }



    @ApiOperation(value = "1:1 문의 수정", notes = "1:1 문의글(isNotice =0)을 수정한다")
    @PutMapping ("/{id}")
    public ResponseEntity<String> updateQna(@PathVariable @ApiParam(value = "게시글 번호", required = true) final Long id, @RequestBody @ApiParam(value = "문의글 수정 모델") @Valid QnaRequestDto qnaRequestDto) {
        HttpStatus status = null;
        try {
            qnaService.updateQna(qnaRequestDto , id);
            status = HttpStatus.OK;
        } catch (RuntimeException e) {
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(status);
    }

    @ApiOperation(value = "1:1 문의 삭제", notes = "문의글을 삭제한다")
    @DeleteMapping ("/{id}")
    public ResponseEntity<String> deleteQna(@PathVariable @ApiParam(value = "문의 게시글 번호") Long id) {
        HttpStatus status = null;
        try {
            qnaService.deleteQna(id);
            status = HttpStatus.OK;
        } catch (RuntimeException e) {
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(status);
    }





}
