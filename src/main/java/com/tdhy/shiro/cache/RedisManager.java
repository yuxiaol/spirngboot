package com.tdhy.shiro.cache;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings({"rawtypes","unchecked"})
public class RedisManager {
 
	private RedisTemplate<String,Object> redisTemplate;
	
	private Integer expire = 3600;
	
	
	
	
	public RedisManager(RedisTemplate redisTemplate, Integer expire) {
		this(redisTemplate);
		this.expire = expire;
	}

	public RedisManager(RedisTemplate redisTemplate) {
		 	if(null == redisTemplate) {
		 		throw new IllegalArgumentException("RedisManager argument cannot be null.");
		 	}
			this.redisTemplate = redisTemplate;
		}

	public Object get(String key) {
	        try {
	        	Object value= redisTemplate.opsForValue().get(key);
	        	if(this.expire > 0) {
	        		redisTemplate.expire(key, expire, TimeUnit.SECONDS);
	        	}
	        	return value;
	        } catch(Exception e) {
	        	log.error("RedisManager is  get error :{ }" , e);
	        }
	        return null;
	}
	 
	 
	 
	 public Object set(String key, Object value) {
		

	        try {
	        	 redisTemplate.opsForValue().set(key, value);
	            if (this.expire > 0) {
	            	redisTemplate.expire(key, this.expire, TimeUnit.SECONDS);
	            }
	        } catch(Exception e) {
	        	log.error("RedisManager is  set error :{ }" , e);
	        }
	        return null;
	    }
	 
	 
	 public void del(String key) {
	        try {
	        	 redisTemplate.delete(key);
	        } catch(Exception e) {
	        	log.error("RedisManager is  del error :{ }" , e);
	        }

	   }
	 
	 public void flushDB() {
		 redisTemplate.execute(new RedisCallback<Object>() {
	            public String doInRedis(RedisConnection connection) throws DataAccessException {
	                connection.flushDb();
	                return "ok";
	            }
	        });
	  }
	 
	 
	 public Long dbSize() {
		 return redisTemplate.execute(new RedisCallback<Long>() {
	            public Long doInRedis(RedisConnection connection) throws DataAccessException {
	                return connection.dbSize();
	            }
	        });
	   }
	 
	 
	 public Set<String> keys(String pattern) {
		 
		 //return null;
		 	return redisTemplate.keys(pattern);
	    }

	public Integer getExpire() {
		return expire;
	}
	 
}
