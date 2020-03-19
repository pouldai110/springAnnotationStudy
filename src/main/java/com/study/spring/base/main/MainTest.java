package com.study.spring.base.main;

import com.study.spring.base.bean.base.Person;
import com.study.spring.base.config.MyMainConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MyMainConfig.class);
        Person bean = annotationConfigApplicationContext.getBean(Person.class);
        System.out.println(bean);
        String[] names  = annotationConfigApplicationContext.getBeanNamesForType(Person.class);
        for(String name : names){
            System.out.println(name);
        }
    }
}
