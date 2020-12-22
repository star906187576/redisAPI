package com.bigdata;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class redis {
    private JedisPool jedisPool;
    private JedisPoolConfig config;
    private Jedis resource;
    @BeforeTest
    public void redisConnectionPoll(){
        config = new JedisPoolConfig();
        config.setMaxIdle(10);
        config.setMaxWaitMillis(3000);
        config.setMaxTotal(50);
        config.setMinIdle(5);
        jedisPool = new JedisPool(config, "starl", 6379);
        resource = jedisPool.getResource();
    }
    @AfterTest
    public void closePool(){
        jedisPool.close();
        resource.close();
    }
    /**
     * 操作string类型数据
     */
    @Test
    public void stringOperate(){
//        添加、修改
        resource.set("hell00","aaa");
//        查询
        String hell00 = resource.get("hell00");
        System.out.println(hell00);
//        删除
//        追加数据
        resource.incr("hell0o");
        resource.incrBy("hell0o",3);
        resource.del("hell00");
    }
    /**
     * 操作hash类型数据
     */
    @Test
    public void hashOperate(){
//        添加、修改数据
        resource.hset("key1","field1","lma");
        resource.hset("key1","field2","lmb");
        resource.hset("key1","field3","lmc");
        resource.hset("key1","field4","lmd");
        resource.hset("key1","field5","lme");
//        获取数据
        System.out.println(resource.hget("key1", "field2"));
        System.out.println(resource.hgetAll("key1"));
        Map<String, String> key1 = resource.hgetAll("key1");
        for (String s : key1.keySet()) {
            System.out.println(s+"="+resource.hget("key1",s));
        }
//        删除数据
//        resource.del("key1");
//        Set<String> key11 = resource.keys("key1");
//        for (String s : key11) {
//            System.out.println(s);
//        }
    }
    /**
     * 操作list类型数据
     */
    @Test
    public void listOperate(){
//        从左添加元素
        resource.lpush("list1","3","4");
//        从右删除元素
        resource.rpop("list2");
//        获取数据
        List<String> list1 = resource.lrange("list1", 0, -1);
        for (String s : list1) {
            System.out.println(s);
        }
        System.out.println(resource.lindex("list2", 0));
    }
    /**
     * 操作set类型数据
     */
    @Test
    public void setOperate(){
//        添加数据
        resource.sadd("set3","10");
//        查询数据
        Set<String> set3 = resource.smembers("set3");
        for (String s : set3) {
            System.out.println(s);
        }
//        移除数据
        resource.srem("set3","10");
    }
    /**
     * 操作keys
     */
    @Test
    public void keysOperate(){
        Set<String> keys = resource.keys("*");
        System.out.println(keys);
        for (String key : keys) {
            System.out.println(key);
        }
    }
}
