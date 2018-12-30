package org.slsale.common;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * RedisAPI
 * @author bdqn_hl
 * @date 2014-3-15
 */
public class RedisAPI {
	
	public JedisPool jedisPool;
	
	public JedisPool getJedisPool() {
		return jedisPool;
	}
	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}
	/**
	 * set key and value to redis
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(String key,String value){
		try{
			Jedis jedis = jedisPool.getResource();
			jedis.set(key, value);
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 判断某个key是否存在
	 * @param key
	 * @return
	 */
	public boolean exist(String key){
		try{
			Jedis jedis = jedisPool.getResource();
			return jedis.exists(key);
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 返还到连接池
	 * @param pool
	 * @param redis
	 */
	public static void returnResource(JedisPool pool,Jedis redis){
		if(redis != null){
			pool.returnResource(redis);
		}
	}
	
	/**
	 * 获取数据
	 * @param key
	 * @return
	 */
	public String get(String key){
		String value = null;
		Jedis jedis = null;
		try{
			jedis = jedisPool.getResource();
			value = jedis.get(key);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//返还到连接池
			returnResource(jedisPool, jedis);
		}
		
		return value;
	}
}
