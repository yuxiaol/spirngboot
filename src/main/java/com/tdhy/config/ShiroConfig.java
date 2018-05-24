package com.tdhy.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import com.tdhy.shiro.MyShiroRealm;
import com.tdhy.shiro.cache.RedisCacheManager;
import com.tdhy.shiro.cache.RedisManager;
import com.tdhy.shiro.cache.RedisSessionDAO;
import com.tdhy.util.PasswordHelper;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

@Configuration
@SuppressWarnings("rawtypes")
public class ShiroConfig {
	
	
	/**
	 * 将自己的验证方式加入容器
	 * @see myShiroRealm 
	 * @thorows 
	 * @return MyShiroRealm
	 */
    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }
    
    /** 
     * 
     * @see redisManager 
     * @thorows 
     * @return RedisManager
     */
 
	@Bean
   
    public RedisManager redisManager( @Autowired RedisTemplate redisTemplate) {
    	 RedisManager redisManager = new RedisManager(redisTemplate);
    	 return redisManager;
    }
    
    /**
     * shiroCacheManager  区分   RedisCache 配置中的RedisCacheManager  
     * 命名 shiroCacheManager
     * @see shiroCacheManager 
     * @thorows 
     * @return RedisCacheManager
     */
    @Bean(name = "shiroCacheManager")
    public RedisCacheManager shiroCacheManager( @Autowired RedisManager redisManager) {
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisManager);
        return redisCacheManager;
    }
    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     */
    
 
    
    @Bean
    
    public RedisSessionDAO redisSessionDAO(@Autowired RedisManager redisManager) {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO(redisManager);
        return redisSessionDAO;
    }
    
    @Bean
    public DefaultWebSessionManager sessionManager( @Autowired RedisSessionDAO redisSessionDAO) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO);
        return sessionManager;
    }
    
    //权限管理，配置主要是Realm的管理认证
    @Bean
    public DefaultWebSecurityManager securityManager( @Autowired DefaultWebSessionManager sessionManager, @Autowired RedisCacheManager shiroCacheManager) {
    	DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        securityManager.setCacheManager(shiroCacheManager);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }
    
    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
    
    /**
     * ShiroDialect，为了在thymeleaf里使用shiro的标签的bean
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
    
    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean( @Autowired  DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String,String> map = new HashMap<String, String>();
        //登出
        map.put("/logout","logout");
     
      //TODO  匹配自定义规则  anon 放行  ，  authc 验证
        
        map.put("/*.html", "anon"); 
        map.put("/*.css", "anon");
        map.put("/*.js", "anon");
        map.put("/*.jpg", "anon");
        map.put("/*.jpeg", "anon");
        map.put("/*.png", "anon");
        map.put("/api/**", "anon");
        
        map.put("/manage/**","authc");
        //登录
        shiroFilterFactoryBean.setLoginUrl("/manage/login");
        //首页
        shiroFilterFactoryBean.setSuccessUrl("/manage/index");
        //错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }
    
    //加入注解的使用，不加入这个注解不生效
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor( @Autowired  DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    
    
    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     *  所以我们需要修改下doGetAuthenticationInfo中的代码;
     * ）
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();

        hashedCredentialsMatcher.setHashAlgorithmName(PasswordHelper.TEMPLATE);//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(PasswordHelper.NUM);//散列的次数，比如散列两次，相当于 md5(md5(""));

        return hashedCredentialsMatcher;
    }
}
