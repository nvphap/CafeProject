package com.cafe.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.cafe.dto.LoginUser;
import com.cafe.entity.CafeShop;
import com.cafe.entity.Role;
import com.cafe.entity.Staff;
import com.cafe.form.StaffForm;
import com.cafe.mapper.StaffMapper;
import com.cafe.search.StaffSearch;
import com.cafe.service.interfaces.AuthorityService;
import com.cafe.utils.AppDateUtils;
import com.cafe.utils.AppNumUtils;
import com.cafe.utils.AppStrUtils;
import com.cafe.utils.Constant;
import com.cafe.utils.MyBatisUtil;
/**
* The login action.
*
* @author  	phap.nguyen
* @version 	1.0
* @since   	2015-05-27 
* @update   
*/
// This service handle STAFF, ROLE table
public class AuthorityServiceImpl implements AuthorityService {
	
	public StaffForm findStaffRole(Long cafeShopSn,Long staffSn){
		StaffForm staff = null;
		SqlSession session = MyBatisUtil.openSession();
		try {
			StaffMapper mapper = session.getMapper(StaffMapper.class);
			Map<String,Object> map= new HashMap<String,Object>();
			map.put(Staff.COL_CAFE_SHOP_SN,cafeShopSn);
			map.put(Staff.COL_SN, staffSn);
			Map<String,Object> mapStaff= mapper.findStaffRole(map);
			staff = toStaffForm(mapStaff);
		} finally {
			session.close();
		}
		return staff;
	}
	
