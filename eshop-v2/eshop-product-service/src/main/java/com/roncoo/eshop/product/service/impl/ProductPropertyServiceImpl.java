package com.roncoo.eshop.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roncoo.eshop.product.mapper.ProductPropertyMapper;
import com.roncoo.eshop.product.model.ProductProperty;
import com.roncoo.eshop.product.rabbitmq.RabbitMQSender;
import com.roncoo.eshop.product.rabbitmq.RabbitQueue;
import com.roncoo.eshop.product.service.ProductPropertyService;

@Service
public class ProductPropertyServiceImpl implements ProductPropertyService {

	@Autowired
	private ProductPropertyMapper productPropertyMapper;
	@Autowired
	private RabbitMQSender rabbitMQSender;
	
	public void add(ProductProperty productProperty) {
		productPropertyMapper.add(productProperty); 
		rabbitMQSender.send(RabbitQueue.DATA_CHANGE_QUEUE, "{\"event_type\": \"add\", \"data_type\": \"product_property\", \"id\": " + productProperty.getId() + ", \"product_id\": " + productProperty.getProductId() + "}");
	}

	public void update(ProductProperty productProperty) {
		productPropertyMapper.update(productProperty); 
		rabbitMQSender.send(RabbitQueue.DATA_CHANGE_QUEUE, "{\"event_type\": \"update\", \"data_type\": \"product_property\", \"id\": " + productProperty.getId() + ", \"product_id\": " + productProperty.getProductId() + "}");
	}

	public void delete(Long id) {
		ProductProperty productProperty = findById(id);
		productPropertyMapper.delete(id); 
		rabbitMQSender.send(RabbitQueue.DATA_CHANGE_QUEUE, "{\"event_type\": \"delete\", \"data_type\": \"product_property\", \"id\": " + id + ", \"product_id\": " + productProperty.getProductId() + "}");
	}

	public ProductProperty findById(Long id) {
		return productPropertyMapper.findById(id);
	}

}
