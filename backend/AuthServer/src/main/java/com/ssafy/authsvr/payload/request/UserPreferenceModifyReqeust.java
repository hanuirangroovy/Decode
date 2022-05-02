package com.ssafy.authsvr.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPreferenceModifyReqeust {
    @NotNull
    private Integer id;
    @NotNull
    private Integer thrill;
    @NotNull
    private Integer romance;
    @NotNull
    private Integer reasoning;
    @NotNull
    private Integer sfFantasy;
    @NotNull
    private Integer adventure;
    @NotNull
    private Integer comedy;
    @NotNull
    private Integer crime;
    @NotNull
    private Integer horror;
    @NotNull
    private Integer adult;
    @NotNull
    private Integer drama;
}
