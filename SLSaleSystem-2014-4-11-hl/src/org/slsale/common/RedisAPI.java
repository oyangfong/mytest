package org.slsale.common;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * RedisAPI
 * @author bdqn_hl
 * @date 2014-3-15
 * 
 * Jedis操作步骤如下：
 * 1.获取Jedis实例需要从JedisPool中获取；
 * 2.用完Jedis实例需要返还给JedisPool；
 * 3.如果Jedis在使用过程中出错，则也需要还给JedisPool；
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
	 * 
	 * a、获取jedis实例时，实际上可能有两类错误。
	 * 一类是pool.getReource()，得不到可用的jedis实例；
	 * 另一类是jedis.set/get时出错也会抛出异常；
	 * 为了实现区分，所以根据instance是否为null来实现，如果为空就证明instance根本就没初始化，也就不用return给pool；
	 * 如果instance不为null，则证明是需要返还给pool的；
	 * b、在instance出错时，必须调用returnBrokenResource返还给pool，否则下次通过getResource得到的instance的缓冲区可能还存在数据，出现问题！
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
	 * 
	 * 单一Jedis实例是非线程安全的。而JedisPool是一个线程安全的网络连接池。
	 * 可以用JedisPool创建一些可靠Jedis实例，可以从池中拿到Jedis的实例。
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
