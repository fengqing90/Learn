package com.roncoo.eshop.datasync.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.alibaba.fastjson.JSONObject;
import com.roncoo.eshop.datasync.service.EshopProductService;

/**
 * 数据同步服务，就是获取各种原子数据的变更消息
 * 
 * （1）然后通过spring cloud fegion调用eshop-product-service服务的各种接口， 获取数据
 * （2）将原子数据在redis中进行增删改
 * （3）将维度数据变化消息写入rabbitmq中另外一个queue，供数据聚合服务来消费
 * 
 * @author Administrator
 *
 */
@Component  
@RabbitListener(queues = "data-change-queue")  
public class DataChangeQueueReceiver {  
	
	@Autowired
	private EshopProductService eshopProductService;
	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private RabbitMQSender rabbitMQSender;
  
    @RabbitHandler  
    public void process(String message) {  
    	// 对这个message进行解析
    	JSONObject jsonObject = JSONObject.parseObject(message);
    	
    	// 先获取data_type
    	String dataType = jsonObject.getString("data_type");  
    	if("brand".equals(dataType)) {
    		processBrandDataChangeMessage(jsonObject);  
    	} else if("category".equals(dataType)) {
    		processCategoryDataChangeMessage(jsonObject); 
    	} else if("product_intro".equals(dataType)) {
    		processProductIntroDataChangeMessage(jsonObject); 
    	} else if("product_property".equals(dataType)) {
    		processProductPropertyDataChangeMessage(jsonObject);
     	} else if("product".equals(dataType)) {
     		processProductDataChangeMessage(jsonObject); 
     	} else if("product_specification".equals(dataType)) {
     		processProductSpecificationDataChangeMessage(jsonObject);  
     	}
    }  
    
    private void processBrandDataChangeMessage(JSONObject messageJSONObject) {
    	Long id = messageJSONObject.getLong("id"); 
    	String eventType = messageJSONObject.getString("event_type"); 
    	
    	if("add".equals(eventType) || "update".equals(eventType)) { 
    		JSONObject dataJSONObject = JSONObject.parseObject(eshopProductService.findBrandById(id));  
    		Jedis jedis = jedisPool.getResource();
    		jedis.set("brand_" + dataJSONObject.getLong("id"), dataJSONObject.toJSONString());
    	} else if ("delete".equals(eventType)) {
    		Jedis jedis = jedisPool.getResource();
    		jedis.del("brand_" + id);
    	}
    	  
    	rabbitMQSender.send("aggr-data-change-queue", "{\"dim_type\": \"brand\", \"id\": " + id + "}");
    }
    
    private void processCategoryDataChangeMessage(JSONObject messageJSONObject) {
    	Long id = messageJSONObject.getLong("id"); 
    	String eventType = messageJSONObject.getString("event_type"); 
    	
    	if("add".equals(eventType) || "update".equals(eventType)) { 
    		JSONObject dataJSONObject = JSONObject.parseObject(eshopProductService.findCategoryById(id));  
    		Jedis jedis = jedisPool.getResource();
    		jedis.set("category_" + dataJSONObject.getLong("id"), dataJSONObject.toJSONString());
    	} else if ("delete".equals(eventType)) {
    		Jedis jedis = jedisPool.getResource();
    		jedis.del("category_" + id);
    	}
    	  
    	rabbitMQSender.send("aggr-data-change-queue", "{\"dim_type\": \"category\", \"id\": " + id + "}");
    }
    
    private void processProductIntroDataChangeMessage(JSONObject messageJSONObject) {
    	Long id = messageJSONObject.getLong("id"); 
    	Long productId = messageJSONObject.getLong("product_id");
    	String eventType = messageJSONObject.getString("event_type"); 
    	
    	if("add".equals(eventType) || "update".equals(eventType)) { 
    		JSONObject dataJSONObject = JSONObject.parseObject(eshopProductService.findProductIntroById(id));  
    		Jedis jedis = jedisPool.getResource();
    		jedis.set("product_intro_" + productId, dataJSONObject.toJSONString());
    	} else if ("delete".equals(eventType)) {
    		Jedis jedis = jedisPool.getResource();
    		jedis.del("product_intro_" + productId);
    	}
    	  
    	rabbitMQSender.send("aggr-data-change-queue", "{\"dim_type\": \"product\", \"id\": " + productId + "}");
    }
    
    private void processProductDataChangeMessage(JSONObject messageJSONObject) {
    	Long id = messageJSONObject.getLong("id"); 
    	String eventType = messageJSONObject.getString("event_type"); 
    	
    	if("add".equals(eventType) || "update".equals(eventType)) { 
    		JSONObject dataJSONObject = JSONObject.parseObject(eshopProductService.findProductById(id));  
    		Jedis jedis = jedisPool.getResource();
    		jedis.set("product_" + id, dataJSONObject.toJSONString());
    	} else if ("delete".equals(eventType)) {
    		Jedis jedis = jedisPool.getResource();
    		jedis.del("product_" + id);
    	}
    	  
    	rabbitMQSender.send("aggr-data-change-queue", "{\"dim_type\": \"product\", \"id\": " + id + "}");
    }
    
    private void processProductPropertyDataChangeMessage(JSONObject messageJSONObject) {
    	Long id = messageJSONObject.getLong("id"); 
    	Long productId = messageJSONObject.getLong("product_id");
    	String eventType = messageJSONObject.getString("event_type"); 
    	
    	if("add".equals(eventType) || "update".equals(eventType)) { 
    		JSONObject dataJSONObject = JSONObject.parseObject(eshopProductService.findProductPropertyById(id));  
    		Jedis jedis = jedisPool.getResource();
    		jedis.set("product_property_" + productId, dataJSONObject.toJSONString());
    	} else if ("delete".equals(eventType)) {
    		Jedis jedis = jedisPool.getResource();
    		jedis.del("product_property_" + productId);
    	}
    	  
    	rabbitMQSender.send("aggr-data-change-queue", "{\"dim_type\": \"product\", \"id\": " + productId + "}");
    }
    
    private void processProductSpecificationDataChangeMessage(JSONObject messageJSONObject) {
    	Long id = messageJSONObject.getLong("id"); 
    	Long productId = messageJSONObject.getLong("product_id");
    	String eventType = messageJSONObject.getString("event_type"); 
    	
    	if("add".equals(eventType) || "update".equals(eventType)) { 
    		JSONObject dataJSONObject = JSONObject.parseObject(eshopProductService.findProductSpecificationById(id));  
    		Jedis jedis = jedisPool.getResource();
    		jedis.set("product_specification_" + productId, dataJSONObject.toJSONString());
    	} else if ("delete".equals(eventType)) {
    		Jedis jedis = jedisPool.getResource();
    		jedis.del("product_specification_" + productId);
    	}
    	  
    	rabbitMQSender.send("aggr-data-change-queue", "{\"dim_type\": \"product\", \"id\": " + productId + "}");
    }
  
}  