package com.tdhy.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;
import com.tdhy.config.env.SystemLocalEnv;

import lombok.extern.slf4j.Slf4j;


/** 
 * druid 数据连接池配置
 * @see DruidDBConfig 
 * @author yuxiaolong@tiandihengye.com
 * @version 0.0.1  
 */
@Configuration
@Slf4j
public class DruidDBConfig {

	@Autowired
	private SystemLocalEnv dataSourceEnv;
	
	 @Bean // 声明其为Bean实例
     @Primary // 在同样的DataSource中，首先使用被标注的DataSource
     public DataSource dataSource() {
         DruidDataSource datasource = new DruidDataSource();

         datasource.setUrl(dataSourceEnv.getUrl());
         datasource.setUsername(dataSourceEnv.getUsername());
         datasource.setPassword(dataSourceEnv.getPassword());
         datasource.setDriverClassName(dataSourceEnv.getDriverClassName());
         // configuration
         datasource.setInitialSize(dataSourceEnv.getInitialSize());
         datasource.setMinIdle(dataSourceEnv.getMinIdle());
         datasource.setMaxActive(dataSourceEnv.getMaxActive());
         datasource.setMaxWait(dataSourceEnv.getMaxWait());
         datasource.setTimeBetweenEvictionRunsMillis(dataSourceEnv.getTimeBetweenEvictionRunsMillis());
         datasource.setMinEvictableIdleTimeMillis(dataSourceEnv.getMinEvictableIdleTimeMillis());
         datasource.setValidationQuery(dataSourceEnv.getValidationQuery());
         datasource.setTestWhileIdle(dataSourceEnv.getTestWhileIdle());
         datasource.setTestOnBorrow(dataSourceEnv.getTestOnBorrow());
         datasource.setTestOnReturn(dataSourceEnv.getTestOnReturn());
         datasource.setPoolPreparedStatements(dataSourceEnv.getPoolPreparedStatements());
         datasource.setMaxPoolPreparedStatementPerConnectionSize(dataSourceEnv.getMaxPoolPreparedStatementPerConnectionSize());
         try {
             datasource.setFilters(dataSourceEnv.getFilters());
         } catch (SQLException e) {
        	 log.error("druid DataSource 配置异常： {}", e);	
         }
         datasource.setConnectionProperties(dataSourceEnv.getConnectionProperties());
         log.info("druid DataSource parms ：{}",datasource);
         return datasource;
     }
	
}
