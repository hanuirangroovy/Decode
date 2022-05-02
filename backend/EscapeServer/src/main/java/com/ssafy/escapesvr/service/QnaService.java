package com.ssafy.escapesvr.service;

import com.ssafy.escapesvr.dto.QnaRequestDto;
import com.ssafy.escapesvr.dto.QnaResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QnaService {

    /* 조회 */
    Page<QnaResponseDto> getMyQnaList(Integer userId, Pageable pageable); //회원별 모든 게시글 조회
    Page<QnaResponseDto> getQnaList(Pageable pageable); //모든 게시글 조회
    QnaResponseDto getQna(Long id); //해당 게시글 조회

    /* 저장, 수정, 삭제 */
    void insertQna(QnaRequestDto qnaRequestDto) throws Exception;
    void updateQna(QnaRequestDto qnaRequestDto, Long id);
    void deleteQna(Long id);

}
