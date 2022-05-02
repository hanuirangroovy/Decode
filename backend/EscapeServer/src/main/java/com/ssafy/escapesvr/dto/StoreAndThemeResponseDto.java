package com.ssafy.escapesvr.dto;

import com.ssafy.escapesvr.entity.RecommendNumber;
import com.ssafy.escapesvr.entity.Store;
import com.ssafy.escapesvr.entity.Theme;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreAndThemeResponseDto {

    @ApiModelProperty(value = "테마 아이디", example = "1", required = true)
    private Integer themeId;

    @ApiModelProperty(value = "테마 이름", example = "명당", required = true)
    private String themeName;

    @ApiModelProperty(value = "포스터 url", example = "http://www.secretgardenescape.com/pimages/Product/Pr_1593416874.jpg", required = true)
    private String posterUrl;

    @ApiModelProperty(value = "테마 난이도", example = "3", required = true)
    private Integer level;

    @ApiModelProperty(value = "테마 장르", example = "공포", required = true)
    private String genre;

    @ApiModelProperty(value = "테마 유형", example = "공포", required = true)
    private String type;

    @ApiModelProperty(value = "최대 인원수", example = "5", required = true)
    private Integer maxNumber;

    @ApiModelProperty(value = "테마 시간", example = "60", required = true)
    private Integer time;
    @ApiModelProperty(value = "예약 페이지url", example = "60", required = true)
    private String reserveUrl;

    //추천인원수
    @ApiModelProperty(value = "추천인원수 2명 가능", example = "1", required = true)
    private Integer numberIsTwo;
    @ApiModelProperty(value = "추천인원수 3명 가능", example = "1", required = true)
    private Integer numberIsThree;
    @ApiModelProperty(value = "추천인원수 4명 가능", example = "0", required = true)
    private Integer numberIsFour;
    @ApiModelProperty(value = "추천인원수 5명 가능", example = "0", required = true)
    private Integer numberIsFive;

    //store
    //지역 대분류
    @ApiModelProperty(value = "지역 대분류", example = "서울", required = true)
    private String largeRegion;
    //지역 소분류
    @ApiModelProperty(value = "지역 소분류", example = "홍대", required = true)
    private String smallRegion;

    //가게 이름
    @ApiModelProperty(value = "가게 이름", example = "넥스트에디션 강남1호점", required = true)
    private String storeName;

    //사이트 url
    @ApiModelProperty(value = "가게 사이트 url", example = "https://www.nextedition.co.kr/shops/NextEdition%20Gangnam", required = true)
    private String siteUrl;

    public StoreAndThemeResponseDto(Theme theme, Store store,RecommendNumber recommendNumber){
        themeId=theme.getId();
        themeName=theme.getName();
        posterUrl=theme.getPosterUrl();
        level=theme.getLevel();
        genre=theme.getGenre();
        type=theme.getType();
        maxNumber=theme.getMaxNumber();
        time=theme.getTime();
        reserveUrl=theme.getReserveUrl();
        numberIsTwo=recommendNumber.getNumberIsTwo();
        numberIsThree=recommendNumber.getNumberIsThree();
        numberIsFour=recommendNumber.getNumberIsFour();
        numberIsFive=recommendNumber.getNumberIsFive();
        storeName=store.getName();
        siteUrl=store.getSiteUrl();
        largeRegion=store.getLargeRegion();
        smallRegion=store.getSmallRegion();

    }

}
