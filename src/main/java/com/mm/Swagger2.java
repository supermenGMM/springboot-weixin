package com.mm;

import com.mm.vo.OrderIdVo;
import com.mm.vo.ResponseVo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2 {
    @Bean
    public Docket createRestApi() {

        List<ResponseMessage> responseMessageList = new ArrayList<>();
//        responseMessageList.add(new ResponseMessageBuilder().code(404).message("找不到资源").responseModel(new ModelRef("ResponseVo")).build());
//        responseMessageList.add(new ResponseMessageBuilder().code(409).message("业务逻辑异常").responseModel(new ModelRef("ResponseVo")).build());
//        responseMessageList.add(new ResponseMessageBuilder().code(422).message("参数校验异常").responseModel(new ModelRef("ResponseVo")).build());
//        responseMessageList.add(new ResponseMessageBuilder().code(500).message("服务器内部错误").responseModel(new ModelRef("ResponseVo")).build());
//        responseMessageList.add(new ResponseMessageBuilder().code(503).message("Hystrix异常").responseModel(new ModelRef("ResponseVo")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(503).message("未知异常").responseModel(new ModelRef("ResponseVo")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(200).message("成功").responseModel(new ModelRef("ResponseVo")).build());

        return new Docket(DocumentationType.SWAGGER_2)
            .globalResponseMessage(RequestMethod.GET,responseMessageList)
            .globalResponseMessage(RequestMethod.PUT,responseMessageList)
            .globalResponseMessage(RequestMethod.DELETE,responseMessageList)
            .globalResponseMessage(RequestMethod.POST,responseMessageList)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.mm.controller"))
            .paths(PathSelectors.any())
            .build()
//            .ignoredParameterTypes(OrderIdVo.class)//或略某个入参
            ;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("点餐系统")
            .description("微信平台开发")
            .termsOfServiceUrl("http://supermeng.natapp.cc1/sell")
            .contact("supermenG")
            .version("1.0")
            .build();
    }
}
