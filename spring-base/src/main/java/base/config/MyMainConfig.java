package base.config;

import base.bean.base.Person;
import base.condition.LiunxCondition;
import base.condition.WindowsCondition;
import org.springframework.context.annotation.*;

@Configuration  //告诉spring这是个配置类,等同于spring中的xml 配置

@ComponentScan(value = "base"
//       excludeFilters = {@Filter(type = FilterType.ANNOTATION,classes = {Controller.class})}
//        includeFilters = {@Filter(type = FilterType.ANNOTATION,classes = {Service.class})},useDefaultFilters = false
//        includeFilters = {@Filter(type = FilterType.CUSTOM,classes = {MyComponentFliter.class})},useDefaultFilters = false
)
// ComponentScan 包扫描注解,可以扫描指定包下的 @bean @Repository @Service @Controller @Component 的注解
// excludeFilters 过滤指定参数类型的注解
// includeFilters 只扫描指定参数类型的注解，必须设置useDefaultFilters=false
// FilterType:
//     ANNOTATION, // 按照注解类型
//    ASSIGNABLE_TYPE, // 按照给定类的类型
//    ASPECTJ, //使用ASPECTJ 表达式
//    REGEX, //使用正则表达式
//    CUSTOM; //使用自定义规则
public class MyMainConfig {
    // 在容器中注册一个Bean，返回值为person类型，id默认为方法名作为id
    @Scope("prototype")
    // siglton 单例 容器中只有一个实例 默认
    // prototype  多例 每次请求都会初始化一个实例到容器中
    // request 同一请求用同一个实例
    // session 同一会话用同一个实例
    @Lazy
    // 懒加载，不会在容器初始化时声明实例，在调用时声明实例
    @Bean
    public Person person() {
        System.out.println("初始化李四");
        return new Person("李四", 20);
    }


    @Bean("zhangsan")
    public Person person1() {
        System.out.println("初始化 张三");
        return new Person("张三", 20);
    }

    @Bean("liunx")
    @Conditional(LiunxCondition.class)
    // Conditional 条件注册bean 实现condition接口进行判断
    public Person personLiunx() {
        System.out.println("初始化 liunx");
        return new Person("liunx", 20);
    }


    @Bean("windows")
    @Conditional(WindowsCondition.class)
    public Person personWindows() {
        System.out.println("初始化 windows");
        return new Person("windows", 20);
    }
}
