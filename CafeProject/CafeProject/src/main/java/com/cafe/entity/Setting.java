package com.cafe.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.cafe.utils.Constant;

// Generated May 19, 2015 11:29:13 AM by Hibernate Tools 4.3.1

/**
 * Setting generated by hbm2java
 */
/**
* The login action.
*
* @author  	phap.nguyen
* @version 	1.0
* @since   	2015-10-11 
* @update   
*/
public class Setting implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2201851108177788751L;
	public final static String COL_CAFE_SHOP_SN="cafeShopSn";
	public final static String COL_COMMON_NO="commonNo";
	public final static String COL_CLASS_NO="classNo";
	
	private Long cafeShopSn;;
	private String commonNo;
	private String classNo;
	private BigDecimal numdata1;
	private BigDecimal numdata2;
	private BigDecimal numdata3;
	private String chardata1;
	private String chardata2;
	private String chardata3;
	private String classname1;
	private String classname2;
	private String classname3;
	private String memo;
	private Timestamp lastUpdate;

	public Setting() {
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public BigDecimal getNumdata1() {
		return this.numdata1;
	}

	public void setNumdata1(BigDecimal numdata1) {
		this.numdata1 = numdata1;
	}

	public BigDecimal getNumdata2() {
		return this.numdata2;
	}

	public void setNumdata2(BigDecimal numdata2) {
		this.numdata2 = numdata2;
	}

	public BigDecimal getNumdata3() {
		return this.numdata3;
	}

	public void setNumdata3(BigDecimal numdata3) {
		this.numdata3 = numdata3;
	}

	public String getChardata1() {
		return this.chardata1;
	}

	public void setChardata1(String chardata1) {
		this.chardata1 = chardata1;
	}

	public String getChardata2() {
		return this.chardata2;
	}

	public void setChardata2(String chardata2) {
		this.chardata2 = chardata2;
	}

	public String getChardata3() {
		return this.chardata3;
	}

	public void setChardata3(String chardata3) {
		this.chardata3 = chardata3;
	}

	public String getClassname1() {
		return this.classname1;
	}

	public void setClassname1(String classname1) {
		this.classname1 = classname1;
	}

	public String getClassname2() {
		return this.classname2;
	}

	public void setClassname2(String classname2) {
		this.classname2 = classname2;
	}

	public String getClassname3() {
		return this.classname3;
	}

	public void setClassname3(String classname3) {
		this.classname3 = classname3;
	}	

	public String getCommonNo() {
		return commonNo;
	}

	public void setCommonNo(String commonNo) {
		this.commonNo = commonNo;
	}

	public String getClassNo() {
		return classNo;
	}

	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public String getClassname(String languageCode) {
		if(Constant.LANG_ENGLISH_CODE.equals(languageCode)){
			return classname2;
		}
		return classname1;
	}

	public Long getCafeShopSn() {
		return cafeShopSn;
	}

	public void setCafeShopSn(Long cafeShopSn) {
		this.cafeShopSn = cafeShopSn;
	}

}
