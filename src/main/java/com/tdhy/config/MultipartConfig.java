package com.tdhy.config;

import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tdhy.config.env.SystemLocalEnv;

import lombok.extern.slf4j.Slf4j;

/** 
 * 上传配置
 * @see MultipartConfig 
 * @author yuxiaolong@tiandihengye.com
 * @version 0.0.1  
 */
@Configuration
@Slf4j
public class MultipartConfig {
	
	@Autowired
	SystemLocalEnv systemLocalEnv;
	
	@Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(systemLocalEnv.getMaxFileSize());
        factory.setMaxRequestSize(systemLocalEnv.getMaxRequestSize());
        log.info(" 上传配置Bean  successful ");
        return factory.createMultipartConfig();
    }
}
