package com.cafe.form;

import java.math.BigDecimal;

import com.cafe.entity.Setting;
import com.cafe.utils.AppStrUtils;

/**
* The login action.
*
* @author  	dat,huynh
* @version 	1.0
* @since   	2015-07-07 
* @update   
*/
@SuppressWarnings("serial")
public class SettingForm implements java.io.Serializable {
	
	private Long cafeShopSn;
	private String commonNo;
	private String classNo;
	private String numdata1;
	private String numdata2;
	private String numdata3;
	private String chardata1;
	private String chardata2;
	private String chardata3;
	private String classname1;
	private String classname2;
	private String classname3;
	private String sort;
	private String copy;
	private String memo;
	private String status;
	private String lastUpdate;

	public SettingForm() {
	}

	public SettingForm(Setting setting) {
		if (null != setting) {
			setCafeShopSn(setting.getCafeShopSn());
			commonNo = setting.getCommonNo();
			classNo = setting.getClassNo();
			numdata1 = setting.getNumdata1()!=null?setting.getNumdata1().toString():AppStrUtils.EMPTY;
			numdata2 = setting.getNumdata2()!=null?setting.getNumdata2().toString():AppStrUtils.EMPTY;
			numdata3 = setting.getNumdata3()!=null?setting.getNumdata3().toString():AppStrUtils.EMPTY;
			chardata1 = setting.getChardata1()!=null?setting.getChardata1():AppStrUtils.EMPTY;
			chardata2 = setting.getChardata2()!=null?setting.getChardata2():AppStrUtils.EMPTY;
			chardata3 = setting.getChardata3()!=null?setting.getChardata3():AppStrUtils.EMPTY;
			classname1 = setting.getClassname1()!=null?setting.getClassname1():AppStrUtils.EMPTY;
			classname2 = setting.getClassname2()!=null?setting.getClassname2():AppStrUtils.EMPTY;
			classname3 = setting.getClassname3()!=null?setting.getClassname3():AppStrUtils.EMPTY;
			memo = setting.getMemo()!=null?setting.getMemo():AppStrUtils.EMPTY;
		}
	}
	
	public Setting toSetting() {
		Setting setting = new Setting();
		setting.setCafeShopSn(cafeShopSn);
		setting.setCommonNo(commonNo);
		setting.setClassNo(classNo);
		if (!AppStrUtils.isEmpty(numdata1)) {
			setting.setNumdata1(new BigDecimal(numdata1));
		}
		if (!AppStrUtils.isEmpty(numdata2)) {
			setting.setNumdata1(new BigDecimal(numdata2));
		}
		if (!AppStrUtils.isEmpty(numdata3)) {
			setting.setNumdata1(new BigDecimal(numdata3));
		}
		setting.setChardata1(chardata1);
		setting.setChardata2(chardata2);
		setting.setChardata3(chardata3);
		setting.setClassname1(classname1);
		setting.setClassname2(classname2);
		setting.setClassname3(classname3);
		setting.setMemo(memo);
		return setting;
	}
	
	public String getCopy() {
		return copy;
	}

	public void setCopy(String copy) {
		this.copy = copy;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getNumdata1() {
		return this.numdata1;
	}

	public void setNumdata1(String numdata1) {
		this.numdata1 = numdata1;
	}

	public String getNumdata2() {
		return this.numdata2;
	}

	public void setNumdata2(String numdata2) {
		this.numdata2 = numdata2;
	}

	public String getNumdata3() {
		return this.numdata3;
	}

	public void setNumdata3(String numdata3) {
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

	public String getSort() {
		return this.sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
	
	
}
