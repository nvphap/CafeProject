package com.cafe.utils;

/**
 * The login action.
 *
 * @author phap.nguyen
 * @version 1.0
 * @since 2015-05-27
 * @update
 */
public interface Constant {
	public final static String PERMISSION="p";
	
	public final static String ADMIN="admin";
	
	public final static String JSON_EMPTY="[{}]";
	
	public final static int SUGGESTION_PAGE_SIZE=10;
	// 
	public final static String LOGIN_USER = "loginUser";
	// 
	public final static String COOKIE_USER = "HospitalUserId";
	// 
	public final static String COOKIE_PASS = "HospitalPass";
	
	public final static String STATUS="status";
	
	public final static String STS_LOGIN="login";
	
	public final static String STS_LOGOUT="logout";
	// 
	public final static String PRE_PAGE = "prePage";
	// 
	public final static String AUTO_LOGIN = "autoLogin";
	
	public final static String SQL_KEYWORD="keyword";
	
	public final static String TIME_CARE = "CARE";
	
	public final static String TIME_BREAK = "BREAK";

	// Number records per page
	public final static int RECORD_PER_PAGE = 15;

	public final static String ISO="ISO-8859-1";
	public final static String UTF8="UTF-8";
	
	public final static String SQL_LIMIT = "limit";
	public final static String SQL_OFFSET = "offset";
	public final static String SQL_SORT = "sort";
	public final static String SQL_ORDER_FIELD="orderField";
	public final static String SORT_ASC = "asc";
	public final static String SORT_DESC = "desc";
	public final static String SQL_MASTER_DATA = "masterData";
	public final static String MODEL_SORT="sort";
	public final static String MODEL_SORT_2="sort2";
	public final static String MODEL_SORT_3="sort3";
	public final static String MODEL_KEYWORD="keySearch";
	public final static String MODEL_KEYWORD_2="keySearch2";
	public final static String MODEL_KEYWORD_3="keySearch3";
	public final static String MODEL_ORDER_FIELD="orderField";
	public final static String MODEL_ORDER_FIELD_2="orderField2";
	public final static String MODEL_ORDER_FIELD_3="orderField3";
	
	public final static String LANGUAGE_CODE="languageCode";
	
	public final static String LANG_ENGLISH="English";
	public final static String LANG_VIETNAMESE="Vietnamese";
	public final static String LANG_ENGLISH_CODE="en";
	public final static String LANG_VIETNAMESE_CODE="vi";
	public final static String PLATFORM_MOBILE="mobile";
	public final static String PLATFORM_WEB="web";
	
	public final static int RES_STATUS_RESERVATION = 1;
	public final static int RES_STATUS_PROCESSES = 2;
	public final static int RES_STATUS_CANCEL = 3;
	public final static String DATE_PICKER="datePicker";

	public static final String HOSPITAL_SN = "HospitalSn: ";

	public final static String TIME_DEFAULT="---- ~ ----";
	
	public final static String URL_API_MOBILE = "/api/mobile";
	public final static String API_STATUS = "Status";
	public final static String API_RESPONSE = "Response";
	public final static String API_ERROR = "Error";
	public final static String API_MESSAGE = "Message";
	public final static String API_STATUS_SUCCESS = "1";
	public final static String API_STATUS_FAIL = "0";
	public final static String API_DATA = "Data";
	public final static String API_TOTAL = "Total";

	public static final String CONTENT_DISPOSITION = "Content-disposition";

	public static final String CHARSET_UTF_8 = "charset=utf-8";
	public static final String JSON_CHARSET_UTF_8 = "application/json; charset=UTF-8";
	public static final String JSON_EXT_CHARSET_UTF_8 = "*/*;charset=UTF-8";
	public static final String MULTIDATA_CHARSET_UTF_8 = "multipart/form-data; charset=UTF-8";
	public static final String MULTIDATA_EXT_CHARSET_UTF_8 = "*/*;charset=UTF-8";
	public static final String JSON_TEXT_HTML_UTF_8 = "text/html; charset=UTF-8";
	
	//There are 4 content type: 1: Reservation, 2: Verify code, 3: Notice, 4:Counseling
	public final static int USER_TYPE_PATIENT=1;
	public final static int USER_TYPE_STAFF=2;
	public final static int USER_TYPE_GUEST=3;
	public final static int USER_TYPE_OTHER=4;
	
}
