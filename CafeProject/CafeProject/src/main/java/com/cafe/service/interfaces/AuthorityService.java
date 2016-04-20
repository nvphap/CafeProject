package com.cafe.service.interfaces;


import java.util.List;

import com.cafe.dto.LoginUser;
import com.cafe.entity.Staff;
import com.cafe.form.StaffForm;
import com.cafe.search.StaffSearch;
/**
* The login action.
*
* @author  	phap.nguyen
* @version 	1.0
* @since   	2015-05-27 
* @update   
*/
public interface AuthorityService {
	
	public LoginUser login(String userId, String password);
	public Staff findStaffViaUserId(String userId);
	public int updateStaffPassword(Long cafeShopSn,Long staffSn, String password);
	public StaffForm findStaffRole(Long cafeShopSn,Long staffSn);
	public Staff findStaff(String userId, String password);
	public int insertStaff(Staff newStaff);
	public int countAllStaff(Long cafeShopSn);
	public List<StaffForm> findLimitStaffRole(StaffSearch search);
	public int updateStaff(Staff updateStaff);
	public int updateStaffStatus(Long cafeShopSn,Long staffSn,int status);
}
