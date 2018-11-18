package com.peelmicro.server.controllers;

import org.springframework.web.bind.annotation.RestController;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.peelmicro.server.repositories.ValuesRepository;
import com.peelmicro.server.services.RedisService;
import com.peelmicro.server.models.Value;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/values")
public class ValuesController {

    @Autowired
    private ValuesRepository valuesRepository;

    @Autowired
    private RedisService redisService;

    private final String setKey = "values";

    @RequestMapping()
    public String index() {
        return "Hi";
    }

    @RequestMapping(value = "/current")
    public Map<String,String> getCurrent()
    {
      Map<String,String> result = new HashMap<String,String>();
      Jedis jedis = redisService.getJedis();
      Set<String> items = jedis.smembers(setKey);
      for (String item: items) {
        String value = jedis.get(item);
        String key = item.replace(setKey, "");
        result.put(key, value);
      }
      jedis.close();
      return result;
    }

    @RequestMapping(value = "/all")
    public List<Value> getAll()
    {
        return valuesRepository.findAll();
    }

    @PostMapping()
    public ResponseEntity<HashMap<String, Object>> post(@RequestBody HashMap<String, Object> request) {
      HashMap<String, Object> response = new HashMap<>();
      Boolean validIndex = true;
      String index = "";
      Integer number = 0;
      if (!request.containsKey("index") ) {
        validIndex = false;
      }
      if (validIndex) {
        index = request.get("index").toString();
        try  
        {     
          number = Integer.parseInt(index);  
        }  
        catch(NumberFormatException nfe)  
        { 
          validIndex = false; 
        }
      }
      if (!validIndex) {
        response.put("working", false);
        response.put("message", "Invalid Index!");
        return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.UNPROCESSABLE_ENTITY);
      }
      Value value = new Value(number);
      valuesRepository.save(value);

      String redisKey = setKey + index;
      Jedis jedis = redisService.getJedis();
      jedis.set(redisKey, "Nothing yet!");
      jedis.sadd(setKey, redisKey);
      jedis.publish("message", index);
      jedis.close();

      response.put("working", true);
      return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.OK);
    }
}