package io.blacktoast.utils.redis.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class ConfigManager {

	@Autowired
	private RedisUserConfig userConfig;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {

		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(userConfig.getMaxTotalPool());
		poolConfig.setMaxIdle(userConfig.getMaxIdlePool());
		poolConfig.setTestOnBorrow(true);
		poolConfig.setTestOnReturn(true);

		JedisConnectionFactory connectionFactory = new JedisConnectionFactory(poolConfig);
		connectionFactory.setUsePool(true);
		connectionFactory.setHostName(userConfig.getUrl());
		connectionFactory.setPort(userConfig.getPort());

		return connectionFactory;
	}

	@Bean
	public StringRedisTemplate getRedisTemplate() {

		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(redisConnectionFactory());
		stringRedisTemplate.setEnableTransactionSupport(true);

		return stringRedisTemplate;
	}

	public RedisUserConfig getUserConfig() {
		return this.userConfig;
	}

}
