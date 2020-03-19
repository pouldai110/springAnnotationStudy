package com.study.spring.base.bean.self_priming;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 通过有参构造方法自动注入自动注入
 */
@Component
public class CatAutoWiredBean3 {

    private BaseDemo baseDemo;

    @Autowired
    public CatAutoWiredBean3(BaseDemo baseDemo) {
        this.baseDemo = baseDemo;
    }

    public BaseDemo getBaseDemo() {
        return baseDemo;
    }

    public void setBaseDemo(BaseDemo baseDemo) {
        this.baseDemo = baseDemo;
    }
}
