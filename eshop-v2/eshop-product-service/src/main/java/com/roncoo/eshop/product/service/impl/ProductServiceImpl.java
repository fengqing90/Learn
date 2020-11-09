package com.roncoo.eshop.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roncoo.eshop.product.mapper.ProductMapper;
import com.roncoo.eshop.product.model.Product;
import com.roncoo.eshop.product.rabbitmq.RabbitMQSender;
import com.roncoo.eshop.product.rabbitmq.RabbitQueue;
import com.roncoo.eshop.product.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private RabbitMQSender rabbitMQSender;
	
	public void add(Product product) {
		productMapper.add(product); 
		rabbitMQSender.send(RabbitQueue.DATA_CHANGE_QUEUE, "{\"event_type\": \"add\", \"data_type\": \"product\", \"id\": " + product.getId() + "}");
	}

	public void update(Product product) {
		productMapper.update(product); 
		rabbitMQSender.send(RabbitQueue.DATA_CHANGE_QUEUE, "{\"event_type\": \"update\", \"data_type\": \"product\", \"id\": " + product.getId() + "}");
	}

	public void delete(Long id) {
		productMapper.delete(id); 
		rabbitMQSender.send(RabbitQueue.DATA_CHANGE_QUEUE, "{\"event_type\": \"delete\", \"data_type\": \"product\", \"id\": " + id + "}");
	}

	public Product findById(Long id) {
		return productMapper.findById(id);
	}

}
