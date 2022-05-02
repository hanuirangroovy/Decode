package com.ssafy.authsvr.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileRequest {

    @NotNull
    private Integer id;
    @NotBlank
    private String nickName;
    @NotNull
    private Integer age;
    @NotBlank
    private String largeRegion;
    @NotBlank
    private String smallRegion;

}