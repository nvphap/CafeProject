package com.cafe.controller;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.cafe.entity.FoodUnit;
import com.cafe.form.FoodUnitForm;
import com.cafe.service.interfaces.SettingService;
import com.cafe.service.interfaces.StoreService;
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
public class FoodUnitController extends BaseController{  
	
	private List<String> errors = new ArrayList<String>();
	private static Logger LOG;
	
	static{
		LOG= Logger.getLogger(FoodUnitController.class);
	}
	
	@Autowired
	public void AccountsController(MessageSource messages) {
	   this.messages = messages;
	}

	@Autowired
	private StoreService storeService;
	
	@Autowired
	private SettingService settingService;

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
	
	@RequestMapping(value = "/foodUnit/update", method = {RequestMethod.GET})
	public String initUpdateFoodUnit(@RequestParam("sn") Long sn,Model model, 
			HttpServletRequest request, HttpSession session) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/foodUnit/add");
		try{
			FoodUnit foodUnit = storeService.findFoodUnit(loginUser.getCafeShopSn(),sn);
			model.addAttribute("foodUnit",foodUnit);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "foodUnit/update";
	}
	
	@RequestMapping(value = "/foodUnit/update/request", method = {RequestMethod.POST})
	public String updateFoodUnit(@ModelAttribute FoodUnit foodUnit,Model model, HttpServletRequest request, HttpSession session) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/foodUnit/add/request");
		try{
			foodUnit.setCafeShopSn(loginUser.getCafeShopSn());
			FoodUnitForm.rejectBlank(foodUnit);
			storeService.updateFoodUnit(foodUnit);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "redirect:/foodUnit/list";
	}
	
	
	@RequestMapping(value = "/foodUnit/add", method = {RequestMethod.GET})
	public String initAddFoodUnit(Model model, HttpServletRequest request, HttpSession session) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/foodUnit/add");
		try{
			FoodUnit foodUnit = new FoodUnit();
			model.addAttribute("foodUnit",foodUnit);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "foodUnit/add";
	}
	

	@RequestMapping(value = "/foodUnit/add/request", method = {RequestMethod.POST})
	public String addNewFoodUnit(@ModelAttribute FoodUnit foodUnit,Model model, HttpServletRequest request, HttpSession session) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/foodUnit/add/request");
		try{
			FoodUnitForm.rejectBlank(foodUnit);
			storeService.insertFoodUnit(loginUser.getCafeShopSn(),foodUnit.getName());
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "redirect:/foodUnit/list";
	}
	
	@RequestMapping(value = "/foodUnit/list", method = {RequestMethod.GET})
	public String initFoodUnitList(Model model, HttpServletRequest request, HttpSession session) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/foodUnit/list");
		try{
			
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "foodUnit/list";
	}
	
	@RequestMapping(value = "/foodUnit/list/ajaxList", method = {RequestMethod.POST})
	public String initFoodUnitAjaxList(Model model, HttpServletRequest request, HttpSession session) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/foodUnit/list/ajaxList");
		try{
			List<FoodUnitForm> foodUnitList = storeService.findAllFoodUnitList(loginUser.getCafeShopSn());
			model.addAttribute("foodUnitList",foodUnitList);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "foodUnit/ajaxList";
	}
}
