package io.blacktoast.utils.redis;

import java.util.List;
import java.util.Map;

/**
 * @version 2.1.0
 * @author blacktoast
 *
 */
public interface RedisService {

	/**
	 * @param key
	 * @return
	 */
	String getByKey(String key);

	/**
	 * @param key
	 * @param value
	 * @param setOnlyIfNotExists
	 * @param ttlSeconds
	 */
	void setByKey(String key, String value, Boolean setOnlyIfNotExists, Long ttlSeconds);

	/**
	 * @param pattern
	 * @return
	 */
	Map<String, String> getByKeyPattern(String pattern);

	/**
	 * @param pattern
	 * @return
	 */
	List<String> getKeysByPattern(String pattern);

	/**
	 * @param key
	 * @return
	 */
	Long getTtl(String key);

	/**
	 * @param key
	 * @param increment
	 */
	void incrementByKey(String key, Long increment);

	/**
	 * @param quequeName
	 * @param value
	 * @return
	 */
	void pushToQueue(String queueName, String value);

	/**
	 * @param quequeName
	 * @return
	 */
	String retrieveFromQueue(String queueName);

	/**
	 * @param key
	 */
	void deleteByKey(String key);
}
