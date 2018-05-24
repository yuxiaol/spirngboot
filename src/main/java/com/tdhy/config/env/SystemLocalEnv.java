package com.tdhy.config.env;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/** 
 * 环境变量
 * @see SystemLocalEnv 
 * @author yuxiaolong@tiandihengye.com
 * @version 0.0.1  
 */
@Data
@Component
@ConfigurationProperties(prefix ="system.local")
public class SystemLocalEnv {
	private String url;
	
	private String type;
	
	private String username;
	
	private String password;
	
	private String driverClassName;
	/**
	 * 初始连接数
	 */
	private Integer initialSize;
	/**
	 * 最小连接数
	 */
	private Integer minIdle;
	/**
	 * 最大连接数
	 */
	private Integer  maxActive;
	/**
	 * 配置获取连接等待超时的时间
	 */
	private Integer maxWait;
	
	/** 
	* 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 
	*/ 
	private Integer timeBetweenEvictionRunsMillis;
	/**
	 * 配置一个连接在池中最小生存的时间，单位是毫秒 
	 */
	private Integer minEvictableIdleTimeMillis;
	
	private String validationQuery;
	
	private Boolean testWhileIdle;
	
	private Boolean testOnBorrow;
	
	private Boolean testOnReturn;
	/**
	 * 打开PSCache，并且指定每个连接上PSCache的大小 
	 */
	private Boolean poolPreparedStatements;
	
	private Integer maxPoolPreparedStatementPerConnectionSize;
	/**
	 * 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙 
	 */
	private String filters;
	/**
	 * 通过connectProperties属性来打开mergeSql功能；慢SQL记录
	 */
	private String connectionProperties;
	/**
	 * 合并多个DruidDataSource的监控数据
	 */
	private Boolean useGlobalDataSourceStat;
	
	private String imgPath;
	
	private String maxFileSize;
	
	private String MaxRequestSize;
}
