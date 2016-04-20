/**
\* The LoginController is using for user login.
*
* @author  	DoanTT
* 			DatHQ
* @version 	1.0
* @since   	2015-05-04 
* @update   2015-05-06
*/
package com.cafe.controller;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cafe.base.controller.BaseController;
import com.cafe.dto.Group;
import com.cafe.dto.LoginUser;
import com.cafe.dto.PermissionGroup;
import com.cafe.entity.PageGroup;
import com.cafe.entity.Role;
import com.cafe.entity.Setting;
import com.cafe.form.LoginForm;
import com.cafe.service.interfaces.AuthorityService;
import com.cafe.service.interfaces.PermissionService;
import com.cafe.service.interfaces.SettingService;
import com.cafe.utils.Constant;
/**
* The login action.
*
* @author  	phap.nguyen
* @version 	1.0
* @since   	2015-05-27 
* @update   
*/
@Controller
public class AuthorityController extends BaseController { 
	@Autowired
	private AuthorityService authorityService;
	@Autowired
	private SettingService settingService;
	@Autowired
	private PermissionService permissionService;
	
	private static Logger LOG;
	
	static{
		LOG= Logger.getLogger(AuthorityController.class);
	}
	
	@Autowired
	public void AccountsController(MessageSource messages) {
	   this.messages = messages;
	}
	
