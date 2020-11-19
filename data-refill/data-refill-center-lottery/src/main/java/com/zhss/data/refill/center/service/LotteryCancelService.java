package com.zhss.data.refill.center.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhss.data.refill.center.api.LotteryDrawApi;
import com.zhss.data.refill.center.mapper.LotteryDrawMapper;

@Service("lotteryCancelService")
public class LotteryCancelService implements LotteryDrawApi {

	@Autowired
	private LotteryDrawMapper lotteryDrawMapper;
	
	@Override
	@Transactional
	public void increment(Long userAccountId) {
		lotteryDrawMapper.cancelIncrement(userAccountId); 
	}

}
