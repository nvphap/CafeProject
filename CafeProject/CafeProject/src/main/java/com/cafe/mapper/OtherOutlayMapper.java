package com.cafe.mapper;



import java.util.List;
import java.util.Map;

import com.cafe.entity.OtherOutlay;


/**
* The login action.
*
* @author  	phap.nguyen
* @version 	1.0
* @since   	2015-05-27 
* @update   
*/
public interface OtherOutlayMapper {
	public int insertOtherOutlay(OtherOutlay otherOutlay);
	public OtherOutlay findOtherOutlayByName(Map<String,Object> map);
	public List<OtherOutlay>findLimitAllOtherOutlayList(Map<String,Object> map);
}
