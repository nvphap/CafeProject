package com.cafe.mapper;


import java.util.List;

import com.cafe.entity.CafeShop;

/**
* The login action.
*
* @author  	phap.nguyen
* @version 	1.0
* @since   	2015-05-27 
* @update   
*/
public interface CafeShopMapper {
	public List<CafeShop> findAllCafeShopList();
}
