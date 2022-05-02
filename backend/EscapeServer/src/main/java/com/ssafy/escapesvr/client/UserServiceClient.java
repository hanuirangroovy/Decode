package com.ssafy.escapesvr.client;

import com.ssafy.escapesvr.dto.ProfileRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name="auth-server",url="http://j6c203.p.ssafy.io:8081")
public interface
UserServiceClient {

    @GetMapping("/user/name/{id}")
    String userFindNickName(@PathVariable(value="id") Integer id);


    @GetMapping("/user/profile/{id}")
    ProfileRequestDto userFindProfile(@PathVariable("id") Integer id);

    @GetMapping("/user/gender/{id}")
    Map<String,Object> userFindGenderAge(@PathVariable("id") Integer id);


}