	public int updateStaffStatus(Long cafeShopSn,Long staffSn,int status){
		int count =0;
		if(null==staffSn){
			return 0;
		}
		SqlSession session = MyBatisUtil.openSession();
		try{
			StaffMapper mapper = session.getMapper(StaffMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(Staff.COL_CAFE_SHOP_SN,cafeShopSn);
			map.put(Staff.COL_SN,staffSn);
			map.put(Staff.COL_STATUS,status);
			count=mapper.updateStaffStatus(map);
			session.commit();
		}finally{
			session.close();
		}
		return count;
	}
	
	public Staff findStaff(String userId, String password){
		Staff staff = null;
		SqlSession session = MyBatisUtil.openSession();
		try {
			StaffMapper mapper = session.getMapper(StaffMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(Staff.COL_USER_ID,userId);
			map.put(Staff.COL_PASSWORD,password);
			staff= mapper.findStaff(map);
		} finally {
			session.close();
		}
		return staff;
	}
	
	public LoginUser login(String userId, String password){
		LoginUser loginUser = new LoginUser();
		loginUser.setLoginStatus(LoginUser.STS_FAILURE);
		SqlSession session = MyBatisUtil.openSession();
		try {
			StaffMapper mapper = session.getMapper(StaffMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(Staff.COL_USER_ID,userId);
			map.put(Staff.COL_PASSWORD,password);
			Staff staff= mapper.findStaff(map);
			if(null==staff){//login false
				loginUser.setUserId(userId);
				loginUser.setLoginStatus(LoginUser.STS_FAILURE);
			}else{
				loginUser.setStaff(staff);
				loginUser.setLoginStatus(LoginUser.STS_OK);
			}
		} finally {
			session.close();
		}
		return loginUser;
	}
	
	public Staff findStaffViaUserId(String userId){
		Staff staff = null;
		SqlSession session = MyBatisUtil.openSession();
		try {
			StaffMapper mapper = session.getMapper(StaffMapper.class);
			Map<String,Object> params = new HashMap<String,Object>();
			params.put(Staff.COL_USER_ID,userId);
			staff= mapper.findStaffViaUserId(params);
		}catch(Exception ex){
			
		} finally {
			session.close();
		}
		return staff;
	}
	
	public int updateStaffPassword(Long cafeShopSn,Long staffSn, String password){
		if(null==staffSn){
			return 0;
		}
		SqlSession session = MyBatisUtil.openSession();
		try{
			StaffMapper mapper = session.getMapper(StaffMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(Staff.COL_CAFE_SHOP_SN,cafeShopSn);
			map.put(Staff.COL_SN,staffSn);
			map.put(Staff.COL_PASSWORD,password);
			int count=mapper.updateStaffPassword(map);
			session.commit();
			return count;
		}finally{
			session.close();
		}
	}
	
	private StaffForm toStaffForm(Map<String,Object> mapStaff){
		if(null==mapStaff){
			return null;
		}
		StaffForm newItem = new StaffForm();
		newItem.setCafeShopSn(AppNumUtils.toLong(mapStaff.get(CafeShop.DB_CAFE_SHOP_SN)));
		newItem.setCafeShopName(AppStrUtils.toEmpty(mapStaff.get(CafeShop.DB_CAFE_SHOP_NAME)));
		newItem.setSn(AppNumUtils.toLong(mapStaff.get(Staff.DB_SN)));
		newItem.setName(AppStrUtils.toEmpty(mapStaff.get(Staff.DB_NAME)));
		newItem.setRoleTitle(AppStrUtils.toEmpty(mapStaff.get(Role.DB_ROLE_NAME)));
		newItem.setSelectRole(AppNumUtils.toLong(mapStaff.get(Staff.DB_ROLE_SN)));
		newItem.setUserId(AppStrUtils.toEmpty((mapStaff.get(Staff.DB_USER_ID))));
		newItem.setMemo(AppStrUtils.toEmpty((mapStaff.get(Staff.DB_MEMO))));
		newItem.setLastUpdate(AppDateUtils.toYYYYMMDDHHMMStr((Date)mapStaff.get(Staff.DB_LAST_UPDATE)));
		return newItem;
	}
	
	public int insertStaff(Staff newStaff){
		if(null==newStaff){
			return 0;
		}
		SqlSession session = MyBatisUtil.openSession();
		try{
			StaffMapper mapper = session.getMapper(StaffMapper.class);
			int count= mapper.insertStaff(newStaff);
			session.commit();
			return count;
		}finally{
			session.close();
		}
	}
	
	public int countAllStaff(Long cafeShopSn) {
		int count=0;
		SqlSession session = MyBatisUtil.openSession();
		try{
			StaffMapper mapper = session.getMapper(StaffMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			if(null!=cafeShopSn && 0==cafeShopSn){
				cafeShopSn = null;
			}
			map.put(Staff.COL_CAFE_SHOP_SN,cafeShopSn);
			count= mapper.countAllStaff(map);
		}finally{
			session.close();
		}
		return count;
	}
	
	public List<StaffForm> findLimitStaffRole(StaffSearch search){
		List<StaffForm> staffFormList = null;
		if(null== search){
			return new ArrayList<StaffForm>();
		}
		SqlSession session = MyBatisUtil.openSession();
		try {
			StaffMapper mapper = session.getMapper(StaffMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			if(null!=search.getCafeShopSn() && 0==search.getCafeShopSn()){
				search.setCafeShopSn(null);
			}
			map.put(Staff.COL_CAFE_SHOP_SN, search.getCafeShopSn());
			map.put(Constant.SQL_LIMIT, search.getLimit());
			map.put(Constant.SQL_OFFSET,search.getOffset());
			List<Map<String,Object>> mapStaffs = mapper.findLimitStaffRole(map);
			staffFormList = toStaffFormList(mapStaffs);
		} finally {
			session.close();
		}
		return staffFormList;
	}
	
	private List<StaffForm> toStaffFormList(List<Map<String,Object>> mapStaffs){
		List<StaffForm> listStaffForm = new ArrayList<StaffForm>();
		if(null!=mapStaffs){
			StaffForm newItem = null;
			Map<String,Object> map = null;
			for (int i=0;i<mapStaffs.size();i++) {
				map = mapStaffs.get(i);
				newItem = toStaffForm(map);
				listStaffForm.add(newItem);
			}
		}
		return listStaffForm;
	}
	
	public int updateStaff(Staff updateStaff){
		if(null==updateStaff){
			return 0;
		}
		SqlSession session = MyBatisUtil.openSession();
		try{
			StaffMapper mapper = session.getMapper(StaffMapper.class);
			int count=mapper.updateStaff(updateStaff);
			session.commit();
			return count;
		}finally{
			session.close();
		}
	}
}
