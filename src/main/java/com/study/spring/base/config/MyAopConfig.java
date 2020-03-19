package com.study.spring.base.config;

import com.study.spring.base.bean.self_priming.BaseDemo;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;

@Configuration  //告诉spring这是个配置类,等同于spring中的xml 配置
/**
 * @Aspect 生效需要改注解
 */
@EnableAspectJAutoProxy
@ComponentScan(value = "com.study.spring.base.bean")
public class MyAopConfig {
    @Bean
    public BaseDemo baseDemo2(){
        return  new BaseDemo("autowire2",26);
    }
    @Primary
    @Bean
    public BaseDemo baseDemo3(){
        return  new BaseDemo("autowire3",26);
    }


    @Bean
    public BaseDemo baseDemoResource(){
        return  new BaseDemo("resource",26);
    }


    @Bean
    public BaseDemo baseDemoInject(){
        return  new BaseDemo("inject",26);
    }
}
