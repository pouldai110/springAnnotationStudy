package base.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

// 使用@PropertySource读取外部配置文件中的k/v保存到运行的环境变量中;加载完外部的配置文件以后使用${}取出配置文件的值
// 文件地址 编译后地址
@Configuration

@PropertySource({"classpath:/my.properties"})
@ComponentScan("base.bean.property")
public class MyPropertyConfig {
}
