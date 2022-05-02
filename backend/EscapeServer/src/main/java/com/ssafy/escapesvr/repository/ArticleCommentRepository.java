package com.ssafy.escapesvr.repository;

import com.ssafy.escapesvr.entity.Article;
import com.ssafy.escapesvr.entity.ArticleComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {

    List<ArticleComment> findByArticle(Article article);
    Page<ArticleComment> findByUserIdOrderByCreatedAtDesc(Integer userId, Pageable pageable);

}
