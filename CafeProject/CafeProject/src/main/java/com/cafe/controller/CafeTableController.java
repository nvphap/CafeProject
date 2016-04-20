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
import com.cafe.entity.CafeTable;
import com.cafe.entity.TableGroup;
import com.cafe.form.CafeTableForm;
import com.cafe.service.interfaces.SettingService;
import com.cafe.service.interfaces.StoreService;
import com.cafe.utils.AppStrUtils;
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
public class CafeTableController extends BaseController{  
	public final static int ORDER_MAX=10;
	
	private List<String> errors = new ArrayList<String>();
	private static Logger LOG;
	
	static{
		LOG= Logger.getLogger(CafeTableController.class);
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
	
	@RequestMapping(value = "/table/update", method = {RequestMethod.GET})
	public String initUpdateCafeTable(@RequestParam("sn") Long sn,Model model, HttpServletRequest request, HttpSession session) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/table/update");
		try{
			CafeTable table = storeService.findCafeTable(loginUser.getCafeShopSn(),sn);
			CafeTableForm cafeTable = new CafeTableForm(table);
			model.addAttribute("cafeTable",cafeTable);
			
			List<TableGroup> tableGroupList = storeService.findAllTableGroupList(loginUser.getCafeShopSn());
			model.addAttribute("tableGroupList",tableGroupList);
			Long tableGroupSn = table.getTableGroupSn();
			model.addAttribute("selectedGroup",tableGroupSn);
			List<Long> posList =findOrderList();
			model.addAttribute("orderList",posList);
			model.addAttribute("selectedPos",table.getPosition());
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "table/update";
	}
	
	private List<Long> findOrderList(){
		List<Long> orderList = new ArrayList<Long>();
		for (int i=1;i<=ORDER_MAX;i++) {
			orderList.add(new Long(i));
		}
		return orderList;
	}
	
	@RequestMapping(value = "/table/update/request", method = {RequestMethod.POST})
	public String updateCafeTable(@ModelAttribute("cafeTable") CafeTableForm cafeTable,Model model, HttpServletRequest request, HttpSession session) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/table/update/request");
		try{
			cafeTable.setCafeShopSn(loginUser.getCafeShopSn());
			cafeTable.rejectBalank();
			if(false==checkInCaseInsertUpdateTable(model,cafeTable)){
				List<TableGroup> tableGroupList = storeService.findAllTableGroupList(loginUser.getCafeShopSn());
				model.addAttribute("tableGroupList",tableGroupList);
				List<Long> orderList =findOrderList();
				model.addAttribute("orderList",orderList);
				return "table/update";
			}
			storeService.updateCafeTable(cafeTable.toCafeTable());
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "redirect:/table/list";
	}
	
	private boolean checkInCaseInsertUpdateTable(Model model,CafeTableForm cafeTable){
		errors.clear();
		if(AppStrUtils.isEmpty(cafeTable.getName())){
			errors.add(getString("cafeTable.nameEmptyError"));
		}
		if(AppStrUtils.isEmpty(cafeTable.getLocation())){
			errors.add(getString("cafeTable.locationEmptyError"));
		}
		CafeTable findCafeTable = storeService.findCafeTableViaName(cafeTable.getCafeShopSn(),cafeTable.getName());
		if(null!=cafeTable.getSn()){//update
			if(null!=findCafeTable && !findCafeTable.getSn().equals(cafeTable.getSn())){
				errors.add(getString("cafeTable.duplicateName"));
			}	
		}else{//insert
			if(null!=findCafeTable){
				errors.add(getString("cafeTable.duplicateName"));
			}
		}
		if(errors.size()>0){
			model.addAttribute(ERROR_MSG,errors);
			return false;
		}
		return true;
	}
	
	@RequestMapping(value = "/table/add", method = {RequestMethod.GET})
	public String initAddCafeTable(Model model, HttpServletRequest request, HttpSession session) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/table/add");
		try{
			CafeTableForm cafeTable = new CafeTableForm();
			model.addAttribute("cafeTable",cafeTable);
			List<TableGroup> tableGroupList = storeService.findAllTableGroupList(loginUser.getCafeShopSn());
			model.addAttribute("tableGroupList",tableGroupList);
			List<Long> orderList =findOrderList();
			model.addAttribute("orderList",orderList);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "table/add";
	}
	
	@RequestMapping(value = "/table/add/request", method = {RequestMethod.POST})
	public String addNewCafeTable(@ModelAttribute("cafeTable") CafeTableForm tableForm,Model model, HttpServletRequest request, HttpSession session) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/table/add/request");
		try{
			tableForm.setCafeShopSn(loginUser.getCafeShopSn());
			tableForm.rejectBalank();
			if(false==checkInCaseInsertUpdateTable(model,tableForm)){
				List<TableGroup> tableGroupList = storeService.findAllTableGroupList(loginUser.getCafeShopSn());
				model.addAttribute("tableGroupList",tableGroupList);
				List<Long> orderList =findOrderList();
				model.addAttribute("orderList",orderList);
				return "/table/add";
			}
			storeService.insertCafeTable(tableForm.toCafeTable());
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "redirect:/table/list";
	}
	
	@RequestMapping(value = "/table/list", method = {RequestMethod.GET})
	public String initCafeTableList(Model model, HttpServletRequest request, HttpSession session) {
		LOG.info("/table/list");
		try{

		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "table/list";
	}
	
	@RequestMapping(value = "/table/list/ajaxList", method = {RequestMethod.POST})
	public String initAjaxGetCafeTableList(Model model, HttpServletRequest request, HttpSession session) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/table/list/ajaxList");
		try{
			List<CafeTableForm> cafeTableFormList = storeService.findAllCafeTableFormListOrderByIdx(loginUser.getCafeShopSn());
			model.addAttribute("cafeTableList",cafeTableFormList);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "table/ajaxList";
	}
	
	@RequestMapping(value = "/table/delete/request", method = {RequestMethod.POST})
	public String deleteCafeTable(@RequestParam("tableSn") Long sn,Model model, HttpServletRequest request, HttpSession session) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/table/delete/request");
		try{
			storeService.deleteCafeTableLogic(loginUser.getCafeShopSn(),sn);
			List<CafeTableForm> cafeTableFormList = storeService.findAllCafeTableFormList(loginUser.getCafeShopSn());
			model.addAttribute("cafeTableList",cafeTableFormList);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "table/ajaxList";
	}
}
