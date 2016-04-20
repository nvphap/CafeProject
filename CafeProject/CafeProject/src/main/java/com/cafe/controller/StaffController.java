package com.cafe.controller;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe.base.controller.BaseController;
import com.cafe.dto.LoginUser;
import com.cafe.entity.CafeShop;
import com.cafe.entity.Role;
import com.cafe.entity.Setting;
import com.cafe.entity.Staff;
import com.cafe.form.ResetPassForm;
import com.cafe.form.StaffForm;
import com.cafe.search.StaffSearch;
import com.cafe.service.interfaces.AuthorityService;
import com.cafe.service.interfaces.PermissionService;
import com.cafe.service.interfaces.SettingService;
import com.cafe.service.interfaces.StoreService;
import com.cafe.utils.AppNumUtils;
import com.cafe.utils.AppStrUtils;
import com.cafe.utils.Constant;
import com.cafe.utils.Status;

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
public class StaffController extends BaseController{  
	
	private List<String> errors = new ArrayList<String>();
	private static Logger LOG;
	
	static{
		LOG= Logger.getLogger(StaffController.class);
	}
	
	@Autowired
	public void AccountsController(MessageSource messages) {
	   this.messages = messages;
	}

	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private SettingService settingService;
	
	@Autowired
	private PermissionService permissionSerivce;
	
	@Autowired
	private StoreService storeService;

	/**
	* The checkLogin action.
	*
	* @author  	phap.nguyen
	* @version 	1.0
	* @since   	2015-06-04 
	* @update   
	*/
	
	@PostConstruct
	public void init(){
		//messages = getBundle("staff");
	}
	private void processViewStaff(Model model,Long cafeShopSn,Long staffSn){
		StaffForm staff= authorityService.findStaffRole(cafeShopSn,staffSn);
		model.addAttribute("viewStaff",staff);
	}
	
