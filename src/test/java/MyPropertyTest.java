import com.study.spring.base.bean.property.PersonProperty;
import com.study.spring.base.config.MyLifeCycleConfig;
import com.study.spring.base.config.MyPropertyConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MyPropertyTest {
    /**
     * @Import 注解测试
     */
    @Test
    public void Test() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MyPropertyConfig.class);
        System.out.println("容器初始化");

        PersonProperty personProperty = (PersonProperty) annotationConfigApplicationContext.getBean("personProperty");
        System.out.println(personProperty.toString());
        annotationConfigApplicationContext.close();
    }



}
