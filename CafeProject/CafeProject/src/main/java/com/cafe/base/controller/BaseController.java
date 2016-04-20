/**
* The LoginController is using for user login.
*
* @author  	DoanTT
* 			DatHQ
* @version 	1.0
* @since   	2015-05-04 
* @update   2015-05-06
*/
package com.cafe.base.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;

import com.cafe.utils.AppNumUtils;
import com.cafe.utils.AppStrUtils;
import com.cafe.utils.Constant;
import com.cafe.utils.Paging;
import com.google.gson.Gson;

/**
* The login action.
*
* @author  	phap.nguyen
* @version 	1.0
* @since   	2015-05-27 
* @update   
*/
public abstract class BaseController {
	public final static String ERROR_MSG="errorMessage";
	public final static String INFO_MSG="infoMessage";
	public final static String BUNDLE_FOLDER="/properties/";
	protected ResourceBundle messageBundle;
	protected MessageSource messages;
	
	protected int recordPerPage;
	protected int totalRecord;
	protected int currentPage;
	protected int offset;
	protected int limit;
	protected int totalPage;
	
	public String getFullUrl(HttpServletRequest request){
	      String uri=request.getRequestURI();
	      return uri.replace(request.getContextPath(),StringUtils.EMPTY)+"?"+getAllParameter(request);
	}
	
