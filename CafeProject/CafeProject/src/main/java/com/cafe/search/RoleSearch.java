package com.cafe.search;

import com.cafe.utils.Constant;

public class RoleSearch implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2212178719405625166L;

	private int offset;
	
	private int limit;
	
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

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}
}
