package com.cafe.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;

import com.cafe.dto.LoginUser;
import com.cafe.service.impl.PermissionServiceImpl;
import com.cafe.service.interfaces.PermissionService;
import com.cafe.utils.AppStrUtils;
import com.cafe.utils.Constant;
/**
* The login action.
*
* @author  	phap.nguyen
* @version 	1.0
* @since   	2015-05-27 
* @update   
*/
public class CustomFilter implements Filter{
	public final static String PAGE_NO_PERMISSION="/noPermission";
	public final static String AUTO="/auto";
	public final static String REQUEST="/request";
	public final static String BACK="/back";
	public final static String RESOURCES="/resources";
	public final static String CSS=".css";
	public final static String PAGE_LOGIN="/login";
	public final static String PAGE_LOGOUT="/logout";
	public final static String PAGE_ROOT="/";
	public final static String NOT_FILTER="";
	
	private static PermissionService permissionService;

    @Override
    public void destroy() {
        // Do nothing
    }
    
    public String getLanguageCode(){
		String lang=LocaleContextHolder.getLocale().getDisplayLanguage();
		if(Constant.LANG_ENGLISH.equals(lang)){
			return Constant.LANG_ENGLISH_CODE;
		}
		return Constant.LANG_VIETNAMESE_CODE;
	}
    
    private boolean isLoginStatus(HttpSession session,String url,String uri){
        if(null==session){
        	return false;
        }else if(null==session.getAttribute(Constant.LOGIN_USER)){
        	return false;
        }
    	LoginUser loginUser=(LoginUser)session.getAttribute(Constant.LOGIN_USER);
    	if(LoginUser.STS_FAILURE==loginUser.getLoginStatus()){
    		return false;
    	}
    	return true;
    }
    
    /**
     * rule for filter:do not filter
     * + Root "/"
     * + Login page:/login
     * + Auto login:login/auto
     * + Logout page:logout/request
     * + resource: resouces/*
     * - Step by step to check login status
     * + Check login status on session
     * + Check login status on cookie
     * If ok --> continue to render current page
     * If is not ok, redirect to login page 
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
        FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse)res;
        HttpSession session =request.getSession();
        String url=request.getRequestURL().toString();
        String uri=request.getRequestURI();
        String nextUrl = url.replace(uri,StringUtils.EMPTY);
        /*nextUrl +=Constant.CONTEXT_PATH;*/
        nextUrl +=request.getContextPath();
        boolean loginStatus = false;
        //if(isFilter(url,uri)){
        	//check status of user: in case exist status in session and value equal to logout, redirect to login page
            //in case status == login run code below
            //in case status is null, session timeout run code below
        	if(null!=session.getAttribute(Constant.STATUS)){//in this case may be user logout but response has not yet flushed
            	if(Constant.STS_LOGOUT.equals(session.getAttribute(Constant.STATUS).toString())){
            		response.sendRedirect(nextUrl+PAGE_LOGIN);//in case time out, redirect to login page
            		return;
            	}
            }
        	loginStatus=isLoginStatus(session,url,uri);// check login status on session
        	if(false==loginStatus){//auto login not exist in admin login page
        		//in case get login status from session failure, check login information from cookie
            	//loginStatus = autoLogin(request);//check cookie and if exits --> auto login to hospital system.
        		if(null==session.getAttribute(Constant.AUTO_LOGIN)){//this is first time to auto login
        			session.setAttribute(Constant.PRE_PAGE,getFullUrl(request));
            		nextUrl+="/login/auto";
            		response.sendRedirect(nextUrl);
            		return;
        		}
        		response.sendRedirect(nextUrl+PAGE_LOGIN);//in case time out, redirect to login page
        		return;
            }else{//in case login successful, continue check permission
            	LoginUser loginUser=(LoginUser)session.getAttribute(Constant.LOGIN_USER);
            	String uriChecked=uri.replace(request.getContextPath(),AppStrUtils.EMPTY);//first value will be replace
            	boolean permission = permissionService.hasPermission(loginUser.getRoleSn(),uriChecked);
            	if(false==permission){
            		response.sendRedirect(nextUrl+PAGE_NO_PERMISSION);
            		return;
            	}
            }
      //  }
        chain.doFilter(req, res);
    }
    
    private String getFullUrl(HttpServletRequest request){
    	if(!StringUtils.isEmpty(request.getQueryString())){
    		return request.getRequestURL().toString()+"?"+request.getQueryString();
    	}
    	return request.getRequestURL().toString();
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    	// Do nothing
    	if(null==permissionService){
    		permissionService = new PermissionServiceImpl();
    	}
    }

}