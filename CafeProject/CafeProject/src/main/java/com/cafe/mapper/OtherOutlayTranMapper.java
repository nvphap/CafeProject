package com.cafe.mapper;


import java.util.List;
import java.util.Map;

import com.cafe.entity.OtherOutlayTran;


/**
* The login action.
*
* @author  	phap.nguyen
* @version 	1.0
* @since   	2015-05-27 
* @update   
*/
public interface OtherOutlayTranMapper {
	public int insertOtherOutlayTran(OtherOutlayTran otherOutlayTran);
	public List<Map<String,Object>> findLimitOtherOutlayTranList(Map<String,Object> map);
	public int countAllOtherOutlayTranList(Map<String,Object> map);
	public Map<String,Object> findOtherOutlayTranMap(Map<String,Object> map);
	public int updateOtherOutlayTran(OtherOutlayTran otherOutlayTran);
	public int deleteOtherOutlayTran(Map<String,Object> map);
	public int calculateMoneyOfOtherOutlayTran(Map<String,Object> map);
	public int countAllTypeOfOtherOutlayList(Map<String,Object> map);
	public List<Map<String,Object>> findLimitOtherOutlayTranStatisticList(Map<String,Object> map);
}
