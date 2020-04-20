package com.study.spring.springmvc;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;

import javax.servlet.annotation.WebServlet;

/**
 * @ClassName ApplocationStart
 * @Author: pouldai
 * @Date: 2020/4/14 15:59
 * @Description:
 * @Version V1.0
 */
@SpringBootApplication
@ServletComponentScan("com.study.spring.springmvc.servlet")
public class ApplocationStart {
    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(ApplocationStart.class);

        builder.headless(false)
                // .web(WebApplicationType.NONE)
                // .bannerMode(Banner.Mode.OFF)
                .run(args);
    }

}
