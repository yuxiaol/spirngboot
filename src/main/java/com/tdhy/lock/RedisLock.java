package com.tdhy.lock;
/**
 *  RedisLock redis 分布式锁
 * @author yuxiaolong@tiandihengye.com
 * @version 0.0.1
 */
public interface RedisLock {
	/**
	 * 默认key过期时间
	 */
	public static final long TIMEOUT_MILLIS = 30000;
    /**
     * 默认尝试获取锁的次数
     */
    public static final int RETRY_TIMES = Integer.MAX_VALUE;
    /**
     * 默认当前线程等待时间
     */
    public static final long SLEEP_MILLIS = 500;
    /**
     *  lock 使用默认参数获取锁
     * @param key key值
     * @return boolean true 成功 false 失败
     */
    public boolean lock(String key);
    /**
     *  lock 设置获取锁的次数获取锁
     * @param key key值
     * @param retryTimes 尝试获取锁的次数
     * @return boolean boolean true 成功 false 失败
     */
    public boolean lock(String key, int retryTimes);
    /**
     *  lock 设置次数、一次线程等待时间 获取锁
     * @param key key值
     * @param retryTimes 尝试获取锁的次数
     * @param sleepMillis 线程等待时间 获取锁的频率
     * @return boolean boolean true 成功 false 失败
     */
    public boolean lock(String key, int retryTimes, long sleepMillis);
    /** 
     *  lock 设置过期时间的 获取锁
     * @param key key值
     * @param expire key过期时间
     * @return boolean boolean true 成功 false 失败
     */
    public boolean lock(String key, long expire);
    /** 
     *  lock 
     * @param key key值
     * @param expire key过期时间
     * @param retryTimes 尝试获取锁的次数
     * @return boolean
     */
    public boolean lock(String key, long expire, int retryTimes);
    /** 
     *  lock 
     * @param key key值
     * @param expire key过期时间
     * @param retryTimes 尝试获取锁的次数
     * @param sleepMillis 线程等待时间 获取锁的频率
     * @return boolean
     */
    public boolean lock(String key, long expire, int retryTimes, long sleepMillis);
    
    /** 
     *  unLock  释放锁
     * @param key key值
     * @return boolean
     */
    public boolean unLock(String key);
}
