package com.ry.futures.util.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Target;

@Documented
@Inherited
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface RedisCache {
    Class type();

    //缓存保存时间
    int cacheTime() default 600;
}
