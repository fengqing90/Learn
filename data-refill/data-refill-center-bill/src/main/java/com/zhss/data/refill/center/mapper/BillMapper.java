package com.zhss.data.refill.center.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.zhss.data.refill.center.domain.Bill;

@Mapper
public interface BillMapper {

	@Insert("INSERT INTO bill("
				+ "from_account_id,"
				+ "to_account_id,"
				+ "transfer_amount,"
				+ "status"
			+ ") "
			+ "VALUES("
				+ "#{fromAccountId},"
				+ "#{toAccountId},"
				+ "#{transferAmount},"
				+ "0"  
			+ ")")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	void tryCreateBill(Bill bill);
	
	@Update("UPDATE bill SET status=1 WHERE id=#{id}")  
	void confirmCreateBill(@Param("id") Long id);
	
	@Update("UPDATE bill SET status=2 WHERE id=#{id}")
	void cancelCreateBill(@Param("id") Long id);
	
}
