package base.bean.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

// @Aspect 需要 spring-aspects 相关依赖 aspectjweaver.jar 和 assertj-spring.orm.core.framework.core-3.13.2.jar
@Aspect
@Component
public class Log {
    //    execution()是最常用的切点函数，其语法如下所示：
//
//    整个表达式可以分为五个部分：
//
//            1、execution(): 表达式主体。
//
//            2、第一个*号：表示返回类型，*号表示所有的类型。
//
//            3、包名：表示需要拦截的包名，后面的两个句点表示当前包和当前包的所有子包，com.sample.service.impl包、子孙包下所有类的方法。
//
//            4、第二个*号：表示类名，*号表示所有的类。
//
//            5、*(..):最后这个星号表示方法名，*号表示所有的方法，后面括弧里面表示方法的参数，两个句点表示任何参数。
    @Pointcut("execution(* base.bean..*.*(..))")
    public void pointCut() {
    }


    /**
     * 方法调用前
     *
     * @param point
     */
    @Before("pointCut()")
    public void aspectBefore(JoinPoint point) {
        System.out.println(point.getSignature().getName() + "方法调用前");
    }


    /**
     * 方法调用后
     *
     * @param point
     */
    @After("pointCut()")
    public void aspectAfter(JoinPoint point) {
        System.out.println(point.getSignature().getName() + "方法调用后");
    }


    /**
     * 方法返回值后
     *
     * @param point
     */
    @AfterReturning(value = "pointCut()", returning = "value")
    public void aspectReturning(JoinPoint point, Object value) {
        System.out.println(point.getSignature().getName() + "方法返回值:" + value);
    }

    /**
     * 方法抛出异常
     *
     * @param point
     */
    @AfterThrowing(value = "pointCut()", throwing = "ex")
    public void aspectThrowing(JoinPoint point, Exception ex) {
        System.out.println(point.getSignature().getName() + "方法异常" + ex.getMessage());
    }

    /**
     * 同时在所拦截方法的前后
     *
     * @param point
     */
    @Around("pointCut()")
    public Object aspectAround(ProceedingJoinPoint point) throws Throwable {
        Object result;
        Boolean isError = Boolean.FALSE;
        long beginTime = System.currentTimeMillis();
        try {
            // 执行方法
            result = point.proceed();
        } catch (Throwable e) {
            isError = Boolean.TRUE;
            throw e;
        } finally {
            // 执行时长(毫秒)
            long time = System.currentTimeMillis() - beginTime;
            System.out.println(point.getSignature().getName() + "方法执行时长:" + time);
        }
        return result;
    }


}
