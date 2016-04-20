package com.cafe.mapper;


import java.util.List;
import java.util.Map;

import com.cafe.entity.Staff;

/**
* The login action.
*
* @author  	phap.nguyen
* @version 	1.0
* @since   	2015-05-27 
* @update   
*/
public interface StaffMapper {
	public Staff findStaff(Map<String,Object> map);
	public Staff findStaffViaUserId(Map<String,Object> map);
	public int updateStaffPassword(Map<String,Object> map);
	public Map<String,Object> findStaffRole(Map<String,Object> map);
	public int insertStaff(Staff staff);
	public int countAllStaff(Map<String,Object> map);
	public List<Map<String,Object>> findLimitStaffRole(Map<String,Object> map);
	public int updateStaff(Staff staff);
	public int updateStaffStatus(Map<String,Object> map);
	
}
