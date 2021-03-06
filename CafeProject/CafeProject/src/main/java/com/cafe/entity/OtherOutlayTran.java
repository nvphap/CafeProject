package com.cafe.entity;

// Generated May 22, 2015 3:06:46 PM by Hibernate Tools 4.3.1

import java.sql.Timestamp;
import java.util.Date;


/**
 * Role generated by hbm2java
 */
/**
* The login action.
*
* @author  	phap.nguyen
* @version 	1.0
* @since   	2015-11-10 
* @update   
*/
@SuppressWarnings("serial")
public class OtherOutlayTran implements java.io.Serializable {
	public final static String COL_CAFE_SHOP_SN="cafeShopSn";
	public final static String COL_START_TIME="startTime";
	public final static String COL_END_TIME="endTime";
	public final static String COL_SN="sn";
	public final static String COL_OTHER_OUTLAY_SN="otherOutlaySn";
	public final static String COL_NAME_SEARCH="nameSearch";
	
	public final static String DB_SN="SN";
	public final static String DB_TOTAL_PRICE="TOTAL_PRICE";
	public final static String DB_OTHER_OUTLAY_SN="OTHER_OUTLAY_SN";
	public final static String DB_NUM_OF_OUTLAY="NUM_OF_OUTLAY";
	public final static String DB_TIME_TRANSACTION="TIME_TRANSACTION";
	public final static String DB_MEMO="MEMO";
	
	private Long sn;
	private Long cafeShopSn;
	private String memo;
	private int totalPrice;
	private Long otherOutlaySn;
	private int numOfOutlay;
	private Date timeTransaction;
	private Long createStaffSn;
	private Long lastUpdateStaffSn;
	private Timestamp lastUpdate;
	
	public OtherOutlayTran() {
	}

	public Long getSn() {
		return this.sn;
	}

	public void setSn(Long sn) {
		this.sn = sn;
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Long getOtherOutlaySn() {
		return otherOutlaySn;
	}

	public void setOtherOutlaySn(Long otherOutlaySn) {
		this.otherOutlaySn = otherOutlaySn;
	}

	public int getNumOfOutlay() {
		return numOfOutlay;
	}

	public void setNumOfOutlay(int numOfOutlay) {
		this.numOfOutlay = numOfOutlay;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int toatlPrice) {
		this.totalPrice = toatlPrice;
	}

	public Date getTimeTransaction() {
		return timeTransaction;
	}

	public void setTimeTransaction(Date timeTransaction) {
		this.timeTransaction = timeTransaction;
	}

	public Long getCreateStaffSn() {
		return createStaffSn;
	}

	public void setCreateStaffSn(Long createStaffSn) {
		this.createStaffSn = createStaffSn;
	}

	public Long getLastUpdateStaffSn() {
		return lastUpdateStaffSn;
	}

	public void setLastUpdateStaffSn(Long lastUpdateStaffSn) {
		this.lastUpdateStaffSn = lastUpdateStaffSn;
	}

	public Long getCafeShopSn() {
		return cafeShopSn;
	}

	public void setCafeShopSn(Long cafeShopSn) {
		this.cafeShopSn = cafeShopSn;
	}
}
