package com.tdhy.shiro.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RedisCache <K, V> implements Cache<K, V>{

	private RedisManager cache;
	
	private String keyPrefix ;
	
	
	
	public RedisCache(RedisManager cache) {
		super();
		if(null == cache) {
			log.error("Cache argument cannot be null. ");
			throw new IllegalArgumentException("Cache argument cannot be null.");
		}
		this.cache = cache;
	}
	
	public RedisCache(RedisManager cache,String keyPrefix) {
		this(cache);
		this.keyPrefix = keyPrefix;
	}

	@Override
	public void clear() throws CacheException {
		log.debug("从redis中删除所有元素");
        try {
            this.cache.flushDB();
        } catch (Throwable var2) {
            throw new CacheException(var2);
        }
		
	}

	@Override
	public V get(K key) throws CacheException {
		log.debug("根据key从Redis中获取对象 key {}" ,key);
		 try {
	            if (key == null) {
	                return null;
	            } else {
	                @SuppressWarnings("unchecked")
					V value = (V) this.cache.get(this.getByteKey(key));
	                return value;
	            }
	        } catch (Throwable var4) {
	            throw new CacheException(var4);
	        }
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<K> keys() {
		try {
            Set<String> keys = this.cache.keys(this.keyPrefix + "*");
            if (CollectionUtils.isEmpty(keys)) {
                return Collections.emptySet();
            } else {
                Set<K> newKeys = new HashSet<K>();
                Iterator<String> i$ = keys.iterator();

                while(i$.hasNext()) {
                	String key = (String)i$.next();
                    newKeys.add((K)key);
                }

                return newKeys;
            }
        } catch (Throwable var5) {
            throw new CacheException(var5);
        }
	}

	@Override
	public V put(K key, V value) throws CacheException {
		   log.debug("根据key从存储 key [" + key + "]");
	        try {
	            this.cache.set(this.getByteKey(key), value);
	            return value;
	        } catch (Throwable var4) {
	            throw new CacheException(var4);
	        }
	}

	@Override
	public V remove(K key) throws CacheException {
		log.debug("从redis中删除 key [" + key + "]");
	        try {
	            V previous = this.get(key);
	            this.cache.del(this.getByteKey(key));
	            return previous;
	        } catch (Throwable var3) {
	            throw new CacheException(var3);
	        }
	}

	@Override
	public int size() {
		try {
            Long longSize = this.cache.dbSize();
            return longSize.intValue();
        } catch (Throwable var2) {
            throw new CacheException(var2);
        }	
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<V> values() {
		 try {
	            Set<String> keys = this.cache.keys(this.keyPrefix + "*");
	            if (!CollectionUtils.isEmpty(keys)) {
					List<V> values = new ArrayList<V>(keys.size());
	                Iterator<String> i$ = keys.iterator();
	                while(i$.hasNext()) {
	                	String key = (String)i$.next();
	                	V value = this.get((K)key);
	                	if (value != null) {
	                        values.add(value);
	                    }
	                }

	                return Collections.unmodifiableList(values);
	            } else {
	                return Collections.emptyList();
	            }
	        } catch (Throwable var6) {
	            throw new CacheException(var6);
	        }
	}
	
	
	  private String getByteKey(K key) {
	            String preKey = this.keyPrefix + key.toString();
	            return preKey;
	    }

}
