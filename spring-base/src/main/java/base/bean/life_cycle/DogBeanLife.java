package base.bean.life_cycle;

public class DogBeanLife {
    public DogBeanLife() {
        System.out.println("DogBeanLife construct ");
    }

    public void init() {
        System.out.println("DogBeanLife init ");
    }

    public void destory() {
        System.out.println("DogBeanLife destory ");
    }

}
