package com.zhss.data.refill.center.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DataRefillHistoryMapper {

	@Insert("INSERT INTO data_refill_history(data_refill_no) VALUES(#{dataRefillNo})")  
	public void create(@Param("dataRefillNo") String dataRefillNo);
	
	@Select("SELECT id FROM data_refill_history WHERE data_refill_no=#{dataRefillNo}")  
	public Long findByDataRefillNo(@Param("dataRefillNo") String dataRefillNo);
	
}
