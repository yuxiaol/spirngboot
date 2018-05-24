package com.tdhy.shiro.cache;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RedisSessionDAO  extends AbstractSessionDAO {
	
	private RedisManager redisManager;
    private String keyPrefix = "shiro_redis_session:";
	

	public RedisSessionDAO(RedisManager redisManager) {
		super();
		
		if(null == redisManager) {
			log.error("RedisSessionDAO argument cannot be null. ");
			throw new IllegalArgumentException("RedisSessionDAO argument cannot be null.");
		}
		this.redisManager = redisManager;
	}

	
	public RedisSessionDAO(RedisManager redisManager, String keyPrefix) {
		this(redisManager);
		this.keyPrefix = keyPrefix;
	}

	
	@Override
	public void delete(Session session) {
		if (session != null && session.getId() != null) {
            this.redisManager.del(session.getId().toString());
        } else {
            log.error("session or session id is null");
        }
	}

	@Override
	public Collection<Session> getActiveSessions() {
		 Set<Session> sessions = new HashSet<Session>();
	        Set<String> keys = this.redisManager.keys(this.keyPrefix + "*");
	        if (keys != null && keys.size() > 0) {
	            Iterator<String> i$ = keys.iterator();
	            while(i$.hasNext()) {
	                String key = i$.next();
	                Session s = (Session)this.redisManager.get(key);
	                sessions.add(s);
	            }
	        }
	        return sessions;
	}

	@Override
	public void update(Session session) throws UnknownSessionException {
		 this.saveSession(session);
	}

	 private void saveSession(Session session) throws UnknownSessionException {
	        if (session != null && session.getId() != null) {
//	            byte[] key = this.getByteKey(session.getId());
//	            byte[] value = SerializeUtils.serialize(session);
	        	session.setTimeout((long)(this.redisManager.getExpire()*20*30));
	            this.redisManager.set( this.getByteKey(session.getId()), session);
	        } else {
	            log.error("session or session id is null");
	        }
	    }
	
	@Override
	protected Serializable doCreate(Session session) {
		 Serializable sessionId = this.generateSessionId(session);
	        this.assignSessionId(session, sessionId);
	        this.saveSession(session);
	        return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable session) {
		if (session == null) {
            log.error("sessionId id is null");
            return null;
        } else {
            Session s = (Session)this.redisManager.get(this.getByteKey(session));
            return s;
        }
	}
	
	
	  private String getByteKey(Serializable sessionId) {
	        String preKey = this.keyPrefix + sessionId.toString();
	        return preKey;
	    }
	
}
