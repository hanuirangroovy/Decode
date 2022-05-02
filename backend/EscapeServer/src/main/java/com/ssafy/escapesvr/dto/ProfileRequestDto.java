package com.ssafy.escapesvr.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequestDto {

    private Integer userId;
    private String image;
    private String nickName;


}
