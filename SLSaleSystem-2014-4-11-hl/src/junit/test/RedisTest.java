package junit.test;

import static org.junit.Assert.*;

import java.util.ResourceBundle;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisTest {
	/*
	 * 	 
    private static JedisPool pool;//jedis池      
    //静态代码初始化池配置  
    static {   
        //加载redis配置文件  
        ResourceBundle bundle = ResourceBundle.getBundle("redis");    
        if (bundle == null) {    
            throw new IllegalArgumentException("[redis.properties] is not found!");    
        }   
        //创建jedis池配置实例  
        JedisPoolConfig config = new JedisPoolConfig();   
        //设置池配置项值  
        config.setMaxActive(Integer.valueOf(bundle.getString("redis.pool.maxActive")));    
        config.setMaxIdle(Integer.valueOf(bundle.getString("redis.pool.maxIdle")));    
        config.setMaxWait(Long.valueOf(bundle.getString("redis.pool.maxWait")));    
        config.setTestOnBorrow(Boolean.valueOf(bundle.getString("redis.pool.testOnBorrow")));    
        config.setTestOnReturn(Boolean.valueOf(bundle.getString("redis.pool.testOnReturn")));    
        //根据配置实例化jedis池  
        pool = new JedisPool(config, bundle.getString("redis.ip"),   
                Integer.valueOf(bundle.getString("redis.port")));  
    }  
     */
    
    public static void test1(){  
    	JedisPool pool=new JedisPool("127.0.0.1");
        //从jedis池中获取一个jedis实例  
        Jedis jedis = pool.getResource();  
                  
        //获取jedis实例后可以对redis服务进行一系列的操作  
        jedis.set("username", "xmong");  
        System.out.println(jedis.get("username"));  
        jedis.del("username");  
        System.out.println(jedis.exists("username"));  
          
        //释放对象池，即获取jedis实例使用后要将对象还回去  
        pool.returnResource(jedis);       
    }  
    
	@Test
	public void test() {
	
		test1();
	}

}
