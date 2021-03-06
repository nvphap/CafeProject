package com.cafe.form;

import com.cafe.entity.Food;
import com.cafe.utils.AppStrUtils;

// Generated May 22, 2015 3:06:46 PM by Hibernate Tools 4.3.1



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
public class FoodForm implements java.io.Serializable {
	public final static int RECIPES_PUBLIC=1;
	public final static int RECIPES_PRIVATE=0;
	
	private Long sn;
	private String name;
	private String memo;
	private int price;
	private String priceStr;
	private int profit;
	private String profitStr;
	private Long foodUnitSn;
	private String foodUnitName;
	private Long foodGroupSn;
	private String foodGroupName;
	private String lastUpdate;
	
	public static void rejectBlank(Food food){
		if(null!=food){
			if(!AppStrUtils.isEmpty(food.getMemo())){
				food.setMemo(food.getMemo());
			}
			if(!AppStrUtils.isEmpty(food.getName())){
				food.setName(food.getName());
			}
		}
	}
	
	public FoodForm() {
	}

	public Long getSn() {
		return this.sn;
	}

	public void setSn(Long sn) {
		this.sn = sn;
	}
	
	public String getMemo() {
		return this.memo;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
		this.priceStr = AppStrUtils.priceToString(this.price);
	}

	public Long getFoodUnitSn() {
		return foodUnitSn;
	}

	public void setFoodUnitSn(Long foodUnitSn) {
		this.foodUnitSn = foodUnitSn;
	}

	public String getFoodUnitName() {
		return foodUnitName;
	}

	public void setFoodUnitName(String foodUnitName) {
		this.foodUnitName = foodUnitName;
	}

	public String getPriceStr() {
		return priceStr;
	}

	public void setPriceStr(String priceStr) {
		this.priceStr = priceStr;
	}

	public Long getFoodGroupSn() {
		return foodGroupSn;
	}

	public void setFoodGroupSn(Long foodGroupSn) {
		this.foodGroupSn = foodGroupSn;
	}

	public String getFoodGroupName() {
		return foodGroupName;
	}

	public void setFoodGroupName(String foodGroupName) {
		this.foodGroupName = foodGroupName;
	}

	public int getProfit() {
		return profit;
	}

	public void setProfit(int profit) {
		this.profit = profit;
		this.profitStr = AppStrUtils.priceToString(this.profit);
	}

	public String getProfitStr() {
		return profitStr;
	}

	public void setProfitStr(String profitStr) {
		this.profitStr = profitStr;
	}
}
