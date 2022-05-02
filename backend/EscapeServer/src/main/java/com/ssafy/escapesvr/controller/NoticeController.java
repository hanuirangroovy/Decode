package com.ssafy.escapesvr.controller;



import com.ssafy.escapesvr.dto.ArticleResponseDto;
import com.ssafy.escapesvr.dto.NoticeRequestDto;
import com.ssafy.escapesvr.dto.NoticeResponseDto;
import com.ssafy.escapesvr.service.NoticeServiceImpl;
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
@RequestMapping("/notice")
@Api("공지게시판 컨트롤러")
public class NoticeController {

    private final NoticeServiceImpl noticeService;


    //등록,수정,삭제,조회

    //notice랑 공지사항 작성 : isNotice가 0이면 qna, 1이면 공지사항
    @ApiOperation(value = "공지사항 작성", notes = "회원이 공지사항(isNotice = 1)을 작성한다")
    @PostMapping
    public ResponseEntity<String> insertNotice(@RequestBody @ApiParam(value = "공지글 작성 모델") @Valid NoticeRequestDto noticeRequestDto) {

        HttpStatus status = null;
        try {
            noticeService.insertNotice(noticeRequestDto);
            status = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(status);

    }

    @ApiOperation(value = "모든 공지사항 리스트 조회", notes = "모든 공지사항 문의 리스트를 조회한다")
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllNoticeList(@PageableDefault(size=5) @SortDefault.SortDefaults({@SortDefault(sort="isNotice", direction = Sort.Direction.DESC), @SortDefault(sort="createdAt", direction = Sort.Direction.DESC)}) Pageable pageable) {

        //(@PathVariable @PageableDefault(sort="createdAt",direction = Sort.Direction.DESC,size=5) Pageable pageable)
        //Sort.by(Sort.Direction.DESC, "isNotice","createdAt"),

        Map<String, Object> result = new HashMap<>();
        Page<NoticeResponseDto> noticeList = null;
        HttpStatus status = null;
        try {
            noticeList = noticeService.getNoticeList(pageable);
            status = HttpStatus.OK;
        } catch (RuntimeException e) {
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        result.put("noticeList", noticeList);

        return new ResponseEntity<>(result, status);
    }

    @ApiOperation(value = "자신(관리자)이 쓴 공지사항 리스트", notes = "관리자가 프로필에서 확인하는 자신의 공지사항 리스트")
    @GetMapping("/profile/{userId}")
    public ResponseEntity<Map<String, Object>> getMyNoticeList(@PathVariable @ApiParam(value = "회원번호") Integer userId, @PageableDefault(sort="createdAt",direction = Sort.Direction.DESC,size=5) Pageable pageable) {
        Map<String, Object> result = new HashMap<>();
        Page<NoticeResponseDto> myNoticeList = null;
        HttpStatus status = null;
        try {
            myNoticeList = noticeService.getMyNoticeList(userId, pageable);
            status = HttpStatus.OK;
        } catch (RuntimeException e) {
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        result.put("myNoticeList", myNoticeList);

        return new ResponseEntity<>(result, status);
    }

    //게시글 번호(id)에 해당하는 게시글 조회
    @ApiOperation(value = "공지게시판 해당 게시물 조회", notes = "게시글 번호(id)에 해당하는 게시글을 불러온다", response = Map.class)
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getNotice(@PathVariable @ApiParam(value = "게시글 번호", required = true) Long id) {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        NoticeResponseDto notice = null;

        try {
            notice = noticeService.getNotice(id);
            httpStatus = HttpStatus.OK;
            result.put("success", true);

        } catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("success", false);

        }
        result.put("notice", notice);
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);

    }


    @ApiOperation(value = "공지사항 수정", notes = "공지사항(isNotice = 1)을 수정한다")
    @PutMapping ("/{id}")
    public ResponseEntity<String> updateNotice(@PathVariable @ApiParam(value = "게시글 번호", required = true) final Long id, @RequestBody @ApiParam(value = "공지글 수정 모델") @Valid NoticeRequestDto noticeRequestDto) {
        HttpStatus status = null;
        try {
            noticeService.updateNotice(noticeRequestDto, id);
            status = HttpStatus.OK;
        } catch (RuntimeException e) {
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(status);
    }

    @ApiOperation(value = "공지사항 삭제", notes = "공지글을 삭제한다")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotice(@PathVariable @ApiParam(value = "공지 게시글 번호") Long id) {
        HttpStatus status = null;
        try {
            noticeService.deleteNotice(id);
            status = HttpStatus.OK;
        } catch (RuntimeException e) {
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(status);
    }





}
