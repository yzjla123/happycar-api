package com.happycar.api.utils;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
protected static Logger logger = Logger.getLogger(RedisUtil.class);
	
	//Redis服务器IP
    private static String ADDR_ARRAY = "127.0.0.1";
    private static String PASSWORD = "";
    
    //Redis的端口号
    private static int PORT = 6379;
    
    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_ACTIVE = 1000;
    
    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 20;
    
    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = 10000;

    //超时时间
    private static int TIMEOUT = 30000;
    
    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;
    
    private static JedisPool jedisPool = null;
    
    /**
     * redis过期时间,以秒为单位
     */
    public final static int EXRP_HOUR = 60*60;			//一小时
    public final static int EXRP_DAY = 60*60*24;		//一天
    public final static int EXRP_MONTH = 60*60*24*30;	//一个月
    
    private static void readConfig(){
    	try {
			Properties p = ResourcesUtil.readResources("redis.properties");
			ADDR_ARRAY = p.getProperty("spring.redis.host");
			PASSWORD = p.getProperty("spring.redis.password");
			PORT = Integer.parseInt(p.getProperty("spring.redis.port"));
			MAX_ACTIVE = Integer.parseInt(p.getProperty("spring.redis.pool.max-active"));
			MAX_IDLE = Integer.parseInt(p.getProperty("spring.redis.pool.max-idle"));
			MAX_WAIT = Integer.parseInt(p.getProperty("spring.redis.pool.max-wait"));
			TIMEOUT = Integer.parseInt(p.getProperty("spring.redis.timeout"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * 初始化Redis连接池
     */
    private static void initialPool(){
        try {
            JedisPoolConfig config = new JedisPoolConfig();
//            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
//            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config, ADDR_ARRAY.split(",")[0], PORT, TIMEOUT);
        } catch (Exception e) {
        	logger.error("First create JedisPool error : "+e);
            try{
	            //如果第一个IP异常，则访问第二个IP
	            JedisPoolConfig config = new JedisPoolConfig();
//	            config.setMaxTotal(MAX_ACTIVE);
	            config.setMaxIdle(MAX_IDLE);
//	            config.setMaxWaitMillis(MAX_WAIT);
	            config.setTestOnBorrow(TEST_ON_BORROW);
	            jedisPool = new JedisPool(config, ADDR_ARRAY.split(",")[1], PORT, TIMEOUT);
            }catch(Exception e2){
            	logger.error("Second create JedisPool error : "+e2);
            }
        }
    }
    
    
    /**
     * 在多线程环境同步初始化
     */
    private static synchronized void poolInit() {
    	if (jedisPool == null) {  
    		readConfig();
            initialPool();
        }
    }

    
    /**
     * 同步获取Jedis实例
     * @return Jedis
     */
    public synchronized static Jedis getJedis() {  
        if (jedisPool == null) {  
        	poolInit();
        }
        Jedis jedis = null;
        try {  
            if (jedisPool != null) {  
            	jedis = jedisPool.getResource(); 
            }
        } catch (Exception e) {  
        	e.printStackTrace();
        	logger.error("Get jedis error : "+e);
        }finally{
        	returnResource(jedis);
        }
        return jedis;
    }  
    
    
    /**
     * 释放jedis资源
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null && jedisPool !=null) {
        	jedisPool.returnResource(jedis);
        }
    }
    
	
	/**
	 * 设置 String
	 * @param key
	 * @param value
	 */
	public static void setString(String key ,String value){
    	try {
    		value = org.apache.commons.lang3.StringUtils.isEmpty(value) ? "" : value;
    		getJedis().set(key,value);
		} catch (Exception e) {
			logger.error("Set key error : "+e);
		}
	}
	
	/**
	 * 设置 过期时间
	 * @param key
	 * @param seconds 以秒为单位
	 * @param value
	 */
	public static void setString(String key ,String value,int seconds){
    	try {
    		value = StringUtils.isEmpty(value) ? "" : value;
    		getJedis().setex(key, seconds, value);
		} catch (Exception e) {
			logger.error("Set keyex error : "+e);
		}
	}
	
	/**
	 * 获取String值
	 * @param key
	 * @return value
	 */
	public static String getString(String key){
		if(getJedis() == null || !getJedis().exists(key)){
			return null;
		}
		return getJedis().get(key);
	}
	
	public static String cache(byte[] key, byte[] value) {
		Jedis jedis = null;
		String result = null;
		try {
			jedis = getJedis();
			if(value!=null)
				result = jedis.set(key, value);
		} catch (Exception e) {
			// 释放redis对象
			returnResource(jedis);
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 获取数据
	 * 
	 * @param key
	 * @return
	 */
	public static byte[] get(byte[] key) {
		byte[] value = null;

		Jedis jedis = null;
		try {
			jedis = getJedis();
			value = jedis.get(key);
		} catch (Exception e) {
			// 释放redis对象
			returnResource(jedis);
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(jedis);
		}

		return value;
	}
}
