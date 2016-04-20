package com.cafe.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.cafe.mapper.PermissionMapper;
import com.cafe.mapper.RoleMapper;
import com.cafe.dto.Group;
import com.cafe.entity.PageGroup;
import com.cafe.entity.Permission;
import com.cafe.entity.Role;
import com.cafe.form.PermissionForm;
import com.cafe.search.PermissionSearch;
import com.cafe.service.interfaces.PermissionService;
import com.cafe.service.interfaces.SettingService;
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
// This service handle ROLE,PERMISSION,PAGE_GROUP table 
public class PermissionServiceImpl implements PermissionService {
	@Autowired
	private SettingService settingService;
	
	public List<PermissionForm> findPermissionFormList(PermissionSearch search){
		List<PermissionForm> permissionFormList = new ArrayList<PermissionForm>();
		if(null==search.getRoleSn()){
			return new ArrayList<PermissionForm>();
		}
		List<Map<String,Object>> resultList= findPermissionMapList(search);
		PermissionForm newForm = null;
		for (Map<String,Object> permission : resultList) {
			newForm = toPermissionForm(permission);
			permissionFormList.add(newForm);
		}
		return permissionFormList;
	}
	
	public List<Map<String,Object>> findPermissionMapList(PermissionSearch search){
		List<Map<String,Object>> permissionList = null;
		if(null==search.getRoleSn()){
			return new ArrayList<Map<String,Object>>();
		}
		SqlSession session = MyBatisUtil.openSession();
		try {
			PermissionMapper mapper = session.getMapper(PermissionMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(Permission.COL_ROLE_SN,search.getRoleSn());
			map.put(Constant.SQL_LIMIT,search.getLimit());
			map.put(Constant.SQL_OFFSET,search.getOffset());
			permissionList= mapper.findAdminPermissionMapList(map);
		} finally {
			session.close();
		}
		return permissionList;
	}
	
	
	private PermissionForm toPermissionForm(Map<String,Object> map){
		PermissionForm form = null;
		if(null!=map){
			form = new PermissionForm();
			form.setSn(AppNumUtils.toLong(map.get(Permission.DB_SN)));
			form.setPageGroupSn(AppNumUtils.toLong(map.get(Permission.DB_PAGE_GROUP_SN)));
			form.setPageGroupName(AppStrUtils.toEmpty(map.get(PageGroup.DB_PAGE_GROUP_NAME)));
			form.setRoleSn(AppNumUtils.toLong(map.get(Permission.DB_ROLE_SN)));
			form.setAdd(AppNumUtils.toIntValue(map.get(Permission.DB_ADD)));
			form.setView(AppNumUtils.toIntValue(map.get(Permission.DB_VIEW)));
			form.setModify(AppNumUtils.toIntValue(map.get(Permission.DB_MODIFY)));
			form.setDelete(AppNumUtils.toIntValue(map.get(Permission.DB_DELETE)));
			form.setPrint(AppNumUtils.toIntValue(map.get(Permission.DB_PRINT)));
			form.setHasView(AppNumUtils.toIntValue(map.get(PageGroup.DB_HAS_VIEW)));
			form.setHasModify(AppNumUtils.toIntValue(map.get(PageGroup.DB_HAS_MODIFY)));
			form.setHasCreate(AppNumUtils.toIntValue(map.get(PageGroup.DB_HAS_CREATE)));
			form.setHasDelete(AppNumUtils.toIntValue(map.get(PageGroup.DB_HAS_DELETE)));
			form.setHasPrint(AppNumUtils.toIntValue(map.get(PageGroup.DB_HAS_PRINT)));
		}
		return form;
	}
	
	public int countPermissionList(PermissionSearch search){
		if(null==search.getRoleSn()){
			return 0;
		}
		SqlSession session = MyBatisUtil.openSession();
		try {
			PermissionMapper mapper = session.getMapper(PermissionMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(Permission.COL_ROLE_SN,search.getRoleSn());
			return mapper.countAdminPermissionList(map);
		} finally {
			session.close();
		}
	}
	
	public List<Role> findAllRoleList(boolean includeAdmin){
		List<Role> roleList = null;
		SqlSession session = MyBatisUtil.openSession();
		try {
			RoleMapper mapper = session.getMapper(RoleMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			if(true==includeAdmin){
				map.put(Role.COL_ADMIN,includeAdmin);
			}else{
				map.put(Role.COL_ADMIN,null);
			}
			
			roleList= mapper.findAllRoleList(map);
		} finally {
			session.close();
		}
		return roleList;
	}
	
	public Permission findPermission(Long roleSn,Long pageGroupSn){
		Permission permission = null;
		SqlSession session = MyBatisUtil.openSession();
		try {
			PermissionMapper mapper = session.getMapper(PermissionMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(Permission.COL_ROLE_SN,roleSn);
			map.put(Permission.COL_PAGE_GROUP_SN,pageGroupSn);
			permission= mapper.findPermissionViaHospitalGroupRoleSn(map);
		} finally {
			session.close();
		}
		return permission;
	}
	
	public Role findRole(Long sn){
		Role role = null;
		if(null== sn){
			return null;
		}
		SqlSession session = MyBatisUtil.openSession();
		try {
			RoleMapper mapper = session.getMapper(RoleMapper.class);
			role= mapper.findRole(sn);
		} finally {
			session.close();
		}
		return role;
	}
	
	public Permission findPermission(Long sn){
		Permission permission = null;
		if(null== sn){
			return null;
		}
		SqlSession session = MyBatisUtil.openSession();
		try {
			PermissionMapper mapper = session.getMapper(PermissionMapper.class);
			permission= mapper.findPermission(sn);
		} finally {
			session.close();
		}
		return permission;
	}
	
	
	public int updatePageGroupRolePermission(Long permissionSn,String type, int value){
		if(null==permissionSn){
			return 0;
		}
		SqlSession session = MyBatisUtil.openSession();
		try{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(Permission.COL_SN,permissionSn);
			map.put(Permission.COL_TYPE,type);
			map.put(Permission.COL_VALUE, value);
			PermissionMapper mapper = session.getMapper(PermissionMapper.class);
			int count=mapper.updatePageGroupRolePermission(map);
			session.commit();
			return count;
		}finally{
			session.close();
		}
	}
	
	public Permission findPermissionViaRoleGroup(Long roleSn, Long pageGroupSn){
		Permission permission = null;
		SqlSession session = MyBatisUtil.openSession();
		try {
			PermissionMapper mapper = session.getMapper(PermissionMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(Permission.COL_ROLE_SN,roleSn);
			map.put(Permission.COL_PAGE_GROUP_SN,pageGroupSn);
			permission= mapper.findPermissionViaRoleGroup(map);
		} finally {
			session.close();
		}
		return permission;
	}
	
	public List<Permission> findPermissionListViaRole(Long roleSn){
		SqlSession session = MyBatisUtil.openSession();
		try {
			PermissionMapper mapper = session.getMapper(PermissionMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(Permission.COL_ROLE_SN,roleSn);
			return mapper.findPermissionListViaRole(map);
		} finally {
			session.close();
		}
	}
	
	public List<Group> findPermissionGroupListViaRole(Long roleSn){
		List<Group> groupList = new ArrayList<Group>();
		if(null==roleSn){
			return groupList;
		}
		SqlSession session = MyBatisUtil.openSession();
		
		try {
			PermissionMapper mapper = session.getMapper(PermissionMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(Permission.COL_ROLE_SN,roleSn);
			List<Map<String,Object>> permissionMapList= mapper.findPermissionMapListViaRole(map);
			Group newGroup = null;
			for (Map<String, Object> map2 : permissionMapList) {
				newGroup = toGroup(map2);
				groupList.add(newGroup);
			}
		} finally {
			session.close();
		}
		return groupList;
	}
	
	private Group toGroup(Map<String,Object> permissionMap){
		Group newGroup = new Group();
		if(null!=permissionMap){
			newGroup.setCode(AppStrUtils.toEmpty(permissionMap.get(PageGroup.DB_NAME_CODE)));
			newGroup.setCreate(AppNumUtils.toIntValue(permissionMap.get(Permission.DB_ADD)));
			newGroup.setDelete(AppNumUtils.toIntValue(permissionMap.get(Permission.DB_DELETE)));
			newGroup.setModify(AppNumUtils.toIntValue(permissionMap.get(Permission.DB_MODIFY)));
			newGroup.setPrint(AppNumUtils.toIntValue(permissionMap.get(Permission.DB_PRINT)));
			newGroup.setView(AppNumUtils.toIntValue(permissionMap.get(Permission.DB_VIEW)));
		}
		return newGroup;
	}
	
	//uri: format: /reservation/list....
	public boolean hasPermission(Long roleSn, String uri){
		if(null==roleSn){
			return false;
		}
		if(StringUtils.isEmpty(uri)){
			return false;
		}
		String[] actions = uri.split("/");
		if(actions.length<3){
			return false;
		}
		Permission permission = findPermission(roleSn,actions[1],actions[2]);
		if(null==permission){
			return false;
		}
		return true;
	}
	
	
	public Permission findPermission(Long roleSn,String pageGroupCode,String type){
		Permission permission = null;
		if(!Permission.COL_P_ADD.equals(type) && !Permission.COL_P_DELETE.equals(type) && 
				!Permission.COL_P_LIST.equals(type) && !Permission.COL_P_UPDATE.equals(type) && !Permission.COL_P_VIEW.equals(type)){
			return null;
		}
		SqlSession session = MyBatisUtil.openSession();
		try {
			PermissionMapper mapper = session.getMapper(PermissionMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(Permission.COL_ROLE_SN,roleSn);
			map.put(PageGroup.COL_NAME_CODE,pageGroupCode);
			map.put(Permission.COL_TYPE,type);
			permission= mapper.findPermissionViaNameCode(map);
		} finally {
			session.close();
		}
		return permission;
	}

}
