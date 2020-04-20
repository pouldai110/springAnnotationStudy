package base.config;

import base.bean.base.Animal;
import base.condition.MyImportBeanDefinitionRegistrar;
import base.condition.MyImportSelector;
import org.springframework.context.annotation.*;

@Configuration
@Import({Animal.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
// Animal.class  直接导入相关类,bean id默认为class 全名称
//  MyImportSelector.class 导入选择器，返回满足条件的类数组
// MyImportBeanDefinitionRegistrar.class 使用手动注册的方式，将bean注入到容器中
public class MyImportMainConfig {

}
