package com.zhss.data.refill.center.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhss.data.refill.center.api.LotteryDrawApi;
import com.zhss.data.refill.center.mapper.LotteryDrawMapper;

@Service("lotteryCancelService")
@RequestMapping("/lottery/cancel")  
public class LotteryCancelService implements LotteryDrawApi {

	@Autowired
	private LotteryDrawMapper lotteryDrawMapper;
	
	@Override
	@Transactional
	public void increment(Long userAccountId) {
		lotteryDrawMapper.cancelIncrement(userAccountId); 
		System.out.println(new Date() + ": cancel增加抽奖次数接口");  
	}

}
