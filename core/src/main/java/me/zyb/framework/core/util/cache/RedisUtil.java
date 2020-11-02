package me.zyb.framework.core.util.cache;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.dict.SuppressWarningsKey;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangyingbin
 */
@Slf4j
public class RedisUtil {
	private static StringRedisTemplate stringRedisTemplate;

	public RedisUtil(StringRedisTemplate stringRedisTemplate){
		RedisUtil.stringRedisTemplate = stringRedisTemplate;
	}

	/**
	 * 添加 key:string 缓存
	 * @param key       键
	 * @param value     值
	 * @param timeout   超时时间（秒）
	 */
	public static void cacheValue(String key, String value, long timeout){
		if(timeout > 0) {
			stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
		} else {
			stringRedisTemplate.opsForValue().set(key, value);
		}
	}

	/**
	 * 添加 key:string 缓存
	 * @param key       键
	 * @param value     值
	 */
	public static void cacheValue(String key, String value){
		stringRedisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 查询缓存 key 是否存在
	 * @param key   键
	 * @return Boolean
	 */
	public static Boolean contains(String key) {
		return stringRedisTemplate.hasKey(key);
	}

	/**
	 * 根据 key 获取 string 缓存数据
	 * @param key   键
	 * @return String
	 */
	public static String getValue(String key){
		return stringRedisTemplate.opsForValue().get(key);
	}

	/**
	 * 添加 key:set 缓存
	 * @param key       键
	 * @param value     值
	 * @param timeout   超时时间（秒）
	 */
	public static synchronized void cacheSet(String key, String value, long timeout){
		stringRedisTemplate.opsForSet().add(key, value);
		if(timeout > 0) {
			stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
		}
	}

	/**
	 * 添加 key:set 缓存
	 * @param key       键
	 * @param value     值
	 */
	public static void cacheSet(String key, String value){
		stringRedisTemplate.opsForSet().add(key, value);
	}

	/**
	 * 添加 key:set 缓存
	 * @param key       键
	 * @param value     值
	 */
	public static synchronized void cacheSet(String key, Set<String> value, long timeout){
		stringRedisTemplate.opsForSet().add(key, value.toArray(new String[]{}));
		if(timeout > 0) {
			stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
		}
	}

	/**
	 * 添加 key:set 缓存
	 * @param key       键
	 * @param value     值
	 */
	public static void cacheSet(String key, Set<String> value){
		stringRedisTemplate.opsForSet().add(key, value.toArray(new String[0]));
	}

	/**
	 * 根据 key 获取 set 缓存数据
	 * @param key   键
	 * @return Set<String>
	 */
	public static Set<String> getSet(String key){
		return stringRedisTemplate.opsForSet().members(key);
	}

	/**
	 * 根据 key 查看 set 中是否存在指定的 value
	 * @param key   健
	 * @param value 值
	 * @return Boolean
	 */
	public static Boolean containsValue4Set(String key, String value){
		return stringRedisTemplate.opsForSet().isMember(key, value);
	}

	/**
	 * 根据 key 获取 set 缓存数据总条数
	 * @param key   键
	 * @return Long
	 */
	public static Long getSize4Set(String key){
		return stringRedisTemplate.opsForSet().size(key);
	}

	/**
	 * 添加 key:list 缓存
	 * @param key       键
	 * @param value     值
	 * @param timeout   超时时间（秒）
	 */
	public static synchronized void cacheList(String key, String value, long timeout){
		stringRedisTemplate.opsForList().rightPush(key, value);
		if(timeout > 0) {
			stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
		}
	}

	/**
	 * 添加 key:list 缓存
	 * @param key       键
	 * @param value     值
	 */
	public static void cacheList(String key, String value){
		stringRedisTemplate.opsForList().rightPush(key, value);
	}

	/**
	 * 添加 key:list 缓存
	 * @param key       键
	 * @param value     值
	 */
	public static synchronized void cacheList(String key, List<String> value, long timeout){
		stringRedisTemplate.opsForList().rightPushAll(key, value);
		if(timeout > 0) {
			stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
		}
	}

	/**
	 * 添加 key:list 缓存
	 * @param key       键
	 * @param value     值
	 */
	public static void cacheList(String key, List<String> value){
		stringRedisTemplate.opsForList().rightPushAll(key, value);
	}

	/**
	 * 根据 key 获取 list 缓存数据
	 * @param key   键
	 * @param start 开始
	 * @param end   结束
	 * @return List<String>
	 */
	public static List<String> getList(String key, long start, long end){
		return stringRedisTemplate.opsForList().range(key, start, end);
	}

	/**
	 * 根据 key 获取 list 缓存数据总条数
	 * @param key   键
	 * @return Long
	 */
	public static Long getSize4List(String key){
		return stringRedisTemplate.opsForList().size(key);
	}

	/**
	 * 根据 key 移除（获取） list 一条缓存数据（头部）
	 * @param key   键
	 */
	public static String removeOneFromList(String key){
		return stringRedisTemplate.opsForList().leftPop(key);
	}

	/**
	 * 添加 key:hash 缓存
	 * @param key       键
	 * @param hashKey   hash键
	 * @param value     值
	 * @param timeout   超时时间（秒）
	 */
	public static synchronized void cacheHash(String key, String hashKey, String value, long timeout){
		stringRedisTemplate.opsForHash().put(key, hashKey, value);
		if(timeout > 0) {
			stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
		}
	}

	/**
	 * 添加 key:hash 缓存
	 * @param key       键
	 * @param hashKey   hash键
	 * @param value     值
	 */
	public static synchronized void cacheHash(String key, String hashKey, String value){
		stringRedisTemplate.opsForHash().put(key, hashKey, value);
	}

	/**
	 * 添加 key:hash 缓存
	 * @param key       键
	 * @param map       hashKey/value
	 * @param timeout   超时时间（秒）
	 */
	public static synchronized void cacheHash(String key, Map<String, String> map, long timeout){
		stringRedisTemplate.opsForHash().putAll(key, map);
		if(timeout > 0) {
			stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
		}
	}

	/**
	 * 添加 key:hash 缓存
	 * @param key       键
	 * @param map       hashKey/value
	 */
	public static synchronized void cacheHash(String key, Map<String, String> map){
		stringRedisTemplate.opsForHash().putAll(key, map);
	}

	/**
	 * 根据 key 和 hashKey 获取缓存数据
	 * @param key       键
	 * @param hashKey   hash键
	 * @return List<String>
	 */
	public static String getHash(String key, String hashKey){
		return JSON.toJSONString(stringRedisTemplate.opsForHash().get(key, hashKey));
	}

	/**
	 * 根据 key 获取 hash 所有数据
	 * @param key   键
	 * @return Map<Object, Object>
	 */
	public static Map<Object, Object> getHashMap(String key){
		return stringRedisTemplate.opsForHash().entries(key);
	}

	/**
	 * 根据 key 获取 hash 缓存数据总条数
	 * @param key   键
	 * @return Long
	 */
	public static Long getSize4Hash(String key){
		return stringRedisTemplate.opsForHash().size(key);
	}

	/**
	 * 根据 key 和 hashKey 移除 hash 中一条缓存数据
	 * @param key       键
	 * @param hashKeys   hash键
	 * @return Long
	 */
	public static Long removeFromHash(String key, String... hashKeys){
		return stringRedisTemplate.opsForHash().delete(key, hashKeys);
	}

	/**
	 * 根据 key 删除缓存数据
	 * @param key   键
	 */
	@SuppressWarnings(SuppressWarningsKey.UNCHECKED)
	public static void remove(String... key){
		if(key != null && key.length > 0) {
			if(key.length == 1){
				stringRedisTemplate.delete(key[0]);
			}else {
				stringRedisTemplate.delete(CollectionUtils.arrayToList(key));
			}
		}
	}

	/**
	 * 根据 key 获取过期时间
	 * @param key   键
	 * @return Long
	 */
	public static Long getTimeout(String key, TimeUnit timeUnit){
		return stringRedisTemplate.getExpire(key, timeUnit);
	}

	/**
	 * 计数器
	 * @param key   键
	 * @param delta 增量
	 * @return Long
	 */
	public static Long increment(String key, long delta){
		return stringRedisTemplate.opsForValue().increment(key, delta);
	}

	/**
	 * Hash计数器
	 * @param key       键
	 * @param hashKey   hash键
	 * @param delta     增量
	 * @return Long
	 */
	public static Long incrementHash(String key, String hashKey, long delta){
		return stringRedisTemplate.opsForHash().increment(key, hashKey, delta);
	}
}
