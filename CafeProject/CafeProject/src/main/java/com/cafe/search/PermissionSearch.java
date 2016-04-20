package com.cafe.search;

import org.apache.commons.lang3.StringUtils;

import com.cafe.utils.Constant;


public class PermissionSearch implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2212178719405625166L;
	
	private int offset;
	
	private int limit;
	
	private String keyword;
	
	private Long roleSn;
	
	private String languageCode = Constant.LANG_VIETNAMESE_CODE;

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

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
		if(StringUtils.isEmpty(this.keyword)){
			this.keyword = null;
		}else{
			this.keyword = this.keyword.trim();
		}
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public Long getRoleSn() {
		return roleSn;
	}

	public void setRoleSn(Long roleSn) {
		this.roleSn = roleSn;
	}
}
