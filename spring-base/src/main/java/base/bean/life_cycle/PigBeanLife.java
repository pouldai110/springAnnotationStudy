package base.bean.life_cycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class PigBeanLife {
    public PigBeanLife() {
        System.out.println("DogBeanLife construct ");
    }

    @PostConstruct
    public void init() {
        System.out.println("PigBeanLife init ");
    }

    @PreDestroy
    public void destory() {
        System.out.println("PigBeanLife destory ");
    }

}
