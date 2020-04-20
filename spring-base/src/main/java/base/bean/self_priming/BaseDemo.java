package base.bean.self_priming;

import org.springframework.stereotype.Component;

@Component
public class BaseDemo {
    private String name;

    private Integer age;

    public BaseDemo() {
        this.age = 18;
        this.name = "autowire";
    }

    public BaseDemo(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
