package spring.orm.core;

import javassist.bytecode.ByteArray;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

/**
 * @ClassName EntityClassUtils
 * @Author: pouldai
 * @Date: 2020/4/08 21:53
 * @Description: 实体文件工具类
 * @Version V1.0
 */
public class EntityClassUtils {

    private EntityClassUtils() {
    }

    /**
     * 操作工具支持的类型
     */
    static Set<Class<?>> SUPPORT_OBJECTS = new HashSet<Class<?>>();

    /**
     * 静态块执行
     */
    static {
        /**
         * 读取需要映射的数据类
         */
        Class<?>[] classes = {
                byte.class, Byte.class,
                boolean.class, Boolean.class,
                short.class, Short.class,
                int.class, Integer.class,
                long.class, Long.class,
                float.class, Float.class,
                double.class, Double.class,
                String.class,
                Date.class,
                LocalDate.class,
                Timestamp.class,
                BigInteger.class,
                BigDecimal.class,
                ByteArray.class,
                List.class
        };
        /**
         * 填充支持工具类的数据类型
         */
        SUPPORT_OBJECTS.addAll(Arrays.asList(classes));
    }

    /**
     * @param clazz
     * @return boolean
     * @Description 判断类是否为枚举类或者需要读取的数据类
     * @date 2020/4/8 10:09
     * @author pouldai
     * @version v1.0
     */
    static boolean isSupportObject(Class<?> clazz) {
        return clazz.isEnum() || SUPPORT_OBJECTS.contains(clazz);
    }

    /**
     * @param clazz
     * @return java.util.Map<java.lang.String   ,   java.lang.reflect.Method>
     * @Description 获取指定类的所有公用的get方法
     * @date 2020/4/12 下午10:13
     * @author pouldai
     * @version v1.0
     */
    public static Map<String, Method> getPublicGetters(Class<?> clazz) {
        Map<String, Method> getterMap = new HashMap<String, Method>();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {   //  忽略静态方法
            if (Modifier.isStatic(method.getModifiers())) {
                continue;
            }
            //  忽略带参数的方法
            if (method.getParameterTypes().length != 0) {
                continue;
            }
            //  忽略getClass方法
            if ("getClass".equals(method.getName())) {
                continue;
            }
            // 获取方法返回类型
            Class<?> returnType = method.getReturnType();
            String methodName = method.getName();
            //  忽略void返回值的方法
            if (void.class.equals(returnType)) {
                continue;
            }
            // 忽略的不进行获取的数据类
            if (!isSupportObject(returnType)) {
                continue;
            }
            // boolean类型发get方法为is
            if ((returnType.equals(boolean.class)
                    || returnType.equals(Boolean.class))
                    && methodName.startsWith("is")
                    && methodName.length() > 2) {
                putGetterMap(getterMap, method);
                continue;
            }
            // 忽略不是get开头的方法
            if ("get".equals(methodName) || !methodName.startsWith("get")) {
                continue;
            }
            putGetterMap(getterMap, method);
        }
        return getterMap;
    }

    /**
     * @param getterMap get的map对象
     * @param method    方法体
     * @return void
     * @Description 将对象的get方法以属性名称-方法的键值对保存
     * @date 2020/4/12 下午10:40
     * @author pouldai
     * @version v1.0
     */
    private static void putGetterMap(Map<String, Method> getterMap, Method method) {
        String methodName = method.getName();
        String filedName = "";
        if (methodName.startsWith("is")) {
            filedName = methodName.substring(2);
        } else {
            filedName = methodName.substring(3);
        }
        filedName = Character.toLowerCase(filedName.charAt(0)) + filedName.substring(1);

        getterMap.put(filedName, method);

    }

    /**
     * @param clazz
     * @return java.util.Map<java.lang.String   ,   java.lang.reflect.Method>
     * @Description 获取指定类的所有公用的set方法
     * @date 2020/4/12 下午10:14
     * @author pouldai
     * @version v1.0
     */
    public static Map<String, Method> getPublicSetters(Class<?> clazz) {
        Map<String, Method> setterMap = new HashMap<String, Method>();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            //  忽略静态方法
            if (Modifier.isStatic(method.getModifiers())) {
                continue;
            }
            //  忽略带参数不等于1的方法
            if (method.getParameterTypes().length != 1) {
                continue;
            }
            //  忽略不是指定监听数据类型的数据
            if (!isSupportObject(method.getParameterTypes()[0])) {
                continue;
            }
            // 获取方法返回类型
            Class<?> returnType = method.getReturnType();
            String methodName = method.getName();


            //  忽略不为void返回值的方法
            if (!void.class.equals(returnType)) {
                continue;
            }
            // 忽略不是set开头的方法
            if ("set".equals(methodName) || !methodName.startsWith("set")) {
                continue;
            }
            putSetterMap(setterMap, method);

        }

        return setterMap;
    }

    /**
     * @param setterMap set的map对象
     * @param method    方法体
     * @return void
     * @Description 将对象的set方法以属性名称-方法的键值对保存
     * @date 2020/4/12 下午10:40
     * @author pouldai
     * @version v1.0
     */
    private static void putSetterMap(Map<String, Method> setterMap, Method method) {
        String methodName = method.getName();
        String filedName = methodName.substring(3);

        filedName = Character.toLowerCase(filedName.charAt(0)) + filedName.substring(1);

        setterMap.put(filedName, method);

    }

    /**
     * @param clazz
     * @return java.lang.reflect.Field[]
     * @Description 获取类的属性值
     * @date 2020/4/12 下午10:40
     * @author pouldai
     * @version v1.0
     */
    public static Field[] getFiled(Class<?> clazz) {
        return clazz.getDeclaredFields();
    }
}
