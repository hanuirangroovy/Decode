package com.ssafy.authsvr.config;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class SwaggerConfig {

    /*
     * Swagger API 문서
     * */
    @Bean
    public Docket swaggerAPI() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.swaggerInfo())    // 스웨거 정보 등록
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(true);  // 기본 세팅되는 200, 401, 403, 404 표시
    }

    /*
     * Swagger 정보
     * */
    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder()
                .title("Decode - 방탈출 카페 추천 프로젝트")
                .description("개인의 성향에 따라서 방탈출 카페를 추천해주는 서비스")
                .version("1.0.0")
                .build();
    }

}