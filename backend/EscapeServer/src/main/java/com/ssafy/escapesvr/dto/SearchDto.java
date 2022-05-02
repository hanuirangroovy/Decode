package com.ssafy.escapesvr.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDto {

    @ApiModelProperty(value = "searchKey(제목, 작성자, 내용 중 선택)", required = true)
    private String searchKey;

    @ApiModelProperty(value = "검색창에 입력한 내용", required = true)
    private String searchValue;

}
