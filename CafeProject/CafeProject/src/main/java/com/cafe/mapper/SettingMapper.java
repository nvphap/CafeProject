package com.cafe.mapper;



import java.util.Map;

import com.cafe.entity.Setting;

/**
* The login action.
*
* @author  	phap.nguyen
* @version 	1.0
* @since   	2015-05-27 
* @update   
*/
public interface SettingMapper {
	public Setting findSetting(Map<String,Object> params);
}
