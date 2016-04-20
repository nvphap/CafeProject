package com.cafe.mapper;


import java.util.List;
import java.util.Map;

import com.cafe.entity.Role;

/**
* The login action.
*
* @author  	phap.nguyen
* @version 	1.0
* @since   	2015-05-27 
* @update   
*/
public interface RoleMapper {
	public Role findRole(Long roleSn);
	public List<Role> findAllRoleList(Map<String,Object> map);
}
