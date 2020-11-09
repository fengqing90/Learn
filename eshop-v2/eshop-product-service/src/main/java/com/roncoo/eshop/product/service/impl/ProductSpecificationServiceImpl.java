package com.roncoo.eshop.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roncoo.eshop.product.mapper.ProductSpecificationMapper;
import com.roncoo.eshop.product.model.ProductSpecification;
import com.roncoo.eshop.product.rabbitmq.RabbitMQSender;
import com.roncoo.eshop.product.rabbitmq.RabbitQueue;
import com.roncoo.eshop.product.service.ProductSpecificationService;

@Service
public class ProductSpecificationServiceImpl implements ProductSpecificationService {

	@Autowired
	private ProductSpecificationMapper productSpecificationMapper;
	@Autowired
	private RabbitMQSender rabbitMQSender;
	
	public void add(ProductSpecification productSpecification) {
		productSpecificationMapper.add(productSpecification); 
		rabbitMQSender.send(RabbitQueue.DATA_CHANGE_QUEUE, "{\"event_type\": \"add\", \"data_type\": \"product_specification\", \"id\": " + productSpecification.getId() + ", \"product_id\": " + productSpecification.getProductId() + "}");
	}

	public void update(ProductSpecification productSpecification) {
		productSpecificationMapper.update(productSpecification); 
		rabbitMQSender.send(RabbitQueue.DATA_CHANGE_QUEUE, "{\"event_type\": \"update\", \"data_type\": \"product_specification\", \"id\": " + productSpecification.getId() + ", \"product_id\": " + productSpecification.getProductId() + "}");
	}

	public void delete(Long id) {
		ProductSpecification productSpecification = findById(id);
		productSpecificationMapper.delete(id); 
		rabbitMQSender.send(RabbitQueue.DATA_CHANGE_QUEUE, "{\"event_type\": \"delete\", \"data_type\": \"product_specification\", \"id\": " + id + ", \"product_id\": " + productSpecification.getProductId() + "}");
	}

	public ProductSpecification findById(Long id) {
		return productSpecificationMapper.findById(id);
	}

}
