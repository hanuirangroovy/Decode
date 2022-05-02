package com.ssafy.escapesvr.repository.querydsl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.escapesvr.dto.ThemeResponseDto;
import com.ssafy.escapesvr.entity.QStore;
import com.ssafy.escapesvr.entity.QTheme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

public class InformationRepositoryImpl implements InformationRepository {

    private static final QTheme qTheme=QTheme.theme;
    private static final QStore qStore=QStore.store;

    private final JPAQueryFactory queryFactory;


    public InformationRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<ThemeResponseDto> findByConditions(String largeRegion, String smallRegion, String genre, Integer maxNumber, Integer maxLevel, Integer maxTime, Integer isSingleplay, Pageable pageable) {
        JPQLQuery<ThemeResponseDto> query =
                queryFactory
                        .select(Projections.constructor(ThemeResponseDto.class,qTheme.id,qStore.largeRegion,qStore.smallRegion,qTheme.name,qTheme.genre,qTheme.maxNumber,qTheme.level,qTheme.time))
                        .from(qTheme)
                        .join(qTheme.store,qStore)
                        .where(qTheme.maxNumber.loe(maxNumber).and(qTheme.level.loe(maxLevel).and(qTheme.time.loe(maxTime))),likelargeRegion(largeRegion),likesmallRegion(smallRegion),likegenre(genre),eqisSingle(isSingleplay))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<ThemeResponseDto> items = query.fetch(); // 데이터 조회
        long totalCount = query.fetchCount(); // 전체 count
        return new PageImpl<>(items, pageable, totalCount);
    }

    private BooleanExpression likelargeRegion(String largeRegion){
        if(StringUtils.isNullOrEmpty(largeRegion)){
            return null;
        }
        return qStore.largeRegion.like(largeRegion);
    }

    private BooleanExpression likesmallRegion(String smallRegion){
        if(StringUtils.isNullOrEmpty(smallRegion)){
            return null;
        }
        return qStore.smallRegion.like(smallRegion);
    }

    private BooleanExpression likegenre(String genre){
        if(StringUtils.isNullOrEmpty(genre)){
            return null;
        }
        return qTheme.genre.like(genre);
    }

    private BooleanExpression eqisSingle(Integer isSingleplay){
        if(isSingleplay==null){
            return null;
        }
        return qTheme.isSingleplay.eq(isSingleplay);
    }




}
