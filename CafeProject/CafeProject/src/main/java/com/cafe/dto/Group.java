package com.cafe.dto;

import org.apache.commons.lang3.StringUtils;

// Generated May 22, 2015 3:06:46 PM by Hibernate Tools 4.3.1


/**
 * Page generated by hbm2java
 */
/**
* The login action.
*
* @author  	phap.nguyen
* @version 	1.0
* @since   	2015-10-29 
* @update   
*/
@SuppressWarnings("serial")
public class Group implements java.io.Serializable {	
	private String code=StringUtils.EMPTY;
	private int modify=0;
	private int view=0;
	private int delete=0;
	private int create=0;
	private int print=0;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getModify() {
		return modify;
	}
	public void setModify(int modify) {
		this.modify = modify;
	}
	public int getView() {
		return view;
	}
	public void setView(int view) {
		this.view = view;
	}
	public int getDelete() {
		return delete;
	}
	public void setDelete(int delete) {
		this.delete = delete;
	}
	public int getCreate() {
		return create;
	}
	public void setCreate(int create) {
		this.create = create;
	}
	public int getPrint() {
		return print;
	}
	public void setPrint(int print) {
		this.print = print;
	}
}
