package com.ssafy.escapesvr.service;

import com.ssafy.escapesvr.dto.QnaCommentRequestDto;
import com.ssafy.escapesvr.dto.QnaCommentResponseDto;
import com.ssafy.escapesvr.dto.QnaCommentUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QnaCommentService {

    /* 조회 */
    List<QnaCommentResponseDto> getQnaCommentList(Long QnaId); //해당 게시물 조회
    Page<QnaCommentResponseDto> getMyQnaCommentList(Integer userId, Pageable pageable); //회원별 모든 게시글 조회
    Page<QnaCommentResponseDto> getQnaComment(Pageable pageable); //모든 게시글 조회

    /* 저장, 수정, 삭제 */
    void insertQnaComment(QnaCommentRequestDto qnaCommentRequestDto);
    void updateQnaComment(QnaCommentUpdateRequestDto qnaCommentUpdateRequestDto, Long id);
    void deleteQnaComment(Long qnaCommentId);


}
