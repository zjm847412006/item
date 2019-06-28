package com.jt.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import redis.clients.jedis.JedisCluster;
@Component	//将拦截器交给spring容器管理
public class UserInterceptor implements HandlerInterceptor{
	
	@Autowired
	private JedisCluster jedisCluster;
	
	/**
	 * 在spring4版本中要求必须重写3个方法,不管是否需要
	 * 在spring5版本中在接口中添加default属性,则省略不写.
	 */
	
	/**
	 * 返回值结果:
	 * 	true: 拦截放行.
	 * 	false: 请求拦截. 重定向到登录页面
	 * 
	 * 业务逻辑:
	 * 	1.获取Cookie数据
	 *  2.从cookie中获取token(TICKET)
	 *  3.判断redis缓存服务器中是否有数据.
	 */
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = null;
		//1.获取Cookie信息
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if("JT_TICKET".equals(cookie.getName())) {
				token = cookie.getValue();
				break;
			}
		}
		
		//2.判断token是否有效
		if(!StringUtils.isEmpty(token)) {
			//4.判断redis中是否有数据
			String userJSON = jedisCluster.get(token);
			
			if(!StringUtils.isEmpty(userJSON)) {
				//redis中有用户数据.拦截放行
				return true;
			}
		}
		//3.重定向到用户登录页面
		response.sendRedirect("/user/login.html");
		return false;//表示拦截
	}
}
