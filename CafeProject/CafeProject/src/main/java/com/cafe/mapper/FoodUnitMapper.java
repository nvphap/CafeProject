package com.cafe.mapper;


import java.util.List;
import java.util.Map;


import com.cafe.entity.FoodUnit;


/**
* The login action.
*
* @author  	phap.nguyen
* @version 	1.0
* @since   	2015-05-27 
* @update   
*/
public interface FoodUnitMapper {
	public int insertFoodUnit(Map<String,Object> map);
	public List<FoodUnit> findAllFoodUnitList(Map<String,Object> map);
	public int countAllFoodUnitList(Map<String,Object> map);
	public FoodUnit findFoodUnit(Map<String,Object> map);
	public int updateFoodUnit(FoodUnit foodUnit);
}
