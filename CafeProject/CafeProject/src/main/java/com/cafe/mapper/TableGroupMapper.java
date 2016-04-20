package com.cafe.mapper;

import java.util.List;
import java.util.Map;

import com.cafe.entity.TableGroup;








/**
* The login action.
*
* @author  	phap.nguyen
* @version 	1.0
* @since   	2015-05-27 
* @update   
*/
public interface TableGroupMapper {
	public List<TableGroup> findAllTableGroupList(Map<String,Object> map);
}
