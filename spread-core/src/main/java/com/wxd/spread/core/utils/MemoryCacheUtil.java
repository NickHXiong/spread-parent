package com.wxd.spread.core.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统模板消息内存缓存工具类
 * 
 * @author wangxiaodan
 *
 */
public class MemoryCacheUtil {
	private static final Map<String, MemoryCache<Object>> cache = new HashMap<String, MemoryCache<Object>>();

	public static boolean put(String schema, String key, Object value) {
		MemoryCache<Object> memoryCache = null;
		if (getSchemaRefreshTime(schema) != -1) {
			memoryCache = cache.get(key);
		} else {
			return false;
		}
		memoryCache.put(key, value, null, true);
		return true;
	}

	public static Object get(String schema, String key) {
		if (getSchemaRefreshTime(schema) != -1) {
			MemoryCache<Object> memoryCache = cache.get(schema);
			return memoryCache.get(key);
		}
		return null;
	}

	/**
	 * 是否存在相应的缓存对象并获取创建时间
	 * 
	 * @param schema
	 * @return -1:不存在 其他：创建时间
	 */
	public static long getSchemaRefreshTime(String schema) {
		if (cache.containsKey(schema)) {
			MemoryCache<Object> memoryCache = cache.get(schema);
			if (memoryCache.getExpireTime() > System.currentTimeMillis()) {
				return memoryCache.getRefreshTime();
			} else {
				cache.remove(schema); // 过期删除
			}
		}
		return -1;
	}

	/**
	 * 初始化内存存储，并设置过期日期
	 * 
	 * @param schema
	 * @param expireMillis
	 *            存活的毫秒数
	 * @return
	 */
	public static MemoryCache<Object> initSchema(String schema, Long expireMillis) {
		MemoryCache<Object> memoryCache = new MemoryCache<Object>(expireMillis);
		cache.put(schema, memoryCache);
		return memoryCache;
	}

	/**
	 * 真正内存对象
	 * 
	 * @author wangxiaodan
	 *
	 */
	public static class MemoryCache<T> {
		private final Map<String, T> map = new HashMap<String, T>();
		/**
		 * 键存储过期时间
		 */
		private final Map<String, Long> expireMap = new HashMap<String, Long>();
		private long refreshTime;
		private long expireTime = -1; // MemoryCache的过期时间

		/**
		 * 初始化
		 * 
		 * @param expireLong
		 *            过期毫秒数
		 */
		public MemoryCache(Long expireMillis) {
			refreshTime = System.currentTimeMillis();
			if (expireMillis != null) {
				if (expireMillis > 0) {
					expireTime = refreshTime + expireMillis;
				} else {
					expireTime = refreshTime;
				}
			}
		}

		public long getRefreshTime() {
			return refreshTime;
		}

		protected long getExpireTime() {
			return expireTime;
		}

		public T get(String key) {
			boolean containsKey = map.containsKey(key);
			if (containsKey) {
				if (expireMap.containsKey(key) && System.currentTimeMillis() > expireMap.get(key)) {
					map.remove(key);
					expireMap.remove(key);
					return null;
				}
				return map.get(key);
			}
			return null;
		}

		/**
		 * 是否存在指定的键
		 * 
		 * @param key
		 * @return
		 */
		public boolean contains(String key) {
			boolean containsKey = map.containsKey(key);
			if (containsKey) {
				if (expireMap.containsKey(key) && System.currentTimeMillis() > expireMap.get(key)) {
					map.remove(key);
					expireMap.remove(key);
					return false;
				}
				return true;
			}
			return false;

		}

		/**
		 * 将键值对放入内存中并指定失效时间
		 * 
		 * @param key
		 * @param value
		 * @param expire
		 *            毫秒
		 * @param override
		 */
		public void put(String key, T value, Long expire, boolean override) {
			if (!map.containsKey(key) || override) {
				map.put(key, value);
				if (expire != null) {
					expireMap.put(key, System.currentTimeMillis() + expire);
				} else {
					expireMap.remove(key);
				}
			}
		}
	}
}
