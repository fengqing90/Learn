package com.roncoo.eshop.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roncoo.eshop.product.mapper.ProductIntroMapper;
import com.roncoo.eshop.product.model.ProductIntro;
import com.roncoo.eshop.product.rabbitmq.RabbitMQSender;
import com.roncoo.eshop.product.rabbitmq.RabbitQueue;
import com.roncoo.eshop.product.service.ProductIntroService;

@Service
public class ProductIntroServiceImpl implements ProductIntroService {

	@Autowired
	private ProductIntroMapper productIntroMapper;
	@Autowired
	private RabbitMQSender rabbitMQSender;
	
	public void add(ProductIntro productIntro) {
		productIntroMapper.add(productIntro); 
		rabbitMQSender.send(RabbitQueue.DATA_CHANGE_QUEUE, "{\"event_type\": \"add\", \"data_type\": \"product_intro\", \"id\": " + productIntro.getId() + ", \"product_id\": " + productIntro.getProductId() + "}");
	}

	public void update(ProductIntro productIntro) {
		productIntroMapper.update(productIntro); 
		rabbitMQSender.send(RabbitQueue.DATA_CHANGE_QUEUE, "{\"event_type\": \"update\", \"data_type\": \"product_intro\", \"id\": " + productIntro.getId() + ", \"product_id\": " + productIntro.getProductId() + "}");
	}

	public void delete(Long id) {
		ProductIntro productIntro = findById(id);
		productIntroMapper.delete(id); 
		rabbitMQSender.send(RabbitQueue.DATA_CHANGE_QUEUE, "{\"event_type\": \"delete\", \"data_type\": \"product_intro\", \"id\": " + id + ", \"product_id\": " + productIntro.getProductId() + "}");
	}

	public ProductIntro findById(Long id) {
		return productIntroMapper.findById(id);
	}

}
