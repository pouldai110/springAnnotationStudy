package base.config;

import base.bean.self_priming.BaseDemo;
import org.springframework.context.annotation.*;

@Configuration  //告诉spring这是个配置类,等同于spring中的xml 配置
@ComponentScan(value = "base.bean.ext")
public class MyExtConfig {

}
