package com.study.spring.springmvc.context;

import com.study.spring.springmvc.annotation.MyAutowired;
import com.study.spring.springmvc.annotation.MyController;
import com.study.spring.springmvc.annotation.MyService;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author pouldai
 * @version v1.0
 * @Description 模拟springioc 实现
 * @date 2020/4/14 下午6:37
 */
@NoArgsConstructor
public class MyApplicationContext {


    /**
     * benaFactory
     */

    private Map<String, Object> instanceMap = new ConcurrentHashMap<String, Object>();


    // 配置文件
    Properties config = new Properties();
    /**
     * class缓存
     */

    private List<String> classCache = new ArrayList<>();

    public MyApplicationContext(String location) {
        InputStream inputStream = null;
        try {
            //定位
            inputStream = this.getClass().getClassLoader().getResourceAsStream(location);

            config.load(inputStream);
            // 注册,把所有的class找出来
            String scanPackage = config.getProperty("scanPackage");

            doRegister(scanPackage);
            //
            doCreateBean();


            pulateBean();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param scanPackage 包名
     * @return void
     * @Description 查找所有符合条件的class，并保存在缓存中
     * @date 2020/4/14 下午4:35
     * @author pouldai
     * @version v1.0
     */
    private void doRegister(String scanPackage) {
        //扫描包名下对应的文件
        String filePath = "/" + scanPackage.replaceAll("\\.", "/");
        URL resource = this.getClass().getResource(filePath);
        File dir = new File(resource.getFile());
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                doRegister(scanPackage.concat(".").concat(file.getName()));
            } else {
                if (!file.getName().contains(".class")) {
                    continue;
                }
                classCache.add(scanPackage.concat(".").concat(file.getName()).replace(".class", ""));

            }
        }

    }


    public void doCreateBean() {
        // 检查注册信息
        if (classCache.size() == 0) {
            return;
        }
        try {
            for (String className : classCache) {
                //获取实体类
                Class<?> clazz = Class.forName(className);
                //初始化类
                //指定注解的类进行初始化
                // 初始化contller
                if (clazz.isAnnotationPresent(MyController.class)) {
                    String id = lowerFirstChar(clazz.getSimpleName());
                    instanceMap.put(id, clazz.newInstance());
                }
                if (clazz.isAnnotationPresent(MyService.class)) {
                    MyService myService = clazz.getAnnotation(MyService.class);
                    String id = myService.value();
                    if (!StringUtils.isEmpty(id)) {
                        instanceMap.put(id, clazz.newInstance());
                        continue;
                    }
                    Class<?>[] interfaces = clazz.getInterfaces();
                    //如果这个类实现了接口，就用接口的类型作为id
                    for (Class<?> i : interfaces) {
                        instanceMap.put(i.getName(), clazz.newInstance());
                    }
                    id = lowerFirstChar(clazz.getSimpleName());
                    instanceMap.put(id, clazz.newInstance());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //依赖注入
    private void pulateBean() {

        //首先要判断ioc容器中有没有东西
        if (instanceMap.isEmpty()) {
            return;
        }

        for (Entry<String, Object> entry : instanceMap.entrySet()) {

            //把所有的属性全部取出来，包括私有属性
            Field[] fields = entry.getValue().getClass().getDeclaredFields();

            for (Field field : fields) {

                if (!field.isAnnotationPresent(MyAutowired.class)) {
                    continue;
                }

                MyAutowired autowired = field.getAnnotation(MyAutowired.class);

                String id = autowired.value().trim();
                //如果id为空，也就是说，自己没有设置，默认根据类型来注入
                if ("".equals(id)) {
                    id = field.getType().getName();
                }
                field.setAccessible(true); //把私有变量开放访问权限

                try {
                    field.set(entry.getValue(), instanceMap.get(id));
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }

            }

        }

    }

    public Object getBean() {
        return null;

    }

    public Map<String, Object> getAll() {
        return instanceMap;
    }

    private String lowerFirstChar(String className) {
        char[] chars = className.toCharArray();
        chars[0] += 32;

        return String.valueOf(chars);
    }


    public Properties getConfig() {
        return config;
    }

}
