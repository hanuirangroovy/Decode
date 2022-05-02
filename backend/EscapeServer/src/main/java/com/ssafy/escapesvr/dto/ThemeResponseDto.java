package com.ssafy.escapesvr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThemeResponseDto {

   @ApiModelProperty(value = "테마 아이디", example = "1", required = true)
    private Integer themeId;
   @ApiModelProperty(value = "대분류지역", example = "서울", required = true)
   private String largeRegion;
   @ApiModelProperty(value = "소분류지역", example = "홍대", required = true)
   private String smallRegion;
   @ApiModelProperty(value = "테마 이름", example = "네드", required = true)
    private String theme_name;
    @ApiModelProperty(value = "장르", example = "네드", required = true)
   private String genre;
   @ApiModelProperty(value = "테마 최대 인원수", example = "5", required = true)
    private Integer maxNumber;
   @ApiModelProperty(value = "테마 난이도", example = "3", required = true)
    private Integer level;
   @ApiModelProperty(value = "테마시간", example = "60", required = true)
    private Integer time;

//   @QueryProjection
//   public ThemeResponseDto(Integer id,String largeRegion,String smallRegion,String name,Integer maxNumber,Double level,Integer time){
//       this.theme_id=id;
//       this.largeRegion=largeRegion;
//       this.smallRegion=smallRegion;
//       this.theme_name=name;
//       this.maxNumber=maxNumber;
//       this.level=level;
//       this.time=time;
//   }

}
