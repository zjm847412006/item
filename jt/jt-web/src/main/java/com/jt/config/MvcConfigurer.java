package com.jt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jt.interceptor.UserInterceptor;

@Configuration    
public class MvcConfigurer implements WebMvcConfigurer{
	
	@Autowired
	private UserInterceptor userInterceptor;
	
	//开启匹配后缀型配置
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		
		configurer.setUseSuffixPatternMatch(true);
	}
	
	//新增拦截器配置
	//url:http://www.jt.com/cart/xxxxx
	//url:http://www.jt.com/order/xxxxx  
	//	/* 只能拦截下一级路径 
	//  /** 拦截所有的路径
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(userInterceptor)
				.addPathPatterns("/cart/**","/order/**");
	}
}
