package com.cafe.mapper;


import java.util.List;
import java.util.Map;

import com.cafe.entity.Permission;

/**
* The login action.
*
* @author  	phap.nguyen
* @version 	1.0
* @since   	2015-05-27 
* @update   
*/
public interface PermissionMapper {
	public Permission findPermission(Long permissionSn);
	public List<Map<String,Object>> findAdminPermissionMapList(Map<String,Object> map);
	public int countAdminPermissionList(Map<String,Object> map);
	public int updatePageGroupRolePermission(Map<String,Object> map);
	public Permission findPermissionViaRoleGroup(Map<String,Object> map);
	public Permission findPermissionViaNameCode(Map<String,Object> map);
	public List<Permission> findPermissionListViaHospitalSnRoleAdmin(Map<String,Object> map);
	public int deletePermissionViaPageGroupAndHospitalSn(Map<String,Object> map);
	public Permission findPermissionViaHospitalGroupRoleSn(Map<String,Object> map);
	public List<Permission> findPermissionListViaRole(Map<String,Object> map);
	public List<Map<String,Object>> findPermissionMapListViaRole(Map<String,Object> map);
}
