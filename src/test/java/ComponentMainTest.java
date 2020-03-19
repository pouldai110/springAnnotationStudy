import com.study.spring.base.bean.base.FactoryCat;
import com.study.spring.base.bean.base.Person;
import com.study.spring.base.condition.MyFactoryBean;
import com.study.spring.base.config.MyImportMainConfig;
import com.study.spring.base.config.MyMainConfig;
import com.study.spring.base.config.MyLifeCycleConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ComponentMainTest {
    @Test
    public void testBean() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MyMainConfig.class);

        String[] names  = annotationConfigApplicationContext.getBeanDefinitionNames();
        System.out.println("容器初始化");
        Person person = (Person) annotationConfigApplicationContext.getBean("person");
        Person person1= (Person) annotationConfigApplicationContext.getBean("person");
        for(String name : names){
            System.out.println(name);
        }


        // 获取myFactoryBean其实是获取getObject()方法的对象
        Object bean = annotationConfigApplicationContext.getBean("myFactoryBean");
        System.out.println(bean.getClass().toString());
        FactoryCat cat = (FactoryCat)annotationConfigApplicationContext.getBean("myFactoryBean");
        System.out.println(cat.getName());
        // 在对象名称前加上& 可以获取其具体类
        Object bean1 = annotationConfigApplicationContext.getBean("&myFactoryBean");
        System.out.println(bean1);
        MyFactoryBean myFactoryBean = (MyFactoryBean) annotationConfigApplicationContext.getBean("&myFactoryBean");
    }

    /**
     * @Import 注解测试
     */
     @Test
    public void importTest() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MyImportMainConfig.class);
        String[] names  = annotationConfigApplicationContext.getBeanDefinitionNames();
        System.out.println("容器初始化");
        for(String name : names){
            System.out.println(name);
        }
    }

    /**
     * @Import 注解测试
     */
    @Test
    public void beanLifeCycleConfiTest() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MyLifeCycleConfig.class);
        System.out.println("容器初始化");

        annotationConfigApplicationContext.close();
    }



}
