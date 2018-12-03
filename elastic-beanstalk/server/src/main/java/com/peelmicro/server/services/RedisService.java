package com.peelmicro.server.services;

import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

@Service
public class RedisService {

  String redisHost;
  String redisPort;
  
  private RedisService() {
    redisHost = System.getenv("REDIS_HOST");
    redisPort = System.getenv("REDIS_PORT");
  }

  public Jedis getJedis() {
    return  new Jedis(redisHost, Integer.parseInt(redisPort));
  }
}