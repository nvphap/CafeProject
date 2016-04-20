package com.cafe.service.interfaces;

import java.util.List;
import java.util.Map;

import com.cafe.dto.Group;
import com.cafe.entity.Permission;
import com.cafe.entity.Role;
import com.cafe.form.PermissionForm;
import com.cafe.search.PermissionSearch;
/**
* The login action.
*
* @author  	phap.nguyen
* @version 	1.0
* @since   	2015-05-27 
* @update   
*/
public interface PermissionService {
	public Role findRole(Long sn);
	public Permission findPermission(Long roleSn,Long pageGroupSn);
	public List<Permission> findPermissionListViaRole(Long roleSn);
	public List<Group> findPermissionGroupListViaRole(Long roleSn);
	public boolean hasPermission(Long roleSn, String uri);
	public Permission findPermission(Long roleSn,String pageGroupCode,String type);
	public List<Role> findAllRoleList(boolean includeAdmin);
	public int countPermissionList(PermissionSearch search);
	public List<PermissionForm> findPermissionFormList(PermissionSearch search);
	public List<Map<String,Object>> findPermissionMapList(PermissionSearch search);
	public int updatePageGroupRolePermission(Long permissionSn,String type, int value);
}
