package com.cafe.entity;

// Generated May 22, 2015 3:06:46 PM by Hibernate Tools 4.3.1

import java.sql.Timestamp;


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
public class Role implements java.io.Serializable {
	public final static int ROLE_ADMIN=1;
	public final static int ROLE_MANAGER=2;
	public final static int ROLE_SUB_MANAGER=4;
	public final static int ROLE_STAFF=3;
	
	public final static String COL_SN="sn";
	public final static String COL_ADMIN="admin";
	
	public final static String DB_SN="SN";
	public final static String DB_NAME="NAME";
	public final static String DB_ROLE_NAME="ROLE_NAME";
	
	private Long sn;
	private String name;
	private String memo;
	private Timestamp lastUpdate;
	
	public Role() {
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

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
