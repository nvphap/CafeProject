package com.cafe.form;

import com.cafe.entity.CafeTable;
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
public class CafeTableForm implements java.io.Serializable {
	private Long cafeShopSn;
	private Long sn;
	private String name;
	private String location;
	private int position;
	private Long tableGroupSn;
	private String tableGroupName;
	private int groupPos;
	private String lastUpdate;
	
	public void rejectBalank(){
		if(!AppStrUtils.isEmpty(name)){
			name = name.trim(); 
		}
		if(!AppStrUtils.isEmpty(location)){
			location = location.trim();
		}
	}
	
	public CafeTableForm(CafeTable cafeTable){
		if(null!=cafeTable){
			setCafeShopSn(cafeTable.getCafeShopSn());
			sn = cafeTable.getSn();
			name = cafeTable.getName();
			location = cafeTable.getLocation();
			lastUpdate = AppDateUtils.toYYYYMMDDHHMMStr(cafeTable.getLastUpdate());
		}
	}
	
	public CafeTable toCafeTable(){
		CafeTable cafeTable = new CafeTable();
		cafeTable.setCafeShopSn(cafeShopSn);
		cafeTable.setSn(sn);
		cafeTable.setName(name);
		cafeTable.setLocation(location);
		cafeTable.setPosition(position);
		cafeTable.setTableGroupSn(tableGroupSn);
		return cafeTable;
	}
	
	public CafeTableForm() {
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Long getTableGroupSn() {
		return tableGroupSn;
	}

	public void setTableGroupSn(Long tableGroupSn) {
		this.tableGroupSn = tableGroupSn;
	}

	public String getTableGroupName() {
		return tableGroupName;
	}

	public void setTableGroupName(String tableGroupName) {
		this.tableGroupName = tableGroupName;
	}

	public int getGroupPos() {
		return groupPos;
	}

	public void setGroupPos(int groupPos) {
		this.groupPos = groupPos;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Long getCafeShopSn() {
		return cafeShopSn;
	}

	public void setCafeShopSn(Long cafeShopSn) {
		this.cafeShopSn = cafeShopSn;
	}
}