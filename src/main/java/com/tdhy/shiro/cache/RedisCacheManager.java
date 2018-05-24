package com.tdhy.shiro.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RedisCacheManager implements CacheManager{
	
	private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();
	 
	private String keyPrefix = "shiro_redis_cache:";

	private RedisManager redisManager;
	

	public RedisCacheManager(String keyPrefix, RedisManager redisManager) {
		this(redisManager);
		this.keyPrefix = keyPrefix;
	}
	public RedisCacheManager(RedisManager redisManager) {
		super();
		if(null == redisManager) {
			log.error("RedisCacheManager argument cannot be null. ");
			throw new IllegalArgumentException("RedisCacheManager argument cannot be null.");
		}
		this.redisManager = redisManager;
	}


	
	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		 log.debug("获取名称为: " + name + " 的RedisCache实例");
			Cache c = (Cache)this.caches.get(name);
	        if (c == null) {
	            c = new RedisCache(this.redisManager, this.keyPrefix);
	            this.caches.put(name, c);
	        }
	        return (Cache<K, V>)c;
	}

}