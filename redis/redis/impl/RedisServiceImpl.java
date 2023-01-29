package io.blacktoast.utils.redis.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import io.blacktoast.utils.redis.RedisService;
import io.blacktoast.utils.redis.model.ConfigManager;

@Component
public class RedisServiceImpl implements RedisService {

	private static final Logger LOG = LoggerFactory.getLogger(RedisServiceImpl.class);

	@Autowired
	private ConfigManager configManager;

	private ValueOperations<String, String> getOpsForValue() {
		return configManager.getRedisTemplate().opsForValue();
	}

	private ListOperations<String, String> getOpsForList() {
		return configManager.getRedisTemplate().opsForList();
	}

	@Override
	public String getByKey(String key) {
		String value = null;
		try {
			value = getOpsForValue().get(key);

		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
		}
		return value;
	}

	@Override
	public void incrementByKey(String key, Long increment) {
		try {
			getOpsForValue().increment(key, increment);

		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public Map<String, String> getByKeyPattern(String pattern) {

		Map<String, String> keysAndValues = new HashMap<String, String>();
		try {
			Set<String> keys = configManager.getRedisTemplate().keys(pattern);

			for (String key : keys) {
				String value = getOpsForValue().get(key);
				keysAndValues.put(key, value);
			}

		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
		}

		return keysAndValues;
	}

	@Override
	public List<String> getKeysByPattern(String pattern) {

		List<String> keys = new ArrayList<String>();
		try {
			Set<String> keySet = configManager.getRedisTemplate().keys(pattern);

			if (keySet != null) {
				for (String key : keySet) {
					keys.add(key);
				}
			}

		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
		}

		return keys;
	}

	@Override
	public void setByKey(String key, String value, Boolean setOnlyIfExists, Long ttlSeconds) {
		try {
			BoundValueOperations<String, String> boundValueOperations = configManager.getRedisTemplate()
					.boundValueOps(key);
			boundValueOperations.set(value);
			boundValueOperations.expire(ttlSeconds, TimeUnit.SECONDS);

		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public Long getTtl(String key) {

		Long ttl = 0l;
		try {
			ttl = configManager.getRedisTemplate().getExpire(key, TimeUnit.SECONDS);

		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
		}
		return ttl;
	}

	@Override
	public void pushToQueue(String queueName, String value) {
		getOpsForList().leftPush(queueName, value);
	}

	@Override
	public String retrieveFromQueue(String queueName) {
		return getOpsForList().rightPop(queueName, configManager.getUserConfig().getPopTimeOut(),
				TimeUnit.MILLISECONDS);
	}

	@Override
	public void deleteByKey(String key) {
		getOpsForValue().getOperations().delete(key);
	}

}
