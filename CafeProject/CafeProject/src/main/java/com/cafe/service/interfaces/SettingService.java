package com.cafe.service.interfaces;

import com.cafe.entity.Setting;

/**
* The login action.
*
* @author  	phap.nguyen
* @version 	1.0
* @since   	2015-05-27 
* @update   
*/
public interface SettingService {
	public Setting findSetting(Long cafeShopSn,String commonNo, String classNo);
}
