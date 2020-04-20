package base.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class LiunxCondition implements Condition {
    /**
     * @param conditionContext      判断条件能使用的上下文（环境）
     * @param annotatedTypeMetadata 注释信息
     * @return
     */
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        // 能获取到ioc使用的beanfactory
        ConfigurableListableBeanFactory beanFactory = conditionContext.getBeanFactory();
        //类加载器
        ClassLoader classLoader = conditionContext.getClassLoader();
        // 前环境信息
        Environment environment = conditionContext.getEnvironment();
        // 获取到bean定义的注册类
        BeanDefinitionRegistry registry = conditionContext.getRegistry();
        // 资源加载器
        ResourceLoader resourceLoader = conditionContext.getResourceLoader();
        String system = environment.getProperty("os.name");
        System.out.println(system);
        if (system.toLowerCase().contains("unix")) {
            return true;
        }
        return false;
    }
}
