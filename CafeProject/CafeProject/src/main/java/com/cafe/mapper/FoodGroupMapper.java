package com.cafe.mapper;


import java.util.List;
import java.util.Map;

import com.cafe.entity.FoodGroup;


/**
* The login action.
*
* @author  	phap.nguyen
* @version 	1.0
* @since   	2015-05-27 
* @update   
*/
public interface FoodGroupMapper {
	public List<FoodGroup> findAllFoodGroupList(Map<String,Object> map);
}
