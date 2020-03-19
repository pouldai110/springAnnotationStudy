package com.study.spring.base.bean.self_priming;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;
@Component
public class MyAwareDemo  implements ApplicationContextAware, EmbeddedValueResolverAware {
    private String name;

    private Integer age;

    private String birth;
    private ApplicationContext applicationContext;

    private StringValueResolver stringValueResolver;

    public MyAwareDemo() {
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public StringValueResolver getStringValueResolver() {
        return stringValueResolver;
    }

    public void setStringValueResolver(StringValueResolver stringValueResolver) {
        this.stringValueResolver = stringValueResolver;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MyAwareDemo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birth='" + birth + '\'' +
                ", applicationContext=" + applicationContext +
                ", stringValueResolver=" + stringValueResolver +
                '}';
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }



    @Override
    public void setEmbeddedValueResolver(StringValueResolver stringValueResolver) {
        this.stringValueResolver = stringValueResolver;
        this.birth = stringValueResolver.resolveStringValue("${birthDay}");
    }
}
