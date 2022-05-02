package com.ssafy.escapesvr.repository.querydsl;

import com.ssafy.escapesvr.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchRepository {

    Page<Article> findPageDynamicQuery(String largeRegion , String smallRegion, Pageable pageable);

}