	@RequestMapping(value = "/staff/delete/request", method = {RequestMethod.POST})
	public String deleteStaff(Model model, HttpServletRequest request, HttpSession session,
			@RequestParam(value = "staffSn", required = false) final String staffSnStr,
			@RequestParam(value = "page", required = false) final String pageValue) {
		LOG.info("/staff/delete/request");
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info(loginUser.getLogInfo());
		try{
			authorityService.updateStaffStatus(loginUser.getCafeShopSn(),AppNumUtils.toLong(staffSnStr),Status.STS_DELETE);
			
			Setting setting = settingService.findSetting(loginUser.getCafeShopSn(),"0001","01");
			StaffSearch search = new StaffSearch();
			int recordPerPage = setting.getNumdata1().intValue();
			int totalRecord = authorityService.countAllStaff(loginUser.getCafeShopSn());
			paging(model, recordPerPage, totalRecord, pageValue,"/staff/list/ajaxList",null);
			search.setCafeShopSn(loginUser.getCafeShopSn());
			search.setLimit(recordPerPage);
			search.setOffset(offset);
			List<StaffForm> staffList= authorityService.findLimitStaffRole(search);
			model.addAttribute("staffList",staffList);
			return "staff/ajaxList";
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "staff/ajaxList";
	}
	
	@RequestMapping(value = "/staff/view", method = {RequestMethod.GET})
	public String initViewStaff(@RequestParam("sn") Long staffSn, 
			Model model, HttpServletRequest request, HttpSession session) {
		LOG.info("/staff/view");
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info(loginUser.getLogInfo());
		try{
			processViewStaff(model,request,session,loginUser.getCafeShopSn(),staffSn);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "staff/view";
	}
	
	private void processViewStaff(Model model,HttpServletRequest request, HttpSession session,Long cafeShopSn,Long staffSn){
		StaffForm staff= authorityService.findStaffRole(cafeShopSn,staffSn);
		model.addAttribute("viewStaff",staff);
	}
	
	@RequestMapping(value = "/staff/add", method = RequestMethod.GET)
	public String initAddStaff( Model model,HttpSession session) {
		LOG.info("/staff/add");
		try{
			StaffForm staffFrom  = null;
			clearMessages(model);
			model.addAttribute("roleList",createRoleList(false));
			staffFrom = new StaffForm();
			model.addAttribute("staffForm",staffFrom);
		}catch(Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "staff/add";
	}
	
	private Map<Long,String> createRoleList(boolean includeAdmin){
		List<Role> roleList = permissionSerivce.findAllRoleList(includeAdmin);// find role for hospitalCode
		Map<Long,String> roleMap = new LinkedHashMap<Long,String>();
		for (Role role : roleList) {
			roleMap.put(role.getSn(),role.getName());
		}	
		return roleMap;
	}
	
	/**
	* The checkLogin action.
	*
	* @author  	phap.nguyen
	* @version 	1.0
	* @since   	2015-05-28 
	* @update   
	*/
	
	@RequestMapping(value = "/staff/add/request", method = RequestMethod.POST)
	public String registerStaff(@ModelAttribute StaffForm staffForm, Model model, HttpSession session) {
		LOG.info("/staff/add/request");
		try{
			staffForm.rejectBlank();
			if(!checkRegisterStaff(model,staffForm)){ 
				model.addAttribute("roleList",createRoleList(false));
				return "staff/add";
			}
			staffForm.setSn(null);
			authorityService.insertStaff(staffForm.toStaff());
		}catch(Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "redirect:/staff/list";
	}
	
	/**
	* The checkLogin action.
	*
	* @author  	phap.nguyen
	* @version 	1.0
	* @since   	2015-05-04 
	* @update   
	*/
	
	@RequestMapping(value = "/staff/list", method = RequestMethod.GET)
	public String initStaffList(Model model,HttpSession session) {
		LOG.info("/staff/list");
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		model.addAttribute("roleList",createRoleList(false));
		//model.addAttribute("cafeShopList",findAllCafeShopList());
		LOG.info(loginUser.getLogInfo());
		return "staff/list";
	}
	
	private List<CafeShop> findAllCafeShopList(){
		List<CafeShop> cafeShopList = storeService.findAllCafeShopList();
		CafeShop firstShop = new CafeShop();
		firstShop.setSn(0L);
		firstShop.setName(getString("common.selectAll"));
		cafeShopList.add(0,firstShop);
		return cafeShopList;
	}
	
	/**
	* The checkLogin action.
	*
	* @author  	phap.nguyen
	* @version 	1.0
	* @since   	2015-05-04 
	* @update   
	*/
	
	@RequestMapping(value = "/staff/list/ajaxList", method = RequestMethod.POST)
	public String ajaxList(Model model,HttpSession session,
			@RequestParam(value = "page", required = false) final String pageValue){
		LOG.info("/staff/list/ajaxList");
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info(loginUser.getLogInfo());
		try{
			Setting setting = settingService.findSetting(loginUser.getCafeShopSn(),"0001","01");
			StaffSearch search = new StaffSearch();
			int recordPerPage = setting.getNumdata1().intValue();
			int totalRecord = authorityService.countAllStaff(loginUser.getCafeShopSn());
			paging(model, recordPerPage, totalRecord, pageValue,"/staff/list/ajaxList",null);
			search.setCafeShopSn(loginUser.getCafeShopSn());
			search.setLimit(limit);
			search.setOffset(offset);
			List<StaffForm> staffList= authorityService.findLimitStaffRole(search);
			model.addAttribute("staffList",staffList);
		}catch(Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "staff/ajaxList";
	}
	
	
	private boolean checkRegisterStaff(Model model,StaffForm signupForm){
		errors.clear();
		if(AppStrUtils.isEmpty(signupForm.getUserId())){
			errors.add(getString("staff.userIdNotEmpty"));
		}
		
		if(AppStrUtils.isEmpty(signupForm.getName())){
			errors.add(getString("staff.nameIsNotEmpty"));
		}

		Staff findStaff=authorityService.findStaffViaUserId(signupForm.getUserId());
		if(null!=findStaff ){
			errors.add(getString("staff.useIdExist"));
		}
		
		if(AppStrUtils.isEmpty(signupForm.getPassword())){
			errors.add(getString("staff.passRequired"));
		}
		
		if(!signupForm.getPassword().equals(signupForm.getRetypePassword())){
			errors.add(getString("staff.passNotMatch"));
		}
		
		if(0==signupForm.getSelectRole()){
			errors.add(getString("staff.chooseRole"));
		}
		
		if(errors.size()>0){
			addMessage(model, errors);
			return false;
		}
		return true;
	}
	
	/**
	* The init update staff action
	*
	* @author  	phap.nguyen
	* @version 	1.0
	* @since   	2015-06-08 
	* @update   
	*/
	
	@RequestMapping(value = "/staff/update", method = {RequestMethod.GET})
	public String initUpdateStaff(@RequestParam("sn") Long sn, Model model, HttpServletRequest request, HttpSession session) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/staff/update");
		try{
			model.addAttribute("roleList",createRoleList(true));
			model.addAttribute("editRoleList",createRoleList(false));
			StaffForm updateStaff = authorityService.findStaffRole(loginUser.getCafeShopSn(),sn);
			updateStaff.setPassword(null);
			model.addAttribute("updateStaff", updateStaff);
		}catch(Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "staff/update";
	}
	
	
	@RequestMapping(value = "/staff/update/request", method = {RequestMethod.POST})
	public String updateStaff(@ModelAttribute("updateStaff") StaffForm updateStaff,Model model, HttpServletRequest request, HttpSession session) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/staff/update/request");
		updateStaff.rejectBlank();
		try{
			if(!checkUpdateStaff(model,updateStaff)){
				model.addAttribute("roleList",createRoleList(false));
				return "staff/update";   
			}
			Staff staff = updateStaff.toStaff();
			authorityService.updateStaff(staff);
			if(!StringUtils.isEmpty(updateStaff.getPassword())){//update password
				authorityService.updateStaffPassword(loginUser.getCafeShopSn(),updateStaff.getSn(),updateStaff.getPassword());
			}
		}catch(Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		
		return "redirect:/staff/list";
	}
	
	/**
	* The init update staff action
	*
	* @author  	phap.nguyen
	* @version 	1.0
	* @since   	2015-06-08 
	* @update   
	*/
	private boolean checkUpdateStaff(Model model,StaffForm signupForm){
		errors.clear();
		if(AppStrUtils.isEmpty(signupForm.getUserId())){
			errors.add(getString("staff.userIdNotEmpty"));
		}
		
		if(AppStrUtils.isEmpty(signupForm.getName())){
			errors.add(getString("staff.nameIsNotEmpty"));
		}
		Staff findStaff=authorityService.findStaffViaUserId(signupForm.getUserId());
		if(null!=findStaff && (!findStaff.getSn().equals(signupForm.getSn()))){
			errors.add(getString("staff.useIdExist"));
		}
		//update
		if(!StringUtils.isEmpty(signupForm.getPassword()) && !signupForm.getPassword().equals(signupForm.getRetypePassword())){
			errors.add(getString("staff.passNotMatch"));
		}else if(!StringUtils.isEmpty(signupForm.getPassword()) && !signupForm.getPassword().equals(signupForm.getRetypePassword())){
			errors.add(getString("staff.passNotMatch"));
		}
		
		if(0==signupForm.getSelectRole()){
			errors.add(getString("staff.chooseRole"));
		}
		if(errors.size()>0){
			addMessage(model, errors);
			return false;
		}
		return true;
	}
	
	
	@RequestMapping(value = "/profile/view/individual", method = {RequestMethod.GET})
	public String initViewIndividual(Model model, HttpServletRequest request, HttpSession session) {
		LOG.info("/profile/view/individual");
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info(loginUser.getLogInfo());
		try{
			processViewStaff(model,loginUser.getCafeShopSn(),loginUser.getSn());
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "profile/individual";
	}
	
	@RequestMapping(value = "/profile/update/updatePassword", method = {RequestMethod.GET})
	public String initUpdatePassword(Model model, HttpServletRequest request, HttpSession session) {
		LOG.info("/profile/update/updatePassword");
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info(loginUser.getLogInfo());
		try{
			ResetPassForm resetForm = new ResetPassForm();
			model.addAttribute("resetPassForm",resetForm);
			errors.clear();
		}catch(Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "profile/updatePassword";
	}
	
	@RequestMapping(value = "/profile/update/updatePassword/request", method = {RequestMethod.POST})
	public String updatePassword(@ModelAttribute ResetPassForm resetForm,Model model, HttpServletRequest request, HttpSession session) {
		LOG.info("/profile/update/updatePassword/request");
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info(loginUser.getLogInfo());
		try{
			if(!checkResetPass(loginUser,model,resetForm)){
				return "profile/updatePassword";
			}
			authorityService.updateStaffPassword(loginUser.getCafeShopSn(),loginUser.getSn(),resetForm.getNewPassword());
		}catch(Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "redirect:/profile/view/individual";
	}
	
	private boolean checkResetPass(LoginUser loginUser,Model model,ResetPassForm resetForm){
		errors.clear();
		Staff findStaff=authorityService.findStaff(loginUser.getUserId(),resetForm.getCurrentPass());
		if(null==findStaff){
			errors.add(getString("staff.curPasswordNotMatch"));
		}
		if(!resetForm.getNewPassword().equals(resetForm.getRetypeNewPassword())){
			errors.add(getString("staff.newPassNotMatch"));
		}
		if(errors.size()>0){
			addMessage(model, errors);
			return false;
		}
		return true;
	}
}
