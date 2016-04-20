package com.cafe.search;



import org.apache.commons.lang3.StringUtils;

import com.cafe.utils.Constant;

@SuppressWarnings("serial")
public class FoodSearch implements java.io.Serializable{
	
	public final static String ORDER_FIELD_DEFAULT = "timeTransaction";
	public final static String ORDER_LAST_UPFATE="lastUpdate";
	public final static String ORDER_NAME="name";
	public final static String ORDER_PRICE="price";
	
	private Long cafeShopSn;
	
	private String keyword;

	private int offset;
	
	private int limit;
	
	private String sort = Constant.SORT_DESC;
	
	private String orderField = ORDER_FIELD_DEFAULT;
	

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
}
