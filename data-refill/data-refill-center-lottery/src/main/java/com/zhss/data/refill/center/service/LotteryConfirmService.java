package com.zhss.data.refill.center.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhss.data.refill.center.api.LotteryDrawApi;
import com.zhss.data.refill.center.mapper.LotteryDrawMapper;

@Service("lotteryConfirmService")
@RequestMapping("/lottery/confirm")  
public class LotteryConfirmService implements LotteryDrawApi {

	@Autowired
	private LotteryDrawMapper lotteryDrawMapper;
	
	@Override
	@Transactional
	public void increment(Long userAccountId) {
		lotteryDrawMapper.confirmIncrement(userAccountId); 
		System.out.println(new Date() + ": confirm增加抽奖次数接口");  
	}

}
