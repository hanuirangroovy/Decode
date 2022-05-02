package com.ssafy.escapesvr.service;


import com.ssafy.escapesvr.client.UserServiceClient;
import com.ssafy.escapesvr.dto.*;
import com.ssafy.escapesvr.entity.ArticleComment;
import com.ssafy.escapesvr.entity.Qna;
import com.ssafy.escapesvr.entity.QnaComment;
import com.ssafy.escapesvr.repository.QnaCommentRepository;
import com.ssafy.escapesvr.repository.QnaRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QnaCommentServiceImpl implements QnaCommentService {

    private final QnaRepository qnaRepository;
    private final QnaCommentRepository qnaCommentRepository;
    private final UserServiceClient userServiceClient;

    //댓글 전체 조회
    @Override
    public Page<QnaCommentResponseDto> getQnaComment(Pageable pageable) {
        Page<QnaComment> list = qnaCommentRepository.findAll(pageable);

        Page<QnaCommentResponseDto> qnaCommentList=list.map( o-> new QnaCommentResponseDto(o.getId(), o.getContent(), o.getUserId(), o.getCreatedAt(), o.getModifiedAt(), o.getQna().getId(), o.getNickName(), o.getUserImage()));
        return qnaCommentList;
    }


    //게시글 별 댓글 조회
    @Override
    public List<QnaCommentResponseDto> getQnaCommentList(Long qnaId) {
        Qna qna = qnaRepository.getById(qnaId);
        List<QnaComment> comments = qnaCommentRepository.findByQna(qna);
        return comments.stream().map(QnaCommentResponseDto::new).collect(Collectors.toList());
    }

    //회원 별 댓글 조회
    @Override
    public Page<QnaCommentResponseDto> getMyQnaCommentList(Integer userId, Pageable pageable) {

        Page<QnaComment> myQnaComments = qnaCommentRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
        Page<QnaCommentResponseDto> myQnaComment = myQnaComments.map(o-> new QnaCommentResponseDto(o.getId(), o.getContent(), o.getUserId(), o.getCreatedAt(), o.getModifiedAt(), o.getQna().getId(), o.getNickName(), o.getUserImage()));
        return myQnaComment;

    }

    //댓글 작성
    @Transactional
    @Override
    public void insertQnaComment(QnaCommentRequestDto qnaCommentRequestDto) {
        Qna qna = qnaRepository.getById(qnaCommentRequestDto.getQnaId());

        QnaComment qnaComment = new QnaComment();
        qnaComment.setContent(qnaCommentRequestDto.getContent());
        qnaComment.setCreatedAt(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime());
        qnaComment.setQna(qna);
        qnaComment.setUserId(qnaCommentRequestDto.getUserId());

        try{
            ProfileRequestDto profileRequestDto = userServiceClient.userFindProfile(qnaCommentRequestDto.getUserId());
            qnaComment.setNickName(profileRequestDto.getNickName());
            qnaComment.setUserImage(profileRequestDto.getImage());
        }catch (FeignException e){
            e.printStackTrace();
        }



        qnaCommentRepository.save(qnaComment);
    }

    //댓글 수정
    @Transactional
    @Override
    public void updateQnaComment(QnaCommentUpdateRequestDto qnaCommentUpdateRequestDto, Long id) {

        QnaComment qnaComment = qnaCommentRepository.getById(qnaCommentUpdateRequestDto.getId());
        qnaComment.setContent(qnaCommentUpdateRequestDto.getContent());
        qnaComment.setModifiedAt(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime());
        qnaCommentRepository.save(qnaComment);
    }

    //댓글 삭제
    @Transactional
    @Override
    public void deleteQnaComment(Long qnaCommentId) {
        qnaCommentRepository.deleteById(qnaCommentId);
    }



}
