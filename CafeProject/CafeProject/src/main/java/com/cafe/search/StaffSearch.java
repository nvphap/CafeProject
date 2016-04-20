package com.cafe.search;


import org.apache.commons.lang3.StringUtils;

import com.cafe.utils.Constant;

@SuppressWarnings("serial")
public class StaffSearch implements java.io.Serializable{
	
	public String ORDER_FIELD_DEFAULT = "lastUpdate";
	
	private Long cafeShopSn;

	private int offset;
	
	private int limit;
	
	private String keySearch;
	
	private Long status;
	
	private	Long roleSn;
	
	private String sort = Constant.SORT_DESC;
	
	private String orderField = ORDER_FIELD_DEFAULT;
	
	private Long staffSn;
	

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getKeySearch() {
		return keySearch;
	}

	public void setKeySearch(String keySearch) {
		this.keySearch = keySearch;
		if(StringUtils.isEmpty(this.keySearch)){
			this.keySearch = null;
		}else{
			this.keySearch= this.keySearch.trim();
		}
	}

	/**
	 * @return the order
	 */
	public String getSort() {
		return sort;
	}

	/**
	 * @param sort the order to set
	 */
	public void setSort(String sort) {
		if(!StringUtils.isEmpty(sort)){
			this.sort = sort;
		}else{
			sort = Constant.SORT_ASC;
		}
	}

	/**
	 * @return the orderField
	 */
	public String getOrderField() {
		return orderField;
	}

	/**
	 * @param orderField the orderField to set
	 */
	public void setOrderField(String orderField) {
		if(StringUtils.isEmpty(orderField)){
			this.orderField = ORDER_FIELD_DEFAULT;
		}else{
			this.orderField = orderField;
		}
		
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getRoleSn() {
		return roleSn;
	}

	public void setRoleSn(Long roleSn) {
		this.roleSn = roleSn;
	}

	public Long getStaffSn() {
		return staffSn;
	}

	public void setStaffSn(Long staffSn) {
		this.staffSn = staffSn;
	}

	public Long getCafeShopSn() {
		return cafeShopSn;
	}

	public void setCafeShopSn(Long cafeShopSn) {
		this.cafeShopSn = cafeShopSn;
	}
}
