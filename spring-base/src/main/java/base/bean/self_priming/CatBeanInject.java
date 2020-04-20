package base.bean.self_priming;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.inject.Inject;

@Component
public class CatBeanInject {
    @Inject
    private BaseDemo baseDemo;

    public BaseDemo getBaseDemo() {
        return baseDemo;
    }

    public void setBaseDemo3(BaseDemo baseDemo) {
        this.baseDemo = baseDemo;
    }
}
