package com.cafe.dto;

// Generated May 22, 2015 3:06:46 PM by Hibernate Tools 4.3.1


import com.cafe.utils.AppStrUtils;


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
public class StatisticByTable implements java.io.Serializable {
	
	private int numOfFood;
	
	private String numOfFoodStr;
	
	private int totalProfit;
	
	private String totalProfitStr; 
	
	private String cafeTableName;
	
	private Long cafeTableSn;
	
	private int totalMoney;
	
	private String totalMoneyStr;
	
	public StatisticByTable() {
	}

	public int getNumOfFood() {
		return numOfFood;
	}

	public void setNumOfFood(int numOfFood) {
		this.numOfFood = numOfFood;
		numOfFoodStr = AppStrUtils.priceToString(numOfFood);
	}

	public int getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(int totalMoney) {
		this.totalMoney = totalMoney;
		totalMoneyStr = AppStrUtils.priceToString(totalMoney);
	}

	public String getNumOfFoodStr() {
		return numOfFoodStr;
	}

	public void setNumOfFoodStr(String numOfFoodStr) {
		this.numOfFoodStr = numOfFoodStr;
	}

	public String getTotalMoneyStr() {
		return totalMoneyStr;
	}

	public void setTotalMoneyStr(String totalMoneyStr) {
		this.totalMoneyStr = totalMoneyStr;
	}

	public String getCafeTableName() {
		return cafeTableName;
	}

	public void setCafeTableName(String cafeTableName) {
		this.cafeTableName = cafeTableName;
	}

	public Long getCafeTableSn() {
		return cafeTableSn;
	}

	public void setCafeTableSn(Long cafeTableSn) {
		this.cafeTableSn = cafeTableSn;
	}

	public int getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(int totalProfit) {
		this.totalProfit = totalProfit;
		totalProfitStr = AppStrUtils.priceToString(this.totalProfit);
	}

	public String getTotalProfitStr() {
		return totalProfitStr;
	}

	public void setTotalProfitStr(String totalProfitStr) {
		this.totalProfitStr = totalProfitStr;
	}
}
