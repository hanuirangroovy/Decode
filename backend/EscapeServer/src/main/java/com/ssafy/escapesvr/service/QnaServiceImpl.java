package com.ssafy.escapesvr.service;

import com.ssafy.escapesvr.client.UserServiceClient;
import com.ssafy.escapesvr.dto.NoticeResponseDto;
import com.ssafy.escapesvr.dto.ProfileRequestDto;
import com.ssafy.escapesvr.dto.QnaRequestDto;
import com.ssafy.escapesvr.dto.QnaResponseDto;
import com.ssafy.escapesvr.entity.Notice;
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

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class QnaServiceImpl implements QnaService {

    private final QnaRepository qnaRepository;

    private final UserServiceClient userServiceClient;

    private final QnaCommentRepository qnaCommentRepository;

    //게시글 작성
    @Transactional
    @Override
    public void insertQna(QnaRequestDto qnaRequestDto) throws Exception {

        Qna qna = new Qna();
        qna.setId(qnaRequestDto.getId());
        qna.setTitle(qnaRequestDto.getTitle());
        qna.setContent(qnaRequestDto.getContent());
        qna.setIsNotice(qnaRequestDto.getIsNotice());
        qna.setIsSecret(qnaRequestDto.getIsSecret());
        qna.setCreatedAt(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime());
        qna.setUserId(qnaRequestDto.getUserId());

        try{
            ProfileRequestDto profileRequestDto = userServiceClient.userFindProfile(qnaRequestDto.getUserId());
            qna.setNickName(profileRequestDto.getNickName());
            qna.setUserImage(profileRequestDto.getImage());
        }catch (FeignException e){
            e.printStackTrace();
        }

        qnaRepository.save(qna);

    }

    //게시글 수정
    @Transactional
    @Override
    public void updateQna(QnaRequestDto qnaRequestDto, Long id) {

        Qna qna = qnaRepository.getById(id);

        qna.setTitle(qnaRequestDto.getTitle());
        qna.setContent(qnaRequestDto.getContent());
        qna.setIsNotice(qnaRequestDto.getIsNotice());
        qna.setIsSecret(qnaRequestDto.getIsSecret());
        qna.setModifiedAt(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime());
        qna.setUserId(qnaRequestDto.getUserId());

        qnaRepository.save(qna);

    }

    //게시글 삭제
    @Transactional
    @Override
    public void deleteQna(Long id) {

        //qnaRepository.deleteById(id);

        Qna qna = qnaRepository.getById(id);

        List<QnaComment> comments = qnaCommentRepository.findByQna(qna);

        for(QnaComment qnaComment : comments){
            qnaCommentRepository.deleteById(qnaComment.getId());
        }

        qnaRepository.deleteById(id);



    }



    //모든 게시글 조회
    @Override
    public Page<QnaResponseDto> getQnaList(Pageable pageable) {

        //공지글이 앞으로 오도록 내림차순 정렬. + 시간순 정렬.
        //Page<Qna> Qnas = QnaRepository.findAll(Sort.by(Sort.Direction.DESC, "isNotice","createdAt"),pageable);
        Page<Qna> qnas = qnaRepository.findAll(pageable);
        Page<QnaResponseDto> qna = qnas.map(o -> new QnaResponseDto(o.getId(),o.getTitle(), o.getContent(),  o.getIsSecret(), o.getIsNotice(), o.getCreatedAt(), o.getModifiedAt(),o.getUserId(), o.getNickName(), o.getUserImage()));

        return qna;

    }

    //해당 게시물 조회
    @Override
    public QnaResponseDto getQna(Long id) {

        Qna qna = qnaRepository.getById(id);
        QnaResponseDto qnaResponseDto = new QnaResponseDto(qna);

        return qnaResponseDto;

    }

    //회원 별 게시글 조회
    @Override
    public Page<QnaResponseDto> getMyQnaList(Integer userId, Pageable pageable) {


        Page<Qna> myQnas = qnaRepository.findByUserId(userId,pageable);

        Page<QnaResponseDto> myQna = myQnas.map(o -> new QnaResponseDto(o.getId(),o.getTitle(), o.getContent(),  o.getIsSecret(), o.getIsNotice(), o.getCreatedAt(), o.getModifiedAt(),o.getUserId(), o.getNickName(), o.getUserImage()));

        return  myQna;
    }




}
