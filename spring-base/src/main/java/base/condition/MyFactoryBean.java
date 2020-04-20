package base.condition;

import base.bean.base.FactoryCat;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class MyFactoryBean implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        return new FactoryCat("FactoryBean", 12);
    }

    @Override
    public Class<?> getObjectType() {
        return FactoryCat.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
