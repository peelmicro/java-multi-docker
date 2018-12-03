package com.peelmicro;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
public class App 
{
    public static void main( String[] args )
    {
        String redisHost = System.getenv("REDIS_HOST");
        String redisPort = System.getenv("REDIS_PORT");
        
        Jedis jedis = null;
        try   {
            jedis = new Jedis(redisHost, Integer.parseInt(redisPort));
            jedis.subscribe(new JedisPubSub() {
                @Override
                public void onMessage(String channel, String message) {
                    String setKey = "values";
                    String redisKey = setKey + message;
                    Jedis jedis = new Jedis(redisHost, Integer.parseInt(redisPort));
                    jedis.set(redisKey, fib(Integer.parseInt(message)).toString());
                    jedis.sadd(setKey, redisKey);
                    jedis.quit();
                    jedis.close();                    
                }
            }, "message");            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.quit();
                jedis.close();               
            }
        }
    }

    protected static Integer fib(int index) {
        if (index < 2) return 1;
        return fib(index - 1) + fib(index - 2);          
    }
    
}
