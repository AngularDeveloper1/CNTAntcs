package com.es.wfx.analytics.caching;

import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching(proxyTargetClass=true)
public class CacheConfig extends CachingConfigurerSupport {
	
	@Value("${cache.server}")
	private String CACHE_SERVER;
	
	@Value("${cache.server.port}")
	private int CACHE_SERVER_PORT;
	
	@Value("${cache.expiration}")
	private long CACHE_EXPIRATION;
	
	 @Bean
	  public JedisConnectionFactory redisConnectionFactory() {
	    JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
	    redisConnectionFactory.setHostName(CACHE_SERVER);
	    redisConnectionFactory.setPort(CACHE_SERVER_PORT);
	    return redisConnectionFactory;
	  }

	  @Bean
	  public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf) {
	    RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
	    redisTemplate.setConnectionFactory(cf);
	    return redisTemplate;
	  }
	 
	  @Bean
	  public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
	    RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
	    cacheManager.setDefaultExpiration(CACHE_EXPIRATION);
	    return cacheManager;
	  }
	  
	  @Bean
	  public KeyGenerator keyGenerator() {
	    return new KeyGenerator() {
	      @Override
	      public Object generate(Object o, Method method, Object... objects) {
	        // This will generate a unique key of the class name, the method name,
	        // and all method parameters appended.
	        StringBuilder sb = new StringBuilder();
	        sb.append(o.getClass().getName());
	        sb.append(method.getName());
	        for (Object obj : objects) {
	          sb.append(obj.toString());
	        }
	        return sb.toString();
	      }
	    };
	  }
}
