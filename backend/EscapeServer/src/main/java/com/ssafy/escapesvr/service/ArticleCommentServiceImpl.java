package com.ssafy.escapesvr.service;

import com.ssafy.escapesvr.client.UserServiceClient;
import com.ssafy.escapesvr.dto.*;
import com.ssafy.escapesvr.entity.Article;
import com.ssafy.escapesvr.entity.ArticleComment;
import com.ssafy.escapesvr.repository.ArticleCommentRepository;
import com.ssafy.escapesvr.repository.ArticleRepository;
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
public class ArticleCommentServiceImpl implements ArticleCommentService {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final UserServiceClient userServiceClient;

    //댓글 전체 조회
    @Override
    public Page<ArticleCommentResponseDto> getArticleComment(Pageable pageable) {
        Page<ArticleComment> list = articleCommentRepository.findAll(pageable);

        Page<ArticleCommentResponseDto> articleCommentList=list.map( o-> new ArticleCommentResponseDto(o.getId(), o.getContent(), o.getUserId(), o.getCreatedAt(), o.getModifiedAt(), o.getArticle().getId(), o.getArticle().getTitle(), o.getNickName(), o.getUserImage()));
        return articleCommentList;
    }

    //게시글 당 댓글 조회
    @Override
    public List<ArticleCommentResponseDto> getArticleCommentList(Long articleId) {
        Article article = articleRepository.getById(articleId);
        List<ArticleComment> comments = articleCommentRepository.findByArticle(article);
        return comments.stream().map(ArticleCommentResponseDto::new).collect(Collectors.toList());
    }

    //내가 쓴 댓글 조회
    @Override
    public Page<ArticleCommentResponseDto> getMyArticleCommentList(Integer userId, Pageable pageable) {
        Page<ArticleComment> myArticleComments = articleCommentRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
        Page<ArticleCommentResponseDto> myArticleComment = myArticleComments.map(o-> new ArticleCommentResponseDto(o.getId(), o.getContent(), o.getUserId(), o.getCreatedAt(), o.getModifiedAt(), o.getArticle().getId(), o.getArticle().getTitle(), o.getNickName(), o.getUserImage()));

        return myArticleComment;
    }


    //댓글 작성
    @Transactional
    @Override
    public ArticleCommentResponseDto  insertArticleComment(ArticleCommentRequestDto articleCommentRequestDto) {
        Article article = articleRepository.getById(articleCommentRequestDto.getArticleId());

        ArticleComment articleComment = new ArticleComment();
        articleComment.setContent(articleCommentRequestDto.getContent());
        articleComment.setCreatedAt(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime());
        articleComment.setArticle(article);
        articleComment.setUserId(articleCommentRequestDto.getUserId());

        try{
            ProfileRequestDto profileRequestDto = userServiceClient.userFindProfile(articleCommentRequestDto.getUserId());
            articleComment.setNickName(profileRequestDto.getNickName());
            articleComment.setUserImage(profileRequestDto.getImage());
        }catch (FeignException e){
            e.printStackTrace();
        }


        articleComment = articleCommentRepository.save(articleComment);

        return new ArticleCommentResponseDto(articleComment);
    }

    //댓글 수정
    @Transactional
    @Override
    public void updateArticleComment(ArticleCommentUpdateRequestDto articleCommentUpdateRequestDto, Long id) {
        //넘어오는 파라미터가 수정 댓글모델 , 게시물 번호

//        //1. DB에서 해당 게시물id에 해당하는 게시물을 가져오고
//        Article article = articleRepository.getById(id);
//
//        //2. 해당 게시물에 달린 댓글들을 조회를 해서
//        List<ArticleComment> comments = article.getComments();
//

        //3. 그중에 댓글id(articleCommentDto에 있음)가 일치하는걸 찾아서 수정
        ArticleComment articleComment = articleCommentRepository.getById(articleCommentUpdateRequestDto.getId());
        articleComment.setContent(articleCommentUpdateRequestDto.getContent());
        articleComment.setModifiedAt(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime());
        articleCommentRepository.save(articleComment);

    }



    //댓글 삭제
    @Transactional
    @Override
    public void deleteArticleComment(Long commentId) {
        articleCommentRepository.deleteById(commentId);
    }




}