	@PostConstruct
	public void init() {
		//messageBundle = getBundle("authority"); 
	}
	/**
	* The login action.
	*
	* @author  	phap.nguyen
	* @version 	1.0
	* @since   	2015-05-27 
	* @update   
	*/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("index"); 
		return mav;
	}
	
	/**
	* The login action.
	*
	* @author  	phap.nguyen
	* @version 	1.0
	* @since   	2015-06-04 
	* @update   
	*/
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		LOG.info("/");
		return "redirect:/login";
	}
	
	/**
	* The login action.
	*
	* @author  	phapnv
	* @version 	1.0
	* @since   	2015-06-05 
	* @update   
	*/
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String initLogin(HttpServletRequest request, Model model,HttpSession session) {
		LOG.info("/login");
		clearMessages(model);
		return "login";
	}

	/**
	* The logout action
	*
	* @author  	phapnv
	* @version 	1.0
	* @since   	2015-06-05 
	* @update   
	*/
	@RequestMapping(value = "/logout/request", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, Model model,HttpSession session,HttpServletResponse response) {
		//clear session and cookie if user logout. Redirect to login page after that
		LOG.info("/logout/request");
		if(null!=session){
			LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
			if(null!=loginUser){
				LOG.info(loginUser.getLogInfo());
				try{
					session.setAttribute(Constant.STATUS,Constant.STS_LOGOUT);
					Cookie[] cookies = request.getCookies();
					for (Cookie cookie : cookies) {
						if(Constant.COOKIE_USER.equals(cookie.getName())){
							cookie.setMaxAge(1);
							response.addCookie(cookie);
						}else if(Constant.COOKIE_PASS.equals(cookie.getName())){
							cookie.setMaxAge(1);
							response.addCookie(cookie);
						}
					}
					session.removeAttribute(Constant.LOGIN_USER);//remove session contain information of login user
					session.removeAttribute(Constant.AUTO_LOGIN);//remove content of auto login flag
				}catch(Exception ex){
					LOG.error(getExceptionContent(ex));
				}
			}
		}
		return "redirect:/login";
	}
	
	/**
	* The checkLogin action.
	*
	* @author  	phap.nguyen
	* @version 	1.0
	 * @throws IOException 
	* @since   	2015-05-04 
	* @update   
	*/
	
	private boolean autoLogin(HttpServletRequest request,HttpSession session){
		session.removeAttribute(Constant.LOGIN_USER);
    	Cookie[] cookies = request.getCookies();
    	String userId = null;
    	String password=null;
		for (Cookie cookie : cookies) {
			if(Constant.COOKIE_USER.equals(cookie.getName())){
				userId = cookie.getValue();
			}else if(Constant.COOKIE_PASS.equals(cookie.getName())){
				password = cookie.getValue();
			}
		}//in case login automatic, do not set cookie again, and do not set message error if login failed
		if(null!=userId && null!=password){
			LoginUser loginUser = authorityService.login(userId, password);
			request.getSession().setAttribute(Constant.LOGIN_USER,loginUser);
			if(LoginUser.STS_OK==loginUser.getLoginStatus()){
				session.setAttribute(Constant.STATUS,Constant.STS_LOGIN);
				return true;
			}else{// login not ok
				return false;
			}
		}
		return false;
    }
	
	@RequestMapping(value = "/login/auto", method = RequestMethod.GET)
	public void autoLogin(HttpSession session,HttpServletRequest request,HttpServletResponse response) throws IOException{
		LOG.info("/login/auto");
		try{
			if(null!=session.getAttribute(Constant.PRE_PAGE)){//just for filter redirect
				if(true==autoLogin(request,session)){
					session.setAttribute(Constant.LANGUAGE_CODE,getLanguageCode());
					String currentPage =(String)session.getAttribute(Constant.PRE_PAGE);
					session.removeAttribute(Constant.PRE_PAGE);
					response.sendRedirect(currentPage);
					return;
				}else{//if auto login failed --> set flag is try to log and do not need auto login again.
					session.setAttribute(Constant.AUTO_LOGIN,Constant.AUTO_LOGIN);// try to auto in the past
				}
			}
			String url=request.getRequestURL().toString();
	        String uri=request.getRequestURI();
	        String nextUrl = url.replace(uri,StringUtils.EMPTY);
	        /*nextUrl +=Constant.CONTEXT_PATH;*/
	        nextUrl +=request.getContextPath();
	        nextUrl+="/login";
	        response.sendRedirect(nextUrl);
		}catch(Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		
	}
	
	/**
	* The checkLogin action.
	*
	* @author  	phap.nguyen
	* @version 	1.0
	* @since   	2015-05-04 
	* @update   
	*/
	
	@RequestMapping(value = "/login/request", method = RequestMethod.POST)
	public String checkLogin(@ModelAttribute LoginForm loginForm, Model model, HttpSession session,HttpServletRequest request,HttpServletResponse response) {
		LOG.info("/login/request");
		try {
			session.setAttribute(Constant.LOGIN_USER,null);
			LoginUser loginUser = authorityService.login(loginForm.getUserId(),loginForm.getPassword());
			loginUser.setRememberMe(loginForm.getRememberMe());
			session.setAttribute(Constant.LOGIN_USER,loginUser);
			if(LoginUser.STS_OK==loginUser.getLoginStatus()){
				LOG.info(loginUser.getLogInfo());
				session.setAttribute(Constant.LANGUAGE_CODE,getLanguageCode());
				session.setAttribute(Constant.STATUS,Constant.STS_LOGIN);
				updatePermissionGroup(session,loginUser);
				if(loginUser.isRememberMe()){
					//save hossn, userId, pass in cookie.
					Setting setting = settingService.findSetting(loginUser.getCafeShopSn(),"0001","01");
					Cookie userCookie = new Cookie(Constant.COOKIE_USER,loginUser.getUserId());
					userCookie.setMaxAge(setting.getNumdata1().intValue());
					response.addCookie(userCookie);
					//Cookie passCookie = new Cookie(Constant.COOKIE_PASS,loginUser.getPassword());
					Cookie passCookie = new Cookie(Constant.COOKIE_PASS,loginForm.getPassword());
					passCookie.setMaxAge(setting.getNumdata1().intValue());
					response.addCookie(passCookie);
				}else{// in case do not check, delete cookie
					session.removeAttribute(Constant.AUTO_LOGIN);//remove content of auto login flag
					Cookie[] cookies = request.getCookies();
					for (Cookie cookie : cookies) {
						if(Constant.COOKIE_USER.equals(cookie.getName())){
							cookie.setMaxAge(1);
							response.addCookie(cookie);
						}else if(Constant.COOKIE_PASS.equals(cookie.getName())){
							cookie.setMaxAge(1);
							response.addCookie(cookie);
						}
					}
				}
				if(Role.ROLE_ADMIN == loginUser.getRoleSn()){
					return "redirect:/permission/list";
				}
				return "redirect:/cafeOrder/list";
			}
			setErrorMessage(model,getString("login.loginIncorrect"));
			return "login";
			
		} catch (Exception ex) {
			LOG.error(getExceptionContent(ex));
			setErrorMessage(model,getString("login.loginIncorrect"));
			return "login";
		}
	}
	
	private void updatePermissionGroup(HttpSession session,LoginUser loginUser){
		List<Group> groupList = permissionService.findPermissionGroupListViaRole(loginUser.getRoleSn());
		PermissionGroup perGroup = new PermissionGroup();
		for (Group group : groupList) {
			if(PageGroup.CODE_FOOD.equals(group.getCode())){
				perGroup.setFood(group);
			}else if(PageGroup.CODE_FOOD_UNIT.equals(group.getCode())){
				perGroup.setFoodUnit(group);
			}else if(PageGroup.CODE_PERMISSION.equals(group.getCode())){
				perGroup.setPermission(group);
			}else if(PageGroup.CODE_CAFE_ORDER.equals(group.getCode())){
				perGroup.setCafeOrder(group);
			}else if(PageGroup.CODE_OTHER_OUTLAY.equals(group.getCode())){
				perGroup.setOtherOutlay(group);
			}else if(PageGroup.CODE_STATISTIC.equals(group.getCode())){
				perGroup.setStatistic(group);
			}else if(PageGroup.CODE_SETTING.equals(group.getCode())){
				perGroup.setSetting(group);
			}else if(PageGroup.CODE_PROFILE.equals(group.getCode())){
				perGroup.setProfile(group);
			}else if(PageGroup.CODE_TABLE.equals(group.getCode())){
				perGroup.setTable(group);
			}else if(PageGroup.CODE_STAFF.equals(group.getCode())){
				perGroup.setStaff(group);
			}
		}
		perGroup.updateToEmpty();
		session.setAttribute(Constant.PERMISSION,perGroup);
	}

}
