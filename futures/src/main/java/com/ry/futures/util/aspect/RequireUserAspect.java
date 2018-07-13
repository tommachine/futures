package com.ry.futures.util.aspect;

import com.ry.futures.bean.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class RequireUserAspect {

	@Pointcut("@annotation(com.ry.futures.util.annotation.RequireUser)")
	public void userPointCut() {

	}
	@Around("userPointCut()")
	public Object arroundUserPointCut(ProceedingJoinPoint joinPoint) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		User user = (User) request.getSession().getAttribute("user");
		if(user == null) {
			Map<String, Object> map = new HashMap<>();
			map.put("result", false);
			map.put("msg", "请您先登录");
			map.put("code", 0);
			return map;
		}
		Object result = joinPoint.proceed();
		return result;
	}
}
