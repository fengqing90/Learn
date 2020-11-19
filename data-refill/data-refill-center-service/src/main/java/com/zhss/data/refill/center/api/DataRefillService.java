package com.zhss.data.refill.center.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.zhss.data.refill.center.mapper.DataRefillHistoryMapper;
import com.zhss.data.refill.center.service.api.DataRefillCenterApi;

@RestController
public class DataRefillService implements DataRefillCenterApi {

	@Autowired
	private DataRefillHistoryMapper dataRefillHistoryMapper;
	
	@Override
	public Boolean queryOperationStatus(@RequestParam("message") String message) {
		JSONObject messageJSONObject = JSONObject.parseObject(message);
		String dataRefillNo = messageJSONObject.getString("dataRefillNo"); 
		
		Long id = dataRefillHistoryMapper.findByDataRefillNo(dataRefillNo);
		if(id == null || id.equals(0L)) {
			return false;
		} else {
			return true;
		}
	}

}
