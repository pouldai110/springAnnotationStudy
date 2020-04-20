package base.bean.self_priming;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 属性标记自动注入
 * 根据类型注入,如果找到多个相同类型的组件，再将属性的名称作为组件的id
 */
@Component
public class CatAutoWiredBean {

    @Autowired
    private BaseDemo baseDemo;

    public CatAutoWiredBean() {
    }

    public BaseDemo getBaseDemo() {
        return baseDemo;
    }

    public void setBaseDemo(BaseDemo baseDemo) {
        this.baseDemo = baseDemo;
    }

}
