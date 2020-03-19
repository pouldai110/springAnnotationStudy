package com.study.spring.base.bean.self_priming;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
@Component
public class CatBeanResource {
    @Resource
    // @Qualifier("baseDemo1") 无效 不支持
    private BaseDemo baseDemoResource;

    public BaseDemo getBaseDemoResource() {
        return baseDemoResource;
    }

    public void setBaseDemoResource(BaseDemo baseDemoResource) {
        this.baseDemoResource = baseDemoResource;
    }

    public CatBeanResource() {

    }
}
