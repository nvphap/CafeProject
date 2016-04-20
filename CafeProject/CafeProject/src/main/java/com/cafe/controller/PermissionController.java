package com.cafe.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe.base.controller.BaseController;
import com.cafe.dto.LoginUser;
import com.cafe.entity.Role;
import com.cafe.entity.Setting;
import com.cafe.form.PermissionForm;
import com.cafe.search.PermissionSearch;
import com.cafe.service.interfaces.PermissionService;
import com.cafe.service.interfaces.SettingService;
import com.cafe.utils.AppNumUtils;
import com.cafe.utils.Constant;


/**
 * Handles requests for the application home page.
 */
/**
* The login action.
*
* @author  	phap.nguyen
* @version 	1.0
* @since   	2015-06-02 
* @update   
*/
@Controller
public class PermissionController extends BaseController{
	public final static String VIEW="view";
	public final static String MODIFY="modify";
	public final static String ADD="add";
	public final static String DELETE="delete";
	
	private static Logger LOG;
	
	static{
		LOG= Logger.getLogger(PermissionController.class);
	}
	
	@Autowired
	public void AccountsController(MessageSource messages) {
	   this.messages = messages;
	}
	
	@PostConstruct
	public void init(){
		//messageBundle = getBundle("permission");
	}
	
	@Autowired
	private PermissionService permissionSerivce;
	
	@Autowired
	private SettingService settingService;
	
	@RequestMapping(value = "/permission/list", method = RequestMethod.GET)
	public String initPermissionList( Model model,HttpSession session) {
		LOG.info("/clientadminPermission/list");
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info(loginUser.getLogInfo());
		model.addAttribute("roleList",createRoleList());
		return "permission/list";
	}
	
	private Map<Long,String> createRoleList(){
		List<Role> roleList = permissionSerivce.findAllRoleList(false);// find role for hospitalCode
		Map<Long,String> roleMap = new LinkedHashMap<Long,String>();
		for (Role role : roleList) {
			roleMap.put(role.getSn(),role.getName());
		}	
		return roleMap;
	}
	
	@RequestMapping(value = "/permission/list/ajaxGetClientPermissionList", method = RequestMethod.POST)
	public String ajaxGetPermissionList( Model model,HttpSession session,
			@RequestParam(value = "page", required = false) final String pageValue,
			@RequestParam(value = "roleSn", required = false) final String roleSnStr) {
		LOG.info("/permission/list/ajaxGetClientPermissionList");
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info(loginUser.getLogInfo());
		try {
			loadPermissionList(loginUser,model,pageValue, AppNumUtils.toLong(roleSnStr));
	    } catch (Exception ex) {
			LOG.error(getExceptionContent(ex));
		}
		return "permission/ajaxList";
	}
	
	private void loadPermissionList(LoginUser loginUser,Model model,String pageValue, Long roleSn){
		//Get setting info
		String commonNo = "0001";
		String classNo = "01";
		Setting setting = settingService.findSetting(loginUser.getCafeShopSn(),commonNo, classNo);
		int recordPerPage = setting.getNumdata1().intValue();
		
		//Paging input parameters
		PermissionSearch search = new PermissionSearch();
		search.setRoleSn(roleSn);
		int totalRecord=permissionSerivce.countPermissionList(search);//find all
		String extUrl = "&roleSn="+roleSn;
		paging(model, recordPerPage, totalRecord, pageValue, "/permission/list/ajaxGetClientPermissionList",extUrl);
    	//Get role info with limit record
		search.setLimit(limit);
		search.setOffset(offset);
		search.setLanguageCode(getLanguageCode());
	    List<PermissionForm> permissionList = permissionSerivce.findPermissionFormList(search);
		model.addAttribute("permissionList",permissionList);
		model.addAttribute("roleSn",roleSn);
	}
	
	@RequestMapping(value = "/permission/list/ajaxUpdateClientPermission", method = RequestMethod.POST)
	public @ResponseBody String ajaxUpdatePermission( Model model,HttpSession session,
			@RequestParam(value = "permissionSn", required = false) final String permissionSnStr,
			@RequestParam(value = "type", required = false) final String typeStr,
			@RequestParam(value = "value", required = false) final String valueStr) {
		LOG.info("/permission/list/ajaxUpdateClientPermission");
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info(loginUser.getLogInfo());
		try {
			int count=permissionSerivce.updatePageGroupRolePermission(AppNumUtils.toLong(permissionSnStr),
					typeStr,AppNumUtils.toIntValue(valueStr));
			if(0==count){
				return "0";
			}
	    	return "1";
	    } catch (Exception ex) {
			LOG.error(getExceptionContent(ex));
			return "0";
		}
	}
}
