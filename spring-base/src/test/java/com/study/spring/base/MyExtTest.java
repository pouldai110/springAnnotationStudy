package com.study.spring.base;

import base.bean.self_priming.CatAutoWiredBean;
import base.bean.self_priming.CatAutoWiredBean1;
import base.config.MyAopConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MyExtTest {
    /**
     * @Import 注解测试
     */
    @Test
    public void Test() throws Exception {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MyAopConfig.class);
        System.out.println("容器初始化");

        System.out.println("annotationConfigApplicationContext" + annotationConfigApplicationContext);
        annotationConfigApplicationContext.close();
    }


}
