import com.study.spring.base.bean.self_priming.*;
import com.study.spring.base.config.MyAopConfig;
import com.study.spring.base.config.MySelfPrimingConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MyAopTest {
    /**
     * @Import 注解测试
     */
    @Test
    public void Test() throws Exception{
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MyAopConfig.class);
        System.out.println("容器初始化");
        Object  o  = annotationConfigApplicationContext.getBean("baseDemo");
        // aotuwire
        CatAutoWiredBean catAutoWiredBean = (CatAutoWiredBean) annotationConfigApplicationContext.getBean("catAutoWiredBean");
        CatAutoWiredBean1 catAutoWiredBean1 = (CatAutoWiredBean1) annotationConfigApplicationContext.getBean("catAutoWiredBean1");
        System.out.println("catAutoWiredBean=>name:"+catAutoWiredBean.getBaseDemo().getName()+ "==>age:"+catAutoWiredBean.getBaseDemo().getAge());

//      catAutoWiredBean.getEx();
        System.out.println("annotationConfigApplicationContext"+annotationConfigApplicationContext);
        annotationConfigApplicationContext.close();
    }



}
