package com.study.spring.base;

import base.config.MyLifeCycleConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LifeCycleTest {
    /**
     * @Import 注解测试
     */
    @Test
    public void beanLifeCycleConfiTest() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MyLifeCycleConfig.class);
        System.out.println("容器初始化");

        annotationConfigApplicationContext.close();
    }


}
