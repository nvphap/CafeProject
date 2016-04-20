package com.cafe.mapper;


import java.util.List;
import java.util.Map;

import com.cafe.entity.CafeTable;


/**
* The login action.
*
* @author  	phap.nguyen
* @version 	1.0
* @since   	2015-05-27 
* @update   
*/
public interface CafeTableMapper {
	public int insertCafeTable(CafeTable cafeTable);
	public List<CafeTable> findAllCafeTableList(Map<String,Object> map);
	public CafeTable findCafeTable(Map<String,Object> map);
	public int updateCafeTable(CafeTable cafeTable);
	public CafeTable findCafeTableViaName(Map<String,Object> map);
	public int deleteCafeTableLogic(Map<String,Object> map);
	public List<Map<String,Object>>findAllCafeTableListOrderByIdx(Map<String,Object> map);
	public List<CafeTable>findAllCafeTableInGroup(Map<String,Object> map);
}
