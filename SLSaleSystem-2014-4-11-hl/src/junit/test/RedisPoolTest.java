package junit.test;

import static org.junit.Assert.*;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class RedisPoolTest {

	@Test
	public void test() {
		String host="127.0.0.1"; //redis服务器主机  
		int port=6379;//端口号 
		
		Jedis jedis=new Jedis(host, port);//单一Jedis实例是非线程安全的
		
		System.out.println(jedis.get("username"));
		
		jedis.del("username");
		
		System.out.println(jedis.exists("username"));
		
	}

}
