package base.config;

import base.bean.life_cycle.CatBeanLife;
import base.bean.life_cycle.DogBeanLife;
import base.bean.life_cycle.PesonBeanLifeBeanPostProcessor;
import base.bean.life_cycle.PigBeanLife;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 生命周期配置类
 */
@Configuration
public class MyLifeCycleConfig {
    @Bean(initMethod = "init", destroyMethod = "destory")
    public DogBeanLife dogBeanLife() {
        return new DogBeanLife();
    }

    @Bean
    public CatBeanLife catBeanLife() {
        return new CatBeanLife();
    }

    @Bean
    public PigBeanLife pigBeanLife() {
        return new PigBeanLife();
    }

}
