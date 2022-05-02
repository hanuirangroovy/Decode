package com.ssafy.escapesvr.dto;

import com.ssafy.escapesvr.entity.Theme;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PosterResponseDto {

    private Integer themeId;

    private String themeName;

    private String posterUrl;


    public PosterResponseDto(Theme theme){
        this.themeId=theme.getId();
        this.themeName=theme.getName();
        this.posterUrl=theme.getPosterUrl();
    }

}
