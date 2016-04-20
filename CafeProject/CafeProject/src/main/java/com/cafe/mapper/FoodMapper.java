package com.cafe.mapper;


import java.util.List;
import java.util.Map;


import com.cafe.entity.Food;


/**
* The login action.
*
* @author  	phap.nguyen
* @version 	1.0
* @since   	2015-05-27 
* @update   
*/
public interface FoodMapper {
	public int insertFood(Food food);
	public List<Food> findAllFoodList(Map<String,Object> map);
	public int countAllFoodList(Map<String,Object> map);
	public Food findFood(Map<String,Object> map);
	public int updateFood(Food food);
	public List<Map<String,Object>>findAllFoodMapList(Map<String,Object> map);
	public Food findFoodViaName(Map<String,Object> map);
	public int deleteFoodLogic(Map<String,Object> map);
	public List<Map<String,Object>>findAllFoodMapListForSuggestion(Map<String,Object> map);
}
