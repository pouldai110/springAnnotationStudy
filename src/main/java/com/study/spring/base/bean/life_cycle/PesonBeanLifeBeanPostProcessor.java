package com.study.spring.base.bean.life_cycle;

import com.study.spring.base.bean.self_priming.BaseDemo;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * @author dyp
 */
@Component
public class PesonBeanLifeBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName + " : before init");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName + " : after init");
        if(beanName.contains("baseDemo")){
            BaseDemo baseDemo =(BaseDemo)bean;
            System.out.println("baseDemo"+baseDemo.getName());
        }
        return bean;
    }
}
