package com.zhss.eshop.schedule.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class CuratorClient {

	private static CuratorFramework client;
	
	static {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		client = CuratorFrameworkFactory.newClient(
				"192.168.31.184:2181,192.168.31.207:2181,192.168.31.192:2181",  
				retryPolicy);
		client.start();
	}
	
	public static CuratorFramework getInstance() {  
		return client;
	}
	
}
