package com.ssafy.escapesvr.service;

import com.ssafy.escapesvr.client.UserServiceClient;
import com.ssafy.escapesvr.dto.*;
import com.ssafy.escapesvr.entity.Article;
import com.ssafy.escapesvr.entity.Notice;
import com.ssafy.escapesvr.entity.Qna;
import com.ssafy.escapesvr.repository.NoticeRepository;
import com.ssafy.escapesvr.repository.QnaRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    private final UserServiceClient userServiceClient;

    //게시글 작성
    @Transactional
    @Override
    public void insertNotice(NoticeRequestDto noticeRequestDto) throws Exception {

        Notice notice = new Notice();
        notice.setId(noticeRequestDto.getId());
        notice.setTitle(noticeRequestDto.getTitle());
        notice.setContent(noticeRequestDto.getContent());
        notice.setIsNotice(noticeRequestDto.getIsNotice());
        notice.setIsSecret(noticeRequestDto.getIsSecret());
        notice.setCreatedAt(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime());
        notice.setUserId(noticeRequestDto.getUserId());

        try{
            ProfileRequestDto profileRequestDto = userServiceClient.userFindProfile(noticeRequestDto.getUserId());
            notice.setNickName(profileRequestDto.getNickName());
            notice.setUserImage(profileRequestDto.getImage());
        }catch (FeignException e){
            e.printStackTrace();
        }

        noticeRepository.save(notice);

    }

    //게시글 수정
    @Transactional
    @Override
    public void updateNotice(NoticeRequestDto noticeRequestDto, Long id) {

        Notice notice = noticeRepository.getById(id);

        notice.setTitle(noticeRequestDto.getTitle());
        notice.setContent(noticeRequestDto.getContent());
        notice.setIsNotice(noticeRequestDto.getIsNotice());
        notice.setIsSecret(noticeRequestDto.getIsSecret());
        notice.setModifiedAt(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime());
        notice.setUserId(noticeRequestDto.getUserId());

        noticeRepository.save(notice);

    }

    //게시글 삭제
    @Transactional
    @Override
    public void deleteNotice(Long id) {
        noticeRepository.deleteById(id);
    }



    //모든 게시글 조회
    @Override
    public Page<NoticeResponseDto> getNoticeList(Pageable pageable) {

        //공지글이 앞으로 오도록 내림차순 정렬. + 시간순 정렬.
        //Page<Qna> Qnas = QnaRepository.findAll(Sort.by(Sort.Direction.DESC, "isNotice","createdAt"),pageable);
        Page<Notice> notices = noticeRepository.findAll(pageable);
        Page<NoticeResponseDto> notice = notices.map(o -> new NoticeResponseDto(o.getId(),o.getTitle(), o.getContent(),  o.getIsSecret(), o.getIsNotice(), o.getCreatedAt(), o.getModifiedAt(),o.getUserId(), o.getNickName(), o.getUserImage()));

        return notice;

    }

    //해당 게시물 조회
    @Override
    public NoticeResponseDto getNotice(Long id) {

        Notice notice = noticeRepository.getById(id);
        NoticeResponseDto noticeResponseDto = new NoticeResponseDto(notice);

        return noticeResponseDto;
    }

    //회원 별 게시글 조회
    @Override
    public Page<NoticeResponseDto> getMyNoticeList(Integer userId, Pageable pageable) {


        Page<Notice> myNotices = noticeRepository.findByUserId(userId,pageable);

        Page<NoticeResponseDto> myNotice = myNotices.map(o -> new NoticeResponseDto(o.getId(),o.getTitle(), o.getContent(),  o.getIsSecret(), o.getIsNotice(), o.getCreatedAt(), o.getModifiedAt(),o.getUserId(), o.getNickName(), o.getUserImage()));

        return  myNotice;
    }




}
