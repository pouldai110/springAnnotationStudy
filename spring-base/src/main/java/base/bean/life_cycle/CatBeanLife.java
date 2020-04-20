package base.bean.life_cycle;

import com.sun.org.apache.xml.internal.security.Init;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class CatBeanLife implements InitializingBean, DisposableBean {
    public CatBeanLife() {
        System.out.println("CatBeanLife construct ");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("CatBeanLife destroy ");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("CatBeanLife init ");
    }
}
