package base.bean.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PersonProperty {
    /**
     * 基本数值
     */

    @Value("test")
    private String name;
    /**
     * 可以写SpEL spring el 表达式
     */
    @Value("#{18*2}")
    private Integer age;
    /**
     * 可以写${}
     */
    @Value("${birthDay}")
    private String bithDay;

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

    public String getBithDay() {
        return bithDay;
    }

    public void setBithDay(String bithDay) {
        this.bithDay = bithDay;
    }

    @Override
    public String toString() {
        return "PersonProperty{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", bithDay='" + bithDay + '\'' +
                '}';
    }
}
