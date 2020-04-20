package base.bean.self_priming;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 通过参数方法调用注入
 */
@Component
public class CatAutoWiredBean2 {

    private BaseDemo baseDemo1;

    public BaseDemo getBaseDemo() {
        return baseDemo1;
    }

    @Autowired
    public void setBaseDemo(BaseDemo baseDemo1) {
        this.baseDemo1 = baseDemo1;
    }
}
