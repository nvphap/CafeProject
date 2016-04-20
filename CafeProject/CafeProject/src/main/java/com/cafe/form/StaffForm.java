package com.cafe.form;

import org.apache.commons.lang3.StringUtils;

import com.cafe.entity.Staff;

/**
* The login action.
*
* @author  	phap.nguyen
* @version 	1.0
* @since   	2015-05-27 
* @update   
*/
public class StaffForm implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6354662383304712465L;
	public final static String TRUE="true";
	public final static String FALSE="false";
	public final static String CHECKED="checked='checked'";
	
	private Long cafeShopSn;
	private String cafeShopName;
	private Long sn;
	private String userId;
	private String password;
	private String retypePassword;
	private String name;
	private Long selectRole;
	private String roleTitle;
	private String memo;
	private String lastUpdate;
	
	public void rejectBlank(){
		if(StringUtils.isEmpty(userId)){
			userId = userId.trim();
		}
		if(StringUtils.isEmpty(name)){
			name = name.trim();
		}
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRetypePassword() {
		return retypePassword;
	}
	public void setRetypePassword(String retypePassword) {
		this.retypePassword = retypePassword;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getSelectRole() {
		return selectRole;
	}
	public void setSelectRole(Long selectRole) {
		this.selectRole = selectRole;
	}
	
	public StaffForm(){
		
	}
	
	public Staff toStaff(){
		Staff newStaff = new Staff();
		newStaff.setSn(sn);
		newStaff.setUserId(userId);
		newStaff.setPassword(password);
		newStaff.setName(name);
		newStaff.setRoleSn(selectRole);
		newStaff.setMemo(memo);
		return newStaff;
	}
	
	public StaffForm(Staff staff){
		if(null!=staff){
			userId=staff.getUserId();
			password=staff.getPassword();
			name=staff.getName();
			selectRole=staff.getRoleSn();
			sn=staff.getSn();
			memo = staff.getMemo();
		}
	}
	
	public String getRoleTitle() {
		return roleTitle;
	}
	public void setRoleTitle(String roleTitle) {
		this.roleTitle = roleTitle;
	}
	
	public Long getSn() {
		return sn;
	}
	public void setSn(Long sn) {
		this.sn = sn;
	}


	public String getMemo() {
		return memo;
	}


	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}


	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Long getCafeShopSn() {
		return cafeShopSn;
	}

	public void setCafeShopSn(Long cafeShopSn) {
		this.cafeShopSn = cafeShopSn;
	}

	public String getCafeShopName() {
		return cafeShopName;
	}

	public void setCafeShopName(String cafeShopName) {
		this.cafeShopName = cafeShopName;
	}	
}
