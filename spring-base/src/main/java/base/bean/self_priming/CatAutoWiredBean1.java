package base.bean.self_priming;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 属性标记自动注入
 *
 * @Qualifier 指定beanid
 */
@Component
public class CatAutoWiredBean1 {
    @Autowired
    @Qualifier("baseDemo2")
    private BaseDemo baseDemo;

    public BaseDemo getBaseDemo() {
        return baseDemo;
    }

    public void setBaseDemo(BaseDemo baseDemo) {
        this.baseDemo = baseDemo;
    }
}
