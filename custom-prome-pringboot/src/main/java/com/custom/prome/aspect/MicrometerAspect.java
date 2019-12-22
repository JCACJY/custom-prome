package com.custom.prome.aspect;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect
public class MicrometerAspect {
    /**
     * 控制器切入点
     */
    @Pointcut("execution(public * com.custom.prome..controller.*.*(..))")
    public void controllerCutPoint(){}
    /**
     * 服务层切入点
     */
    @Pointcut("execution(public * com.custom.prome..service.*.*(..))")
    public void serviceCutPoint(){}
    /**
     * 持久层切入点
     */
    @Pointcut("execution(public * com.custom.prome..dao.*.*(..))")
    public void daoCutPoint(){}
    /**
     * 排斥切入点1
     */
    @Pointcut("!execution(public * com.custom.prome.config.PromeJavaConfig.*(..))")
    public void cutPointExclude1(){}
    /**
     * 排除切入点2
     */
    @Pointcut("!execution(public * io.micrometer.core.instrument.binder.MeterBinder+.*(..))")
    public void cutPointExclude2(){}
    /**
     * 环绕通知
     */
    @Around("(controllerCutPoint() || serviceCutPoint() || daoCutPoint())&& cutPointExclude1() && cutPointExclude2()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Class declaringType = methodSignature.getDeclaringType();
        String packageName= declaringType.getPackage().getName();
        String layer = packageName.substring(packageName.lastIndexOf(".")+1);

        //方法执行时长
        Timer timer = Metrics.timer("method.cost.time", "method.layer",layer,"method.name", method.getName());
        //方法执行次数
        Counter execCount = Metrics.counter("exec.count", "method.layer",layer,"method.name", method.getName());
        //方法执行异常次数
        Counter execErrorCount = Metrics.counter("exec.error.count", "method.layer",layer,"method.name", method.getName());

        InnerThrowable innerThrowable = new InnerThrowable();
        Object result = timer.recordCallable(() -> {
            try {
                execCount.increment();
                return joinPoint.proceed();
            } catch (Throwable e) {
                innerThrowable.throwable = e;
            }
            return null;
        });
        if (innerThrowable.throwable != null) {
            execErrorCount.increment();
            throw innerThrowable.throwable;
        }
        return result;
    }

    private class InnerThrowable {
        Throwable throwable;
    }
}
