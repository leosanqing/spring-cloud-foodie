package com.leosanqing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: leosanqing
 * @Date: 2019-12-05 08:24
 */
@Configuration
@EnableSwagger2
public class Swagger2 {


    // 配置Swagger2核心配置
    @Bean
    public Docket createRestApi() {
        //指定API类型为Swagger2
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors
                                .withClassAnnotation(RestController.class)
//                        .basePackage("com.leosanqing")
                )
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 标题
                .title("leosanqing商铺接口API")
                // 联系人
                .contact(new Contact("leosanqing",
                        "https://github.com/leosanqing/Java-Notes",
                        "stormleo@qq.com"))
                .version("1.0.1")
                // 网站地址
                .termsOfServiceUrl("https://github.com/leosanqing/Java-Notes")
                // 描述
                .description("电商平台API")
                .build();


    }

}