	public String getAllParameter(HttpServletRequest request){
        @SuppressWarnings("unchecked")
		Enumeration<String> parameterNames = request.getParameterNames();
        StringBuffer params = new StringBuffer();
        String paramsStr = StringUtils.EMPTY;
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            params.append(paramName).append("=").append(paramValues[0]).append("&");
//            for (int i = 0; i < paramValues.length; i++) {
//                String paramValue = paramValues[i];
//            }
        }
        if(params.length()>0){
        	paramsStr = params.substring(0,params.length()-1);
        }
        return paramsStr;
	}
	
	public String getExceptionContent(Exception exception){
		 StringWriter sw = new StringWriter();
		 exception.printStackTrace(new PrintWriter(sw));
		 return sw.toString();
	}

	public String getLanguageCode(){
		String lang=LocaleContextHolder.getLocale().getDisplayLanguage();
		if(Constant.LANG_ENGLISH.equals(lang)){
			return Constant.LANG_ENGLISH_CODE;
		}
		return Constant.LANG_VIETNAMESE_CODE;
	}
	
	public ResourceBundle getBundle(String key) {
		String lang=LocaleContextHolder.getLocale().getDisplayLanguage();
		if(Constant.LANG_ENGLISH.equals(lang)){
			return ResourceBundle.getBundle(BUNDLE_FOLDER+key+"_en");
		}
		return ResourceBundle.getBundle(BUNDLE_FOLDER+key+"_ko_KR");
	}
	
	public ResourceBundle getBundle(String key,String lang){
		if(Constant.LANG_ENGLISH.equals(lang)){
			return ResourceBundle.getBundle(BUNDLE_FOLDER+key+"_en");
		}
		return ResourceBundle.getBundle(BUNDLE_FOLDER+key+"_ko_KR");
	}
	
	public void clearMessages(Model model){
		model.addAttribute(ERROR_MSG,AppStrUtils.EMPTY);
	}
	
	public void setErrorMessage(Model model,String message){
		if(null!=message){
			try {
				message = new String(message.getBytes(Constant.ISO),Constant.UTF8);
			} catch (UnsupportedEncodingException e) {
				
			}
		}
		model.addAttribute(ERROR_MSG, message);
	}
	
	public void setInfoMessage(Model model,String message){
		if(null!=message){
			try {
				message = new String(message.getBytes(Constant.ISO),Constant.UTF8);
			} catch (UnsupportedEncodingException e) {
				
			}
		}
		model.addAttribute(INFO_MSG, message);
	}
	
	public String getBundleString(ResourceBundle bundle,String key){
		if(null!=bundle && null!=key){
			try {
				String value = bundle.getString(key);
				return new String(value.getBytes(Constant.ISO),Constant.UTF8);
			} catch (UnsupportedEncodingException e) {
				return StringUtils.EMPTY;
			}
		}
		return StringUtils.EMPTY;
	}
	
	public String getBundleString(String key){
		if(null!=messageBundle && null!=key){
			try {
				String value =messageBundle.getString(key);
				return new String(value.getBytes(Constant.ISO),Constant.UTF8);
			} catch (UnsupportedEncodingException e) {
				return StringUtils.EMPTY;
			}
		}
		return StringUtils.EMPTY;
	}
	
	public String getString(String key){
		if(null!=messages && !AppStrUtils.isEmpty(key)){
			return messages.getMessage(key,null,LocaleContextHolder.getLocale());
		}
		return StringUtils.EMPTY;
	}
	
	public String getString(String key,Object []params){
		if(null!=messages && !AppStrUtils.isEmpty(key)){
			return messages.getMessage(key,params,LocaleContextHolder.getLocale());
		}
		return StringUtils.EMPTY;
	}
	
	public void addMessage(Model model,List<String> errors){
		StringBuffer messageBuffer= new StringBuffer();
		String message = null;
		for (int i=0;i< errors.size();i++) {
			message = errors.get(i);
			if(i==errors.size()-1){
				messageBuffer.append(message);
			}else{
				messageBuffer.append(message).append(AppStrUtils.NEW_LINE_HTML);
			}
		}
		if(messageBuffer.length()>0){
			model.addAttribute(ERROR_MSG,messageBuffer.toString());
		}
		
	}
	
	protected int getTotalPage(int totalRecord, int recordPerPage){
		int totalPage=0;
		if(totalRecord != 0){
			if(totalRecord % recordPerPage == 0 )
        	  totalPage = totalRecord / recordPerPage;
          else
        	  totalPage = (totalRecord / recordPerPage) + 1;
	    }
		return totalPage;
	}
	
	protected int getCurrentPage(String pageValue){
		int currentPage=AppNumUtils.toIntValue(pageValue);
		if(currentPage<1){
			currentPage=1;
		}
		return currentPage;
	}
	
	protected void paging(Model model,int recordPerPage, int totalRecord, String pageValue, String ajaxPageUrl){
		paging(model,recordPerPage,totalRecord,pageValue,ajaxPageUrl,null);
	}
	
	protected void paging(Model model,int recordPerPage, int totalRecord, String pageValue, String ajaxPageUrl, String urlExt){
		this.recordPerPage = recordPerPage;
		this.totalRecord = totalRecord;
		this.currentPage = getCurrentPage(pageValue);
		if((recordPerPage*(currentPage-1))==totalRecord){//in case is final record, down 1 unit of current page
			if(currentPage>1){
				currentPage-=1;
			}
		}
		this.offset = recordPerPage * (currentPage - 1);
		this.limit = recordPerPage;
		this.totalPage = getTotalPage(totalRecord, recordPerPage);
		model.addAttribute(Paging.TOTAL_PAGE, totalPage);
		model.addAttribute(Paging.TOTAL_RECORD, this.totalRecord);
    	model.addAttribute(Paging.CURRENT_PAGE, currentPage);
    	model.addAttribute(Paging.PAGE_URL, ajaxPageUrl);
    	if(!StringUtils.isEmpty(urlExt)){
    		model.addAttribute(Paging.PAGING_URL_EXT,urlExt);
    	}else{
    		model.addAttribute(Paging.PAGING_URL_EXT,StringUtils.EMPTY);
    	}
	}
	
	protected void paging2(Model model,int recordPerPage, int totalRecord, String pageValue, String ajaxPageUrl,String urlExt){
		this.recordPerPage = recordPerPage;
		this.totalRecord = totalRecord;
		this.currentPage = getCurrentPage(pageValue);
		if((recordPerPage*(currentPage-1))==totalRecord){//in case is final record, down 1 unit of current page
			if(currentPage>1){
				currentPage-=1;
			}
		}
		this.offset = recordPerPage * (currentPage - 1);
		this.limit = recordPerPage;
		this.totalPage = getTotalPage(totalRecord, recordPerPage);
		model.addAttribute(Paging.TOTAL_PAGE_2, totalPage);
		model.addAttribute(Paging.TOTAL_RECORD_2, this.totalRecord);
    	model.addAttribute(Paging.CURRENT_PAGE_2, currentPage);
    	model.addAttribute(Paging.PAGE_URL_2, ajaxPageUrl);
    	if(!StringUtils.isEmpty(urlExt)){
    		model.addAttribute(Paging.PAGING_URL_EXT2,urlExt);
    	}else{
    		model.addAttribute(Paging.PAGING_URL_EXT2,StringUtils.EMPTY);
    	}
	}
	
	protected void paging2(Model model,int recordPerPage, int totalRecord, String pageValue, String ajaxPageUrl){
		paging2(model,recordPerPage,totalRecord,pageValue,ajaxPageUrl,null);
	}
	
	protected void paging3(Model model,int recordPerPage, int totalRecord, String pageValue, String ajaxPageUrl,String urlExt){
		this.recordPerPage = recordPerPage;
		this.totalRecord = totalRecord;
		this.currentPage = getCurrentPage(pageValue);
		if((recordPerPage*(currentPage-1))==totalRecord){//in case is final record, down 1 unit of current page
			if(currentPage>1){
				currentPage-=1;
			}
		}
		this.offset = recordPerPage * (currentPage - 1);
		this.limit = recordPerPage;
		this.totalPage = getTotalPage(totalRecord, recordPerPage);
		model.addAttribute(Paging.TOTAL_PAGE_3, totalPage);
		model.addAttribute(Paging.TOTAL_RECORD_3, this.totalRecord);
    	model.addAttribute(Paging.CURRENT_PAGE_3, currentPage);
    	model.addAttribute(Paging.PAGE_URL_3, ajaxPageUrl);
    	if(!StringUtils.isEmpty(urlExt)){
    		model.addAttribute(Paging.PAGING_URL_EXT3,urlExt);
    	}else{
    		model.addAttribute(Paging.PAGING_URL_EXT3,StringUtils.EMPTY);
    	}
	}
	
	protected void paging3(Model model,int recordPerPage, int totalRecord, String pageValue, String ajaxPageUrl){
		paging3(model,recordPerPage,totalRecord,pageValue,ajaxPageUrl,null);
	}
	
	/**
	* Build json data
	*
	* @author	dat.huynh
	* @version 	1.0
	* @since   	2015-08-06 
	* @update   
	*/
	protected String buildJsonResponse(String status, String error, String message, Object response) {
		
		String json = null;
		Gson gson = new Gson();
		Map<String, Object> responseData = new HashMap<>();
		
		responseData.put(Constant.API_STATUS, status);
		responseData.put(Constant.API_ERROR, error);
		responseData.put(Constant.API_MESSAGE, message);
		responseData.put(Constant.API_RESPONSE, response);
		json = gson.toJson(responseData);
		
		return json;
	}
}
