import com.study.spring.base.bean.self_priming.*;
import com.study.spring.base.config.MyLifeCycleConfig;
import com.study.spring.base.config.MySelfPrimingConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.StringValueResolver;

public class MySelfPrimingTest {
    /**
     * @Import 注解测试
     */
    @Test
    public void Test() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MySelfPrimingConfig.class);
        System.out.println("容器初始化");
        // aotuwire
        CatAutoWiredBean catAutoWiredBean = (CatAutoWiredBean) annotationConfigApplicationContext.getBean("catAutoWiredBean");
        System.out.println("catAutoWiredBean=>name:"+catAutoWiredBean.getBaseDemo().getName()+"==>age:"+catAutoWiredBean.getBaseDemo().getAge());
        CatAutoWiredBean1 catAutoWiredBean1 = (CatAutoWiredBean1) annotationConfigApplicationContext.getBean("catAutoWiredBean1");
        System.out.println("catAutoWiredBean1=>name:"+catAutoWiredBean1.getBaseDemo().getName()+"==>age:"+catAutoWiredBean1.getBaseDemo().getAge());
        CatAutoWiredBean2 catAutoWiredBean2 = (CatAutoWiredBean2) annotationConfigApplicationContext.getBean("catAutoWiredBean2");
        System.out.println("catAutoWiredBean2=>name:"+catAutoWiredBean2.getBaseDemo().getName()+"==>age:"+catAutoWiredBean2.getBaseDemo().getAge());
        CatAutoWiredBean3 catAutoWiredBean3= (CatAutoWiredBean3) annotationConfigApplicationContext.getBean("catAutoWiredBean3");
        System.out.println("catAutoWiredBean3=>name:"+catAutoWiredBean3.getBaseDemo().getName()+"==>age:"+catAutoWiredBean3.getBaseDemo().getAge());
        // resource
        CatBeanResource catBeanResource = (CatBeanResource) annotationConfigApplicationContext.getBean("catBeanResource");
        System.out.println("CatBeanResource=>name:"+catBeanResource.getBaseDemoResource().getName()+"==>age:"+catBeanResource.getBaseDemoResource().getAge());
        // inject
        CatBeanInject catBeanInject = (CatBeanInject) annotationConfigApplicationContext.getBean("catBeanInject");
        System.out.println("catBeanInject=>name:"+catBeanInject.getBaseDemo().getName()+"==>age:"+catBeanInject.getBaseDemo().getAge());
        CatBeanInjectQualifier catBeanInjectQualifier = (CatBeanInjectQualifier) annotationConfigApplicationContext.getBean("catBeanInjectQualifier");
        System.out.println("catBeanInjectQualifier=>name:"+catBeanInjectQualifier.getBaseDemo().getName()+"==>age:"+catBeanInjectQualifier.getBaseDemo().getAge());

        //aware

        MyAwareDemo myAwareDemo = (MyAwareDemo) annotationConfigApplicationContext.getBean("myAwareDemo");
        System.out.println(myAwareDemo.toString());


        System.out.println("annotationConfigApplicationContext"+annotationConfigApplicationContext);
        annotationConfigApplicationContext.close();
    }



}
