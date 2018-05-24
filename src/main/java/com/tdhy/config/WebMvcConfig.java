package com.tdhy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.tdhy.web.interceptor.BaseInterceptor;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class WebMvcConfig extends WebMvcConfigurerAdapter{
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		log.info("支持跨域访问 /** ");
		//super.addCorsMappings(registry);
		registry.addMapping("/**");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//TODO
		log.info("拦截器 init ：{}", "successful ");
		registry.addInterceptor(new BaseInterceptor()).addPathPatterns("/**");
		super.addInterceptors(registry);
	}

	
	
}
