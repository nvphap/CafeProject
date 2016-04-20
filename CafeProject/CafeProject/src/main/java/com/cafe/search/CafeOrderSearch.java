package com.cafe.search;

import java.util.Date;




@SuppressWarnings("serial")
public class CafeOrderSearch implements java.io.Serializable{
	public final static String FIELD_ORDER_TIME="orderTime";
	public final static String FIELD_PAY_TIME="payTime";
	public final static String FIELD_TABLE_NAME="tableName";
	public final static String FIELD_NUM_OF_FOOD="numOfFood";
	public final static String FIELD_TOTAL_MONEY="totalMoney";
	public final static String FIELD_TIME_FRAME="timeFrame";
	public final static String FIELD_ORDER_DATE="orderDate";

	private int offset;
	
	private int limit;
	
	private Long cafeShopSn;
	
	private Long cafeTableSn;
	
	private Date startTime;
	
	private Date endTime;
	
	private String orderField;
	
	private String sort;

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

	public Long getCafeTableSn() {
		return cafeTableSn;
	}

	public void setCafeTableSn(Long cafeTableSn) {
		this.cafeTableSn = cafeTableSn;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Long getCafeShopSn() {
		return cafeShopSn;
	}

	public void setCafeShopSn(Long cafeShopSn) {
		this.cafeShopSn = cafeShopSn;
	}
}
