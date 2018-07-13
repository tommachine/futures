package com.ry.futures.util.aspect;

import com.alibaba.fastjson.JSON;
import com.ry.futures.util.RedisDao;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareAnnotation;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class RedisCache {
    @Autowired
    private RedisDao redisDao;

    @Pointcut("@annotation(com.ry.futures.util.annotation.RedisCache)")
    public void redisCache(){

    }

    @Around("redisCache()")
    public Object around(ProceedingJoinPoint pjp){
        //得到类名、方法名和参数
        Class cl = pjp.getClass();  //获取Class对象
        Signature signature = pjp.getSignature(); //获取签名

        String className = cl.getName();        //获取className
        String methodName = signature.getName();//获取methodName
        System.out.println(className);
        System.out.println(methodName);
        Object[] args = pjp.getArgs();         //获取参数

        //根据类名，方法名和参数生成key
        String key = genKey(className,methodName,args);
        //得到被代理的方法
        if(!(signature instanceof MethodSignature)){
            throw  new IllegalArgumentException();
        }
        Object result = null;
        try {
            result = pjp.proceed(args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    };

    /**
     * @Description: 生成key
     * @Param:
     * @return:
     * @Author:
     * @Date: 2018/5/16
     */
    private String genKey(String className, String methodName, Object[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append(className);
        sb.append("_");
        sb.append(methodName);
        sb.append("_");
        for (Object object: args) {
            if(object!=null) {
                sb.append(object+"");
                sb.append("_");
            }
        }
        return sb.toString();
    }
}
