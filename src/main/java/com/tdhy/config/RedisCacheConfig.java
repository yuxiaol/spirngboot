package com.tdhy.config;

import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import com.tdhy.lock.RedisLock;
import com.tdhy.lock.TdhyRedisLock;

/** 
 * 二级缓存配置
 * @see RedisCacheConfig 
 * @author yuxiaolong@tiandihengye.com
 * @version 0.0.1  
 */
@Configuration
@EnableCaching
public class RedisCacheConfig {
	 /**
     * 缓存对象集合中，缓存是以 key-value 形式保存的。当不指定缓存的 key 时，SpringBoot 会使用 SimpleKeyGenerator 生成 key。
     * @return
     */
    @Bean
    public KeyGenerator wiselyKeyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };

    }
    
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    
    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager(this.redisTemplate);
        // 开启使用缓存名称最为key前缀
       // redisCacheManager.setUsePrefix(true);
        //这里可以设置一个默认的过期时间 单位是秒
        redisCacheManager.setDefaultExpiration(3600);
        return redisCacheManager;
    }
    
    /**
     * 分布式锁
     * @see reidsLock 
     * @thorows 
     * @return RedisLock
     */
	@Bean 
    public RedisLock reidsLock() {
    	return  new TdhyRedisLock(redisTemplate);
    }
    
   
}
