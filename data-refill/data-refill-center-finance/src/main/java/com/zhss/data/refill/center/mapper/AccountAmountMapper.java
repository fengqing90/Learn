package com.zhss.data.refill.center.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 账户金额mapper组件
 * @author zhonghuashishan
 *
 */
@Mapper
public interface AccountAmountMapper {

	/**
	 * 修改账户的金额
	 * @param userAccountId 用户账号id
	 * @param amount 账号金额
	 */
	@Update("UPDATE account_amount "
			+ "SET amount=amount + #{updatedAmount} "
			+ "WHERE user_account_id=#{userAccountId}")   
	void updateAmount(
			@Param("userAccountId") Long userAccountId,
			@Param("updatedAmount") Double updatedAmount);   
	
	/**
	 * try转出金额
	 * @param userAccountId
	 * @param updatedAmount
	 */
	@Update("UPDATE account_amount SET "
				+ "amount = amount - #{updatedAmount},"
				+ "locked_amount = locked_amount + #{updatedAmount} "
			+ "WHERE user_account_id=#{userAccountId}")  
	void tryTransferOut(
			@Param("userAccountId") Long userAccountId,
			@Param("updatedAmount") Double updatedAmount);
	
	/**
	 * try转入金额
	 * @param userAccountId
	 * @param updatedAmount
	 */
	@Update("UPDATE account_amount SET "
				+ "locked_amount = locked_amount + #{updatedAmount} "
			+ "WHERE user_account_id=#{userAccountId}") 
	void tryTransferIn(
			@Param("userAccountId") Long userAccountId,
			@Param("updatedAmount") Double updatedAmount);
	
	/**
	 * confirm转出金额
	 * @param userAccountId
	 * @param updatedAmount
	 */
	@Update("UPDATE account_amount SET "
				+ "locked_amount = locked_amount - #{updatedAmount} "
			+ "WHERE user_account_id=#{userAccountId}")  
	void confirmTransferOut(
			@Param("userAccountId") Long userAccountId,
			@Param("updatedAmount") Double updatedAmount);
	
	/**
	 * try转入金额
	 * @param userAccountId
	 * @param updatedAmount
	 */
	@Update("UPDATE account_amount SET "
				+ "locked_amount = locked_amount - #{updatedAmount},"
				+ "amount = amount + #{updatedAmount} "
			+ "WHERE user_account_id=#{userAccountId}") 
	void confirmTransferIn(
			@Param("userAccountId") Long userAccountId,
			@Param("updatedAmount") Double updatedAmount);
	
	/**
	 * cancel转出金额
	 * @param userAccountId
	 * @param updatedAmount
	 */
	@Update("UPDATE account_amount SET "
				+ "amount = amount + #{updatedAmount},"
				+ "locked_amount = locked_amount - #{updatedAmount} "
			+ "WHERE user_account_id=#{userAccountId}")  
	void cancelTransferOut(
			@Param("userAccountId") Long userAccountId,
			@Param("updatedAmount") Double updatedAmount);
	
	/**
	 * cancel转入金额
	 * @param userAccountId
	 * @param updatedAmount
	 */
	@Update("UPDATE account_amount SET "
				+ "locked_amount = locked_amount - #{updatedAmount} "
			+ "WHERE user_account_id=#{userAccountId}") 
	void cancelTransferIn(
			@Param("userAccountId") Long userAccountId,
			@Param("updatedAmount") Double updatedAmount);
	
}
