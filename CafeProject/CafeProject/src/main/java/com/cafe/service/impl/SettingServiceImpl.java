package com.cafe.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.cafe.entity.Setting;
import com.cafe.mapper.SettingMapper;
import com.cafe.service.interfaces.SettingService;
import com.cafe.utils.MyBatisUtil;
/**
* The login action.
*
* @author  	phap.nguyen
* @version 	1.0
* @since   	2015-05-27 
* @update   
*/
// This service handle SETTING table
public class SettingServiceImpl implements SettingService {
	public Setting findSetting(Long cafeShopSn,String commonNo, String classNo){
		Setting setting = null;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put(Setting.COL_CAFE_SHOP_SN,cafeShopSn);
		params.put(Setting.COL_COMMON_NO,commonNo);
		params.put(Setting.COL_CLASS_NO,classNo);
		
		SqlSession session = MyBatisUtil.openSession();
		try {
			SettingMapper mapper = session.getMapper(SettingMapper.class);
			setting= mapper.findSetting(params);
		} finally {
			session.close();
		}
		return setting;
	}
}
