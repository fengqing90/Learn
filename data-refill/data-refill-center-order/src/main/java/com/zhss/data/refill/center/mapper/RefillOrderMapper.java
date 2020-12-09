package com.zhss.data.refill.center.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zhss.data.refill.center.domain.RefillOrder;

/**
 * 充值订单mapper组件
 * @author zhonghuashishan
 *
 */
@Mapper
public interface RefillOrderMapper {

	/**
	 * 增加一个充值订单
	 * @param refillOrder 充值订单
	 */
	@Insert("INSERT INTO refill_order("
				+ "order_no,"
				+ "user_account_id,"
				+ "business_account_id,"
				+ "business_name,"
				+ "amount,"
				+ "title,"
				+ "type,"
				+ "status,"
				+ "pay_type,"
				+ "refill_comment,"
				+ "refill_phone_number,"
				+ "refill_data,"
				+ "credit"
			+ ") "
			+ "VALUES("
				+ "#{orderNo},"
				+ "#{userAccountId},"
				+ "#{businessAccountId},"
				+ "#{businessName},"
				+ "#{amount},"
				+ "#{title},"
				+ "#{type},"
				+ "#{status},"
				+ "#{payType},"
				+ "#{refillComment},"
				+ "#{refillPhoneNumber},"
				+ "#{refillData},"
				+ "#{credit}"
			+ ")")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	void tryCreateRefillOrder(RefillOrder refillOrder);
	
	@Update("UPDATE refill_order SET status=1 WHERE id=#{id}")  
	void confirmCreateRefillOrder(@Param("id") Long id);
	
	@Update("DELETE FROM refill_order WHERE id=#{id}")  
	void cancelCreateRefillOrder(@Param("id") Long id);
	
	/**
	 * 查询所有的充值订单
	 * @return
	 */
	@Select("SELECT "
				+ "id,"
				+ "title,"
				+ "type,"
				+ "created_time,"
				+ "amount,"
				+ "status "
			+ "FROM refill_order "
			+ "WHERE user_account_id=#{userAccountId}") 
	@Results({
		@Result(column = "created_time", property = "createdTime") 
	})
	List<RefillOrder> queryAll(@Param("userAccountId") Long userAccountId);
	
	/**
	 * 查询充值订单
	 * @param id 充值订单id
	 * @return
	 */
	@Select("SELECT * FROM refill_order WHERE id=#{id}")  
	@Results({
		@Result(column = "order_no", property = "orderNo"), 
		@Result(column = "user_account_id", property = "userAccountId"),
		@Result(column = "business_account_id", property = "businessAccountId"),
		@Result(column = "business_name", property = "businessName"),
		@Result(column = "pay_type", property = "payType"),
		@Result(column = "refill_comment", property = "refillComment"),
		@Result(column = "refill_phone_number", property = "refillPhoneNumber"),
		@Result(column = "refill_data", property = "refillData"),
		@Result(column = "created_time", property = "createdTime"),
		@Result(column = "modified_time", property = "modifiedTime")  
	})
	RefillOrder queryById(@Param("id") Long id);
	
}
