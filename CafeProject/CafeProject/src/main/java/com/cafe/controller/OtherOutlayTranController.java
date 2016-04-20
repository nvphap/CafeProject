package com.cafe.controller;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe.base.controller.BaseController;
import com.cafe.dto.AutoItem;
import com.cafe.dto.Item;
import com.cafe.dto.LoginUser;
import com.cafe.entity.OtherOutlay;
import com.cafe.entity.Setting;
import com.cafe.form.OtherOutlayTranForm;
import com.cafe.search.OtherOutlayTranSearch;
import com.cafe.service.interfaces.SettingService;
import com.cafe.service.interfaces.StoreService;
import com.cafe.utils.AppDateUtils;
import com.cafe.utils.AppNumUtils;
import com.cafe.utils.AppStrUtils;
import com.cafe.utils.Constant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
public class OtherOutlayTranController extends BaseController{  
	
	public final static int MODE_LIST=1;
	public final static int MODE_STATISTIC=2;
	
	private List<String> errors = new ArrayList<String>();
	private static Logger LOG;
	
	static{
		LOG= Logger.getLogger(OtherOutlayTranController.class);
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
	
	@RequestMapping(value = "/otherOutlay/update", method = {RequestMethod.GET})
	public String initUpdateCafeTable(@RequestParam("sn") Long sn,Model model, HttpServletRequest request, HttpSession session) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/otherOutlay/update");
		try{
			OtherOutlayTranForm otherOutlayTranForm = storeService.findOtherOutlayTranForm(loginUser.getCafeShopSn(),sn);
			model.addAttribute("otherOutlayForm",otherOutlayTranForm);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "otherOutlay/update";
	}
	
	@RequestMapping(value = "/otherOutlay/update/request", method = {RequestMethod.POST})
	public String updateOtherOutlayTran(@ModelAttribute("otherOutlayForm") OtherOutlayTranForm otherOutlayTranForm,Model model, HttpServletRequest request, HttpSession session) {
		LOG.info("/otherOutlay/update/request");
		try{
			LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
			otherOutlayTranForm.setCafeShopSn(loginUser.getCafeShopSn());
			otherOutlayTranForm.rejectBlank();
			if(null==otherOutlayTranForm.getOtherOutlaySn()){
				OtherOutlay findOtherOutlay = storeService.findOtherOutlayViaName(loginUser.getCafeShopSn(),otherOutlayTranForm.getOtherOutlayName());
				if(null!=findOtherOutlay){
					otherOutlayTranForm.setOtherOutlaySn(findOtherOutlay.getSn());
				}
			}
			if(false==checkInCaseInsertUpdateOtherOutlay(model,otherOutlayTranForm)){
				return "/otherOutlay/update";
			}
			if(null==otherOutlayTranForm.getOtherOutlaySn()){//new other outlay
				OtherOutlay newOtherOutlay = new OtherOutlay();
				newOtherOutlay.setCafeShopSn(loginUser.getCafeShopSn());
				newOtherOutlay.setName(otherOutlayTranForm.getOtherOutlayName());
				storeService.insertOtherOutlay(newOtherOutlay);
				otherOutlayTranForm.setOtherOutlaySn(newOtherOutlay.getSn());
			}
			otherOutlayTranForm.setLastUpdateStaffSn(loginUser.getSn());
			storeService.updateOtherOutlayTran(otherOutlayTranForm.toOtherOutlayTran());
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "redirect:/otherOutlay/list";
	}
	
	private boolean checkInCaseInsertUpdateOtherOutlay(Model model,OtherOutlayTranForm otherOutlayTran){
		errors.clear();
		if(AppStrUtils.isEmpty(otherOutlayTran.getOtherOutlayName())){
			errors.add(getString("otherOutlay.nameNotEmpty"));
		}
		if(otherOutlayTran.getNumOfOutlay()<1){
			errors.add(getString("otherOutlay.numOfOutlayZezo"));
		}
		if(otherOutlayTran.getTotalPrice()<1){
			errors.add(getString("otherOutlay.priceHigherZezo"));
		}
		/*if(null!=otherOutlayTran.getOtherOutlaySn()){
			OtherOutlay findOtherOutlay = storeService.findOtherOutlayViaName(otherOutlayTran.getOtherOutlayName());
			if(null!=findOtherOutlay){
				if(!findOtherOutlay.getSn().equals(otherOutlayTran.getOtherOutlaySn())){
					errors.add(getString("otherOutlay.duplicateName"));
				}
			}
		}*/
		
		if(errors.size()>0){
			model.addAttribute(ERROR_MSG,errors);
			return false;
		}
		return true;
	}
	
	@RequestMapping(value = "/otherOutlay/add", method = {RequestMethod.GET})
	public String initAddOutlay(Model model, HttpServletRequest request, HttpSession session) {
		LOG.info("/otherOutlay/add");
		try{
			OtherOutlayTranForm otherOutlayForm = new OtherOutlayTranForm();
			otherOutlayForm.setNumOfOutlay(1);
			Date now = Calendar.getInstance().getTime();
			model.addAttribute("timeTransaction",AppDateUtils.toYYYYMMDDHHMMStr(now));
			model.addAttribute("otherOutlayForm",otherOutlayForm);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "otherOutlay/add";
	}
	
	@RequestMapping(value = "/otherOutlay/add/request", method = {RequestMethod.POST})
	public String addNewCafeTable(@ModelAttribute("otherOutlayForm") OtherOutlayTranForm otherOutlayTranForm,Model model, HttpServletRequest request, HttpSession session){
		LOG.info("/otherOutlay/add/request");
		try{
			LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
			otherOutlayTranForm.setCafeShopSn(loginUser.getCafeShopSn());
			otherOutlayTranForm.rejectBlank();
			if(null==otherOutlayTranForm.getOtherOutlaySn()){
				OtherOutlay findOtherOutlay = storeService.findOtherOutlayViaName(loginUser.getCafeShopSn(),otherOutlayTranForm.getOtherOutlayName());
				if(null!=findOtherOutlay){
					otherOutlayTranForm.setOtherOutlaySn(findOtherOutlay.getSn());
				}
			}
			if(false==checkInCaseInsertUpdateOtherOutlay(model,otherOutlayTranForm)){
				return "/otherOutlay/add";
			}
			if(null==otherOutlayTranForm.getOtherOutlaySn()){//new other outlay
				OtherOutlay newOtherOutlay = new OtherOutlay();
				newOtherOutlay.setCafeShopSn(loginUser.getCafeShopSn());
				newOtherOutlay.setName(otherOutlayTranForm.getOtherOutlayName());
				storeService.insertOtherOutlay(newOtherOutlay);
				otherOutlayTranForm.setOtherOutlaySn(newOtherOutlay.getSn());
			}
			otherOutlayTranForm.setCreateStaffSn(loginUser.getSn());
			otherOutlayTranForm.setLastUpdateStaffSn(loginUser.getSn());
			storeService.insertOtherOutlayTran(otherOutlayTranForm.toOtherOutlayTran());
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "redirect:/otherOutlay/list";
	}
	
	@RequestMapping(value = "/otherOutlay/list", method = {RequestMethod.GET})
	public String initOtherOutlayList(Model model, HttpServletRequest request, HttpSession session,
			@RequestParam(value = "startDate", required = false) final String startDateStr,
			@RequestParam(value = "endDate", required = false) final String endDateStr,
			@RequestParam(value = "mode", required = false) final String selectedModeStr) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/otherOutlay/list");
		try{
			List<Item> modeList = getModeList();
			model.addAttribute("modeList",modeList);
			int mode = AppNumUtils.toIntValue(selectedModeStr);
			model.addAttribute("selectedMode",mode);
			//Date currentDate= Calendar.getInstance().getTime();
			Date startDate= AppDateUtils.toYYMMDD(startDateStr);
			if(null== startDate){
				//startDate = AppDateUtils.getStartOfMonth(currentDate);
				startDate = getStartDate(loginUser.getCafeShopSn());
			}
			Date endDate = AppDateUtils.toYYMMDD(endDateStr);
			if(null==endDate){
				//endDate= AppDateUtils.getEndOfMonth(currentDate);
				endDate=getEndDate(loginUser.getCafeShopSn());
			}
			model.addAttribute("fromDate",AppDateUtils.toYYYYMMDDStr(startDate));
			model.addAttribute("toDate",AppDateUtils.toYYYYMMDDStr(endDate));
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "otherOutlay/list";
	}
	
	private List<Item> getModeList(){
		List<Item> modeList = new ArrayList<Item>();
		Item newItem = new Item(new Long(MODE_LIST),getString("statistic.list"));
		modeList.add(newItem);
		newItem = new Item(new Long(MODE_STATISTIC),getString("statistic.statistic"));
		modeList.add(newItem);
		return modeList;
	}
	
	private Date getStartDate(Long cafeShopSn){
		//default from 8th of this month to 8th of next month
		Setting setting = settingService.findSetting(cafeShopSn,"0002","01");
		Calendar cal = Calendar.getInstance();
		int currentDay= cal.get(Calendar.DAY_OF_MONTH);
		int currentMonth = cal.get(Calendar.MONTH);
		int currentYear = cal.get(Calendar.YEAR);
		int startDateSetting = setting.getNumdata1().intValue();
		cal.set(Calendar.DAY_OF_MONTH,startDateSetting);
		if(currentDay>=8 && currentDay<=31){
			
		}else{
			currentMonth=currentMonth-1;
			if(currentMonth<0){
				currentMonth = 12;
				cal.set(Calendar.YEAR,currentYear-1);
			}
			cal.set(Calendar.MONTH,currentMonth);
		}
		return cal.getTime();
	}
	
	private Date getEndDate(Long cafeShopSn){
		//default from 8th of this month to 8th of next month
		Setting setting = settingService.findSetting(cafeShopSn,"0002","01");
		Calendar cal = Calendar.getInstance();
		int currentDay= cal.get(Calendar.DAY_OF_MONTH);
		int currentMonth = cal.get(Calendar.MONTH);
		int currentYear = cal.get(Calendar.YEAR);
		int startDateSetting = setting.getNumdata1().intValue();
		cal.set(Calendar.DAY_OF_MONTH,startDateSetting-1);
		if(currentDay>=8 && currentDay<=31){
			currentMonth=currentMonth+1;
			if(currentMonth>11){
				currentMonth = 0;
				cal.set(Calendar.YEAR,currentYear+1);
			}
			cal.set(Calendar.MONTH,currentMonth);
		}else{
			
		}
		return cal.getTime();
	}
	
	@RequestMapping(value = "/otherOutlay/list/ajaxList", method = {RequestMethod.POST})
	public String initAjaxGetOtherOutlayList(Model model, HttpServletRequest request, HttpSession session,
			@RequestParam(value = "startDate", required = false) final String startDateStr,
			@RequestParam(value = "endDate", required = false) final String endDateStr,
			@RequestParam(value = "sort", required = false) final String sortStr,
			@RequestParam(value = "orderField", required = false) final String orderFieldStr,
			@RequestParam(value = "page", required = false) final String pageValue,
			@RequestParam(value = "mode", required = false) final String selectedModeStr,
			@RequestParam(value = "otherOutlaySn", required = false) final String otherOutlaySnStr,
			@RequestParam(value = "nameSearch", required = false) final String nameSearch){
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/otherOutlay/list/ajaxList");
		int mode = AppNumUtils.toIntValue(selectedModeStr);
		if(MODE_STATISTIC==mode){
			return processInCaseListStatistic(loginUser,model,startDateStr,endDateStr,pageValue,otherOutlaySnStr,nameSearch);
		}
		return processInCaseListMode(loginUser,model,startDateStr,endDateStr,sortStr,orderFieldStr,pageValue,otherOutlaySnStr,nameSearch);
	}
	
	private String processInCaseListStatistic(LoginUser loginUser,Model model, String startDateStr,String endDateStr,
			String pageValue,String otherOutlaySnStr,String nameSearch){
		try{
			OtherOutlayTranSearch search = new OtherOutlayTranSearch();
			search.setCafeShopSn(loginUser.getCafeShopSn());
			Date startDate = AppDateUtils.toYYMMDD(startDateStr);
			startDate = AppDateUtils.clearTime(startDate);
			Date endDate = AppDateUtils.toYYMMDD(endDateStr);
			endDate = AppDateUtils.setTimeToEndOfDay(endDate);
			search.setStartDate(startDate);
			search.setEndDate(endDate);
			search.setOtherOutlaySn(AppNumUtils.toLong(otherOutlaySnStr));
			search.setNameSearch(nameSearch);
			int totalRecord= storeService.countAllTypeOfOtherOutlayList(search);
			Setting setting = settingService.findSetting(loginUser.getCafeShopSn(),"0001","01");
			int recordPerPage = setting.getNumdata1().intValue();
			StringBuffer extUrl = new StringBuffer();
			extUrl.append("&startDate=").append(AppStrUtils.toEmpty(startDateStr)).append("&endDate=").append(endDateStr);
			extUrl.append("&mode=2").append("&otherOutlaySn=").append(otherOutlaySnStr).append("&nameSearch");
			extUrl.append(nameSearch);
			paging(model,recordPerPage,totalRecord, pageValue,"/otherOutlay/list/ajaxList",extUrl.toString());
			search.setOffset(offset);
			search.setLimit(limit);
			List<OtherOutlayTranForm> otherOutlayList = storeService.findLimitOtherOutlayTranStatisticList(search);
			model.addAttribute("otherOutlayList",otherOutlayList);
			int othersTotalMoney = storeService.calculateMoneyOfOtherOutlayTran(search);
			String totalMoney = AppStrUtils.priceToString(Double.valueOf(othersTotalMoney));
			model.addAttribute("othersTotalMoney",totalMoney);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "otherOutlay/ajaxStatistic";
	}
	
	private String processInCaseListMode(LoginUser loginUser,Model model, String startDateStr,String endDateStr,String sortStr,String orderFieldStr,
			String pageValue,String otherOutlaySnStr,String nameSearch){
		try{
			OtherOutlayTranSearch search = new OtherOutlayTranSearch();
			Date startDate = AppDateUtils.toYYMMDD(startDateStr);
			startDate = AppDateUtils.clearTime(startDate);
			Date endDate = AppDateUtils.toYYMMDD(endDateStr);
			endDate = AppDateUtils.setTimeToEndOfDay(endDate);
			search.setCafeShopSn(loginUser.getCafeShopSn());
			search.setStartDate(startDate);
			search.setEndDate(endDate);
			search.setSort(sortStr);
			search.setOrderField(orderFieldStr);
			search.setOtherOutlaySn(AppNumUtils.toLong(otherOutlaySnStr));
			search.setNameSearch(nameSearch);
			int totalRecord= storeService.countAllOtherOutlayTranList(search);
			Setting setting = settingService.findSetting(loginUser.getCafeShopSn(),"0001","01");
			int recordPerPage = setting.getNumdata1().intValue();
			StringBuffer extUrl = new StringBuffer();
			extUrl.append("&orderField=").append(search.getOrderField()).append("&sort=").append(search.getSort());
			extUrl.append("&startDate=").append(AppStrUtils.toEmpty(startDateStr)).append("&endDate=").append(endDateStr);
			extUrl.append("&mode=1").append("&otherOutlaySn=").append(otherOutlaySnStr).append("&nameSearch=").append(nameSearch);
			paging(model,recordPerPage,totalRecord, pageValue,"/otherOutlay/list/ajaxList",extUrl.toString());
			search.setOffset(offset);
			search.setLimit(limit);
			List<OtherOutlayTranForm> otherOutlayList = storeService.findLimitOtherOutlayTranList(search);
			model.addAttribute("otherOutlayList",otherOutlayList);
			int othersTotalMoney = storeService.calculateMoneyOfOtherOutlayTran(search);
			String totalMoney = AppStrUtils.priceToString(Double.valueOf(othersTotalMoney));
			model.addAttribute("othersTotalMoney",totalMoney);
			
			setSearchCondition(model,orderFieldStr,sortStr);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "otherOutlay/ajaxList";
	}
	
	private void setSearchCondition(Model model,String orderField, String sort){
		if(Constant.SORT_ASC.equals(sort)){
			model.addAttribute(Constant.MODEL_SORT,Constant.SORT_DESC);
		}else{
			model.addAttribute(Constant.MODEL_SORT,Constant.SORT_ASC);
		}
		model.addAttribute(Constant.MODEL_ORDER_FIELD,orderField);
	}
	
	@RequestMapping(value = "/otherOutlay/delete/request", method = {RequestMethod.POST})
	public String deleteOtherOutlayTran(@RequestParam("otherOutlaySn") Long sn,Model model, HttpServletRequest request, HttpSession session) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/otherOutlay/delete/request");
		try{
			storeService.deleteOtherOutlayTran(loginUser.getCafeShopSn(),sn);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "redirect:/otherOutlay/list";
	}
	
	/**
	* 
	* @author  	phap.nguyen
	* @version 	1.0
	* @since   	2015-06-12 
	* @update   
	*/
	
	@RequestMapping(value = "/suggestion/view/ajaxGetAutoOtherOutlayList", method = RequestMethod.POST)
	public @ResponseBody String[] ajaxGetAutoStaffList(Model model,HttpSession session,
			@RequestParam(value = "keySearch", required = false) final String keySearch,
			@RequestParam(value = "page", required = false) final String pageStr){
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/suggestion/view/ajaxGetAutoOtherOutlayList");
		String[] result = new String[2];
		int page = toPageValue(pageStr);
		try{
			//Setting setting = settingService.findSetting(hospitalSn,"0002","02");
			OtherOutlayTranSearch search = new OtherOutlayTranSearch();
			search.setCafeShopSn(loginUser.getCafeShopSn());
			search.setKeyword(keySearch);
			search.setOffset(((page-1)*Constant.SUGGESTION_PAGE_SIZE));
			search.setLimit(Constant.SUGGESTION_PAGE_SIZE);
			List<OtherOutlay> otherOutlayList= storeService.findLimitAllOtherOutlayList(search);
			String highLightKeyword=AppStrUtils.EMPTY;
			if(!AppStrUtils.isEmpty(keySearch)){
				highLightKeyword="<b class='autocomplete-highlight'>"+keySearch+"</b>";	
			}
			List<AutoItem> staffList = toAutoStaffList(otherOutlayList,keySearch,highLightKeyword);
			Gson gsone = new GsonBuilder().create();
			String jsonEvent=gsone.toJson(staffList,List.class);
			if(staffList.size()>0){//may be have more data
				page+=1;
			}
			result[0]=AppStrUtils.toEmpty(page);
			result[1]=jsonEvent;
			return result;
			//model.addAttribute("staffListPopup",staffList);
		}catch(Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		result[0]=AppStrUtils.toEmpty(page);
		result[1]="[{}]";
		return result;
	}
	
	private int toPageValue(String page){
		int pageValue=AppNumUtils.toIntValue(page);
		if(0==pageValue){
			return 1;
		}
		return pageValue;
	}
	
	private List<AutoItem> toAutoStaffList(List<OtherOutlay> otherOutlayList,String keyword, String highLightKeyword){
		List<AutoItem> listAutoOtherOutlay = new ArrayList<AutoItem>();
		if(null!=otherOutlayList){
			AutoItem newItem = null;
			OtherOutlay newOtherOutlay = null;
			for (int i=0;i<otherOutlayList.size();i++) {
				newOtherOutlay = otherOutlayList.get(i);
				newItem = toAutoOtherOutlay(newOtherOutlay,keyword,highLightKeyword);
				listAutoOtherOutlay.add(newItem);
			}
		}
		return listAutoOtherOutlay;
	}
	
	private AutoItem toAutoOtherOutlay(OtherOutlay otherOutlay, String keyword,String hightLightKeyword){
		AutoItem newItem = new AutoItem();
		newItem.setSn(otherOutlay.getSn());
		newItem.setDisplay(otherOutlay.getName());
		String title = otherOutlay.getName().replace(keyword,hightLightKeyword);
		newItem.setTitle(title);
		
		return newItem;
	}
}
