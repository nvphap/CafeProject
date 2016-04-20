     package com.cafe.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cafe.entity.PageGroup;

/**
* The login action.
*
* @author  	phap.nguyen
* @version 	1.0
* @since   	2015-07-14 
* @update   
*/
public interface PageGroupMapper {
	public PageGroup findPageGroup(Long pageGroupSn);
	public int deletePageGroup(Long pageGroupSn);
	public int updatePageGroup(PageGroup updatePageGroup);
	public int insertPageGroup(PageGroup newPageGroup);
	public List<PageGroup> findAllPageGroupList(Map<String,Object> map);
	public int countAllPageGroupList(Map<String,Object> map);
	public PageGroup findPageGroupViaNameCode(@Param("nameCode") String name);
	public int updatePageGroupStatus(Map<String,Object> map);
	public List<PageGroup> findAllPageGroup();
	public List<PageGroup> findAllPageGroupOfHospital(Map<String,Object> map);
}
