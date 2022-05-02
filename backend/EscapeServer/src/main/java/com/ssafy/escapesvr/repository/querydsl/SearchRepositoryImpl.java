package com.ssafy.escapesvr.repository.querydsl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.escapesvr.dto.ArticleResponseDto;
import com.ssafy.escapesvr.dto.SearchDto;
import com.ssafy.escapesvr.dto.ThemeResponseDto;
import com.ssafy.escapesvr.entity.Article;
import com.ssafy.escapesvr.entity.QArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ssafy.escapesvr.entity.QArticle.article;

public class SearchRepositoryImpl implements SearchRepository {

    private static final QArticle qArticle= QArticle.article;

    private final JPAQueryFactory queryFactory;


    public SearchRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Article> findPageDynamicQuery(String largeRegion, String smallRegion, Pageable pageable) {

        JPQLQuery<Article> query = queryFactory
                .select(qArticle)
                .from(qArticle)
                .where(largeRegionEq(largeRegion), smallRegionEq(smallRegion))
                .orderBy(qArticle.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<Article> items = query.fetch(); // 데이터 조회
        long totalCount = query.fetchCount(); // 전체 count
        return new PageImpl<>(items, pageable, totalCount);
    }

    private BooleanExpression largeRegionEq(String largeRegion) {
        if(largeRegion!=null){ //대분류지역선택한 것에 따라서 조회
            return qArticle.largeRegion.contains(largeRegion);
        } //만약 전체지역을 선택했다면 largeRegion은 null이 넘어옴
        return null;

    }

    private BooleanExpression smallRegionEq(String smallRegion) {
        if(smallRegion!=null){ //소분류지역선택한 것에 따라서 조회
            return qArticle.smallRegion.contains(smallRegion);
        } //만약 전체지역을 선택했다면 smallRegion은 null이 넘어옴
        return null;
    }

//
//    private BooleanExpression searchDtoEq(SearchDto searchDto) {
//        if(searchDto.getSearchKey()!=null && searchDto.getSearchValue()!=null){
//            if(searchDto.getSearchKey().equals("제목")){
//                return  article.title.contains((searchDto.getSearchValue()));
//            }else if(searchDto.getSearchKey().equals("내용")){
//                return article.content.contains(searchDto.getSearchValue());
//            }else{
//                //작성자
//                return article.nickName.contains(searchDto.getSearchValue());
//            }
//        }
//        return null;
//    }


}
