package com.ssafy.escapesvr.service;


import com.ssafy.escapesvr.dto.NoticeRequestDto;
import com.ssafy.escapesvr.dto.NoticeResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeService {

    /* 조회 */
    Page<NoticeResponseDto> getMyNoticeList(Integer userId, Pageable pageable); //관리자가 쓴 모든 게시글 조회
    Page<NoticeResponseDto> getNoticeList(Pageable pageable); //모든 게시글 조회
    NoticeResponseDto getNotice(Long id); //해당 게시물 조회

    /* 저장, 수정, 삭제 */
    void insertNotice(NoticeRequestDto noticeRequestDto) throws Exception;
    void updateNotice(NoticeRequestDto noticeRequestDto, Long id);
    void deleteNotice(Long id);


}
