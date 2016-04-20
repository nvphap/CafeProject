package com.cafe.form;

import com.cafe.entity.FoodUnit;
import com.cafe.utils.AppDateUtils;
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
public class FoodUnitForm implements java.io.Serializable {
	
	private Long sn;
	private String name;
	private String lastUpdate;
	
	public static void rejectBlank(FoodUnit foodUnit){
		if(null!=foodUnit){
			if(!AppStrUtils.isEmpty(foodUnit.getName())){
				foodUnit.setName(foodUnit.getName());
			}
		}
	}
	
	public FoodUnitForm(FoodUnit foodUnit){
		if(null!=foodUnit){
			sn = foodUnit.getSn();
			name = foodUnit.getName();
			lastUpdate = AppDateUtils.toYYYYMMDDHHMMStr(foodUnit.getLastUpdate());
		}
	}
	public void rejectBlank(){
		if(!AppStrUtils.isEmpty(name)){
			name = name.trim();
		}
	}
	
	public FoodUnitForm() {
	}

	public Long getSn() {
		return this.sn;
	}

	public void setSn(Long sn) {
		this.sn = sn;
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
}