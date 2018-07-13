package com.ry.futures.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class RequestUtils {
	
	public static Map<String, Object> formatRequestParas(HttpServletRequest request)
	{
		Map<String, Object> map=new HashMap<String, Object>();
		for (Object key : request.getParameterMap().keySet()) {
			map.put(key.toString(), request.getParameter(key.toString()));
		}
		return map;
	}	
	
	
	public static String getIp(HttpServletRequest request){
		
		String ip = request.getRemoteAddr();
		ip = request.getHeader("X-Real-IP");
		return ip==null ? request.getRemoteAddr():ip;
	}
	public static String getUa(HttpServletRequest request){
		
		return request.getHeader("User-Agent");
	}
	
	public  static String getCuid(HttpServletRequest request){
		String guid=getCookie(request, "cuid");
		guid=guid==null?"":guid;
		return guid;
	}
	public static String getCookie(HttpServletRequest request, String key)
	{
		Cookie[] cks=request.getCookies();
		if(null == cks)
			return "";
		for (Cookie cookie : cks) {
			if(cookie.getName().equals(key))
				return cookie.getValue();
		}
		return "";
	}
	public static void setCookie(HttpServletResponse response,String key,String value)
	{
		response.addCookie(new Cookie(key, value));
	}
}
