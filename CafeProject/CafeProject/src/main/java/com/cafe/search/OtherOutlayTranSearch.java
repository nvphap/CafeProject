package com.cafe.search;


import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.cafe.utils.Constant;

@SuppressWarnings("serial")
public class OtherOutlayTranSearch implements java.io.Serializable{
	
	public String ORDER_FIELD_DEFAULT = "timeTransaction";
	
	private Long cafeShopSn;
	
	private String keyword;

	private int offset;
	
	private int limit;
	
	private Date startDate = null;
	
	private Date endDate = null;
	
	private String sort = Constant.SORT_DESC;
	
	private String orderField = ORDER_FIELD_DEFAULT;
	
	private Long otherOutlaySn = null;
	
	private String nameSearch = null;
	

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
			sort = Constant.SORT_DESC;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Long getCafeShopSn() {
		return cafeShopSn;
	}

	public void setCafeShopSn(Long cafeShopSn) {
		this.cafeShopSn = cafeShopSn;
	}

	public Long getOtherOutlaySn() {
		return otherOutlaySn;
	}

	public void setOtherOutlaySn(Long otherOutlaySn) {
		this.otherOutlaySn = otherOutlaySn;
	}

	public String getNameSearch() {
		return nameSearch;
	}

	public void setNameSearch(String nameSearch) {
		this.nameSearch = nameSearch;
	}
}
