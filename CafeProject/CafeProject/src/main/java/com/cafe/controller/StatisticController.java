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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe.base.controller.BaseController;
import com.cafe.dto.Item;
import com.cafe.dto.LoginUser;
import com.cafe.dto.StatisticByDate;
import com.cafe.dto.StatisticByDay;
import com.cafe.dto.StatisticByHour;
import com.cafe.dto.StatisticByMonth;
import com.cafe.dto.StatisticByTable;
import com.cafe.dto.StatisticByTime;
import com.cafe.entity.Setting;
import com.cafe.form.CafeOrderForm;
import com.cafe.form.CafeTableForm;
import com.cafe.search.CafeOrderSearch;
import com.cafe.search.OtherOutlayTranSearch;
import com.cafe.service.interfaces.OrderService;
import com.cafe.service.interfaces.SettingService;
import com.cafe.service.interfaces.StoreService;
import com.cafe.utils.AppDateUtils;
import com.cafe.utils.AppNumUtils;
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
public class StatisticController extends BaseController{  

	private static Logger LOG;
	
	static{
		LOG= Logger.getLogger(StatisticController.class);
	}
	
	@Autowired
	public void AccountsController(MessageSource messages) {
	   this.messages = messages;
	}

	@Autowired
	private OrderService orderService;
	
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
	
	@RequestMapping(value = "/statistic/list/monthStatistic", method = {RequestMethod.GET})
	public String initStatisticDayList(Model model, HttpServletRequest request, HttpSession session) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/statistic/monthStatistic");
		try{
			List<Item> yearList = getYearList();
			model.addAttribute("yearList",yearList);
			Calendar cal=Calendar.getInstance();
			int currentYear = cal.get(Calendar.YEAR);
			model.addAttribute("selectedYear",currentYear);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "statistic/monthStatistic";
	}
	
	private List<Item> getYearList(){
		Item newItem = null;
		List<Item> itemList = new ArrayList<Item>();
		newItem = new Item(new Long(2016),"2016");
		itemList.add(newItem);
		newItem = new Item(new Long(2017),"2017");
		itemList.add(newItem);
		newItem = new Item(new Long(2018),"2018");
		itemList.add(newItem);
		newItem = new Item(new Long(2019),"2019");
		itemList.add(newItem);
		newItem = new Item(new Long(2020),"2020");
		itemList.add(newItem);
		return itemList;
	}
	
	@RequestMapping(value = "/statistic/list/getCafeOrderGroupByMonth", method = {RequestMethod.POST})
	public String ajaxGetCafeOrderGroupByMonth(Model model, HttpServletRequest request, HttpSession session,
			@RequestParam(value ="year", required = false) final String yearStr) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/statistic/list/getCafeOrderGroupByMonth");
		try{
			int year = AppNumUtils.toIntValue(yearStr);
			loadOrderCafeGroupByMonth(loginUser,model,year);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "statistic/ajaxMonthList";
	}
	
	private void loadOrderCafeGroupByMonth(LoginUser loginUser,Model model,int year){
		List<StatisticByMonth> monthStatisticList = new ArrayList<StatisticByMonth>();
		Calendar cal = Calendar.getInstance();
		if(year>0){
			cal.set(Calendar.YEAR,year);
		}
		Date startTime = null;
		Date endTime= null;
		StatisticByMonth statisticByMonth = null;
		int totalMoneyOutlay=0;
		Setting setting = settingService.findSetting(loginUser.getCafeShopSn(),"0002","01");
		int startDateSetting = setting.getNumdata1().intValue();
		cal.set(Calendar.DAY_OF_MONTH, startDateSetting);
		String monthTitle = getString("statistic.month")+AppStrUtils.BLANK;
		OtherOutlayTranSearch outlaySearch = new OtherOutlayTranSearch();
		outlaySearch.setCafeShopSn(loginUser.getCafeShopSn());
		for (int i = 0; i < 12; i++) {//statistic in 12 months
			cal.set(Calendar.MONTH,i);
			startTime = getStartDate(loginUser.getCafeShopSn(),cal.getTime());
			endTime=getEndDate(loginUser.getCafeShopSn(),cal.getTime());
			statisticByMonth = orderService.findOrderStatisticInPeriodTime(loginUser.getCafeShopSn(),startTime,endTime);
			outlaySearch.setStartDate(startTime);
			outlaySearch.setEndDate(endTime);
			totalMoneyOutlay= storeService.calculateMoneyOfOtherOutlayTran(outlaySearch);
			statisticByMonth.setTotalMoneyOtherOutlay(totalMoneyOutlay);
			statisticByMonth.setTimeFrameStart(AppDateUtils.toYYYYMMDDStr(startTime));
			statisticByMonth.setTimeFrameEnd(AppDateUtils.toYYYYMMDDStr(endTime));
			statisticByMonth.setMonthOfYear(monthTitle+(i+1));
			monthStatisticList.add(statisticByMonth);
		}
		model.addAttribute("monthStatisticList",monthStatisticList);
	}        
	
	
	@RequestMapping(value = "/statistic/list", method = {RequestMethod.GET})
	public String initStatisticDayList(Model model, HttpServletRequest request, HttpSession session,
			@RequestParam(value ="fromDate", required = false) final String fromDateStr,
			@RequestParam(value ="toDate", required = false) final String toDateStr,
			@RequestParam(value ="display", required = false) final String displayStr) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/statistic/list");
		try{
			List<Item> statisticTypeList = getTypeOfStatistic();
			model.addAttribute("typeList",statisticTypeList);
			int display = AppNumUtils.toIntValue(displayStr);
			if(0==display){
				display = Item.STATISTIC_DATE;
			}
			model.addAttribute("selectedType",display);
			Date date = AppDateUtils.toYYMMDD(fromDateStr);
			if(null!=date){
				//date = AppDateUtils.clearTime(date);
			}else{
				date = getStartDate(loginUser.getCafeShopSn(),null);
			}
			model.addAttribute("fromDate",AppDateUtils.toYYYYMMDDStr(date));
			date = AppDateUtils.toYYMMDD(toDateStr);
			if(null!=date){
				//date = AppDateUtils.setTimeToEndOfDay(date);
			}else{
				date = getEndDate(loginUser.getCafeShopSn(),null);
			}
			model.addAttribute("toDate",AppDateUtils.toYYYYMMDDStr(date));
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "statistic/list";
	}
	
	private Date getStartDate(Long cafeShopSn,Date date){
		//default from 8th of this month to 8th of next month
		Setting setting = settingService.findSetting(cafeShopSn,"0002","01");
		Calendar cal = Calendar.getInstance();
		if(null!=date){
			cal.setTime(date);
		}
		int currentDay= cal.get(Calendar.DAY_OF_MONTH);
		int currentMonth = cal.get(Calendar.MONTH);
		int currentYear = cal.get(Calendar.YEAR);
		int startDateSetting = setting.getNumdata1().intValue();
		cal.set(Calendar.DAY_OF_MONTH,startDateSetting);
		if(currentDay>=8 && currentDay<=31){
			
		}else{
			currentMonth=currentMonth-1;
			if(currentMonth<0){
				currentMonth = 11;
				cal.set(Calendar.YEAR,currentYear-1);
			}
			cal.set(Calendar.MONTH,currentMonth);
		}
		return AppDateUtils.clearTime(cal.getTime());
	}
	
	private Date getEndDate(Long cafeShopSn,Date date){
		//default from 8th of this month to 8th of next month
		Setting setting = settingService.findSetting(cafeShopSn,"0002","01");
		Calendar cal = Calendar.getInstance();
		if(null!=date){
			cal.setTime(date);
		}
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
		return AppDateUtils.setTimeToEndOfDay(cal.getTime());
	}
	
	private List<Item> getTypeOfStatistic(){
		Item newType = null;
		List<Item> typeList = new ArrayList<Item>();
		newType = new Item(new Long(Item.STATISTIC_LIST),getString("statistic.list"));
		typeList.add(newType);
		newType = new Item(new Long(Item.STATISTIC_FOOD),getString("statistic.food"));
		typeList.add(newType);
		newType = new Item(new Long(Item.STATISTIC_TABLE),getString("statistic.table"));
		typeList.add(newType);
		newType = new Item(new Long(Item.STATISTIC_TIME),getString("statistic.time"));
		typeList.add(newType);
		newType = new Item(new Long(Item.STATISTIC_HOUR),getString("statistic.hour"));
		typeList.add(newType);
		newType = new Item(new Long(Item.STATISTIC_DATE),getString("statistic.date"));
		typeList.add(newType);
		newType = new Item(new Long(Item.STATISTIC_DAY_OF_WEEK),getString("statistic.dayOfWeek"));
		typeList.add(newType);
		return typeList;
	}
	
	@RequestMapping(value = "/statistic/list/getCafeOrderList", method = {RequestMethod.POST})
	public String ajaxGetCafeOrderStatisticDayList(Model model, HttpServletRequest request, HttpSession session,
			@RequestParam(value ="fromDate", required = false) final String fromDateStr,
			@RequestParam(value ="toDate", required = false) final String toDateStr,
			@RequestParam(value ="page", required = false) final String pageValue,
			@RequestParam(value ="orderField", required = false) final String orderField,
			@RequestParam(value ="sort", required = false) final String sort) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/statistic/list/getCafeOrderList");
		try{
			Date fromDate = AppDateUtils.toYYMMDD(fromDateStr);
			Date toDate = AppDateUtils.toYYMMDD(toDateStr);
			loadOrderCafeList(loginUser,model,fromDate, toDate,pageValue,orderField,sort);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "statistic/ajaxList";
	}
	
	@RequestMapping(value = "/statistic/list/getCafeOrderGroupByFood", method = {RequestMethod.POST})
	public String ajaxGetCafeOrderGroupByFood(Model model, HttpServletRequest request, HttpSession session,
			@RequestParam(value ="fromDate", required = false) final String fromDateStr,
			@RequestParam(value ="toDate", required = false) final String toDateStr,
			@RequestParam(value ="page", required = false) final String pageValue,
			@RequestParam(value ="orderField", required = false) final String orderField,
			@RequestParam(value ="sort", required = false) final String sort) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/statistic/list/getCafeOrderGroupByFood");
		try{
			Date fromDate = AppDateUtils.toYYMMDD(fromDateStr);
			Date toDate = AppDateUtils.toYYMMDD(toDateStr);
			loadOrderCafeGroupByFood(loginUser,model,fromDate, toDate,pageValue,orderField,sort);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "statistic/ajaxFoodList";
	}
	
	
	private void loadTotalValues(Model model, CafeOrderSearch search){
		OtherOutlayTranSearch outlaySearch = new OtherOutlayTranSearch();
		outlaySearch.setCafeShopSn(search.getCafeShopSn());
		outlaySearch.setStartDate(search.getStartTime());
		outlaySearch.setEndDate(search.getEndTime());
		int othersTotalMoney = storeService.calculateMoneyOfOtherOutlayTran(outlaySearch);
		String totalMoney = AppStrUtils.priceToString(Double.valueOf(othersTotalMoney));
		model.addAttribute("othersTotalMoney",totalMoney);
		int orderTotalMoney = orderService.calculateTotalMoney(search);
		int numOfFoodOrder=orderService.countAllNumOfFoodStatusPay(search);
		model.addAttribute("numOfCafeOrder",numOfFoodOrder);
		totalMoney = AppStrUtils.priceToString(orderTotalMoney);
		model.addAttribute("cafeOrderTotalMoney",totalMoney);
	}
	
	private void loadOrderCafeGroupByFood(LoginUser loginUser,Model model,Date fromDate,Date toDate,String pageValue,String orderField, String sort){
		String finalOrderField = null;
		if(AppStrUtils.isEmpty(orderField)){
			finalOrderField = CafeOrderSearch.FIELD_NUM_OF_FOOD;
		}else{
			finalOrderField = orderField;
		}
		String finalSort = null;
		if(AppStrUtils.isEmpty(sort)){
			finalSort = Constant.SORT_DESC;
		}else{
			finalSort = sort;
		}
		model.addAttribute("orderField",finalOrderField);
		if(Constant.SORT_DESC.equals(finalSort)){
			model.addAttribute("sort",Constant.SORT_ASC);
		}else{
			model.addAttribute("sort",Constant.SORT_DESC);
		}
		
		Date startDate = AppDateUtils.clearTime(fromDate);
		Date endDate = AppDateUtils.setTimeToEndOfDay(toDate);
		CafeOrderSearch search = new CafeOrderSearch();
		search.setCafeShopSn(loginUser.getCafeShopSn());
		search.setStartTime(startDate);
		search.setEndTime(endDate);
		search.setOrderField(finalOrderField);
		search.setSort(finalSort);
		loadTotalValues(model,search);
		Setting setting = settingService.findSetting(loginUser.getCafeShopSn(),"0001","01");
		int recordPerPage = setting.getNumdata1().intValue();
		StringBuffer extUrl = new StringBuffer();
		extUrl.append("&fromDate=").append(AppDateUtils.toYYYYMMDDStr(fromDate));
		extUrl.append("&toDate=").append(AppDateUtils.toYYYYMMDDStr(toDate));
		extUrl.append("&orderField=").append(finalOrderField);
		extUrl.append("&sort=").append(finalSort);
		int totalRecord = storeService.countAllFoodList(loginUser.getCafeShopSn());
		paging(model,recordPerPage,totalRecord, pageValue,"/statistic/list/getCafeOrderGroupByFood",extUrl.toString());
		search.setLimit(limit);
		search.setOffset(offset);
		List<CafeOrderForm> cafeOrderFormList = orderService.findLimitCafeOrderListGroupByFood(search);
		model.addAttribute("cafeOrderList",cafeOrderFormList);
	}
	
	@RequestMapping(value = "/statistic/list/getCafeOrderGroupByDate", method = {RequestMethod.POST})
	public String ajaxGetCafeOrderGroupByDate(Model model, HttpServletRequest request, HttpSession session,
			@RequestParam(value ="fromDate", required = false) final String fromDateStr,
			@RequestParam(value ="toDate", required = false) final String toDateStr,
			@RequestParam(value ="page", required = false) final String pageValue,
			@RequestParam(value ="orderField", required = false) final String orderField,
			@RequestParam(value ="sort", required = false) final String sort) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/statistic/list/getCafeOrderGroupByDate");
		try{
			Date fromDate = AppDateUtils.toYYMMDD(fromDateStr);
			Date toDate = AppDateUtils.toYYMMDD(toDateStr);
			loadOrderCafeGroupByDate(loginUser,model,fromDate, toDate,pageValue,orderField,sort);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "statistic/ajaxDateList";
	}
	
	private void loadOrderCafeGroupByDate(LoginUser loginUser,Model model,Date fromDate,Date toDate,String pageValue,String orderField,String sort){
		String finalOrderField = null;
		if(AppStrUtils.isEmpty(orderField)){
			finalOrderField = CafeOrderSearch.FIELD_ORDER_DATE;
		}else{
			finalOrderField = orderField;
		}
		String finalSort = null;
		if(AppStrUtils.isEmpty(sort)){
			finalSort = Constant.SORT_DESC;
		}else{
			finalSort = sort;
		}
		model.addAttribute("orderField",finalOrderField);
		if(Constant.SORT_DESC.equals(finalSort)){
			model.addAttribute("sort",Constant.SORT_ASC);
		}else{
			model.addAttribute("sort",Constant.SORT_DESC);
		}
		
		Date startDate = AppDateUtils.clearTime(fromDate);
		Date endDate = AppDateUtils.setTimeToEndOfDay(toDate);
		CafeOrderSearch search = new CafeOrderSearch();
		search.setCafeShopSn(loginUser.getCafeShopSn());
		search.setStartTime(startDate);
		search.setEndTime(endDate);
		search.setOrderField(finalOrderField);
		search.setSort(finalSort);
		loadTotalValues(model,search);
		Setting setting = settingService.findSetting(loginUser.getCafeShopSn(),"0001","01");
		int recordPerPage = setting.getNumdata1().intValue();
		StringBuffer extUrl = new StringBuffer();
		extUrl.append("&fromDate=").append(AppDateUtils.toYYYYMMDDStr(fromDate));
		extUrl.append("&toDate=").append(AppDateUtils.toYYYYMMDDStr(toDate));
		extUrl.append("&orderField=").append(finalOrderField);
		extUrl.append("&sort=").append(finalSort);
		int totalRecord = orderService.countRecordCafeOrderListGroupByDate(search);
		paging(model,recordPerPage,totalRecord, pageValue,"/statistic/list/getCafeOrderGroupByDate",extUrl.toString());
		search.setLimit(limit);
		search.setOffset(offset);
		List<StatisticByDate> cafeOrderFormList = orderService.findLimitCafeOrderListGroupByDate(search);
		model.addAttribute("cafeOrderList",cafeOrderFormList);
	}
	
	
	@RequestMapping(value = "/statistic/list/getCafeOrderGroupByTable", method = {RequestMethod.POST})
	public String ajaxGetCafeOrderGroupByTable(Model model, HttpServletRequest request, HttpSession session,
			@RequestParam(value ="fromDate", required = false) final String fromDateStr,
			@RequestParam(value ="toDate", required = false) final String toDateStr,
			@RequestParam(value ="page", required = false) final String pageValue,
			@RequestParam(value ="orderField", required = false) final String orderField,
			@RequestParam(value ="sort", required = false) final String sort) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/statistic/list/getCafeOrderGroupByTable");
		try{
			Date fromDate = AppDateUtils.toYYMMDD(fromDateStr);
			Date toDate = AppDateUtils.toYYMMDD(toDateStr);
			loadOrderCafeGroupByTable(loginUser,model,fromDate, toDate,pageValue,orderField,sort);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "statistic/ajaxTableList";
	}
	
	private void loadOrderCafeGroupByTable(LoginUser loginUser,Model model,Date fromDate,Date toDate,String pageValue,String orderField,String sort){
		String finalOrderField = null;
		if(AppStrUtils.isEmpty(orderField)){
			finalOrderField = CafeOrderSearch.FIELD_ORDER_TIME;
		}else{
			finalOrderField = orderField;
		}
		String finalSort = null;
		if(AppStrUtils.isEmpty(sort)){
			finalSort = Constant.SORT_DESC;
		}else{
			finalSort = sort;
		}
		model.addAttribute("orderField",finalOrderField);
		if(Constant.SORT_DESC.equals(finalSort)){
			model.addAttribute("sort",Constant.SORT_ASC);
		}else{
			model.addAttribute("sort",Constant.SORT_DESC);
		}
		
		Date startDate = AppDateUtils.clearTime(fromDate);
		Date endDate = AppDateUtils.setTimeToEndOfDay(toDate);
		CafeOrderSearch search = new CafeOrderSearch();
		search.setCafeShopSn(loginUser.getCafeShopSn());
		search.setStartTime(startDate);
		search.setEndTime(endDate);
		search.setOrderField(finalOrderField);
		search.setSort(finalSort);
		loadTotalValues(model,search);
		Setting setting = settingService.findSetting(loginUser.getCafeShopSn(),"0001","01");
		int recordPerPage = setting.getNumdata1().intValue();
		StringBuffer extUrl = new StringBuffer();
		extUrl.append("&fromDate=").append(AppDateUtils.toYYYYMMDDStr(fromDate));
		extUrl.append("&toDate=").append(AppDateUtils.toYYYYMMDDStr(toDate));
		extUrl.append("&orderField=").append(finalOrderField);
		extUrl.append("&sort=").append(finalSort);
		int totalRecord = orderService.countRecordCafeOrderListGroupByTable(search);
		paging(model,recordPerPage,totalRecord, pageValue,"/statistic/list/getCafeOrderGroupByTable",extUrl.toString());
		search.setLimit(limit);
		search.setOffset(offset);
		List<StatisticByTable> cafeOrderFormList = orderService.findLimitCafeOrderListGroupByTable(search);
		model.addAttribute("cafeOrderList",cafeOrderFormList);
	}
	
	@RequestMapping(value = "/statistic/list/getCafeOrderGroupByDay", method = {RequestMethod.POST})
	public String ajaxGetCafeOrderGroupByDay(Model model, HttpServletRequest request, HttpSession session,
			@RequestParam(value ="fromDate", required = false) final String fromDateStr,
			@RequestParam(value ="toDate", required = false) final String toDateStr,
			@RequestParam(value ="page", required = false) final String pageValue,
			@RequestParam(value ="orderField", required = false) final String orderField,
			@RequestParam(value ="sort", required = false) final String sort) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/statistic/list/getCafeOrderGroupByDay");
		try{
			Date fromDate = AppDateUtils.toYYMMDD(fromDateStr);
			Date toDate = AppDateUtils.toYYMMDD(toDateStr);
			loadOrderCafeGroupByDay(loginUser,model,fromDate, toDate,pageValue,orderField,sort);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "statistic/ajaxDayList";
	}
	
	private void loadOrderCafeGroupByDay(LoginUser loginUser,Model model,Date fromDate,Date toDate,String pageValue,String orderField, String sort){
		String finalOrderField = null;
		if(AppStrUtils.isEmpty(orderField)){
			finalOrderField = CafeOrderSearch.FIELD_TOTAL_MONEY;
		}else{
			finalOrderField = orderField;
		}
		String finalSort = null;
		if(AppStrUtils.isEmpty(sort)){
			finalSort = Constant.SORT_DESC;
		}else{
			finalSort = sort;
		}
		model.addAttribute("orderField",finalOrderField);
		if(Constant.SORT_DESC.equals(finalSort)){
			model.addAttribute("sort",Constant.SORT_ASC);
		}else{
			model.addAttribute("sort",Constant.SORT_DESC);
		}
		
		Date startDate = AppDateUtils.clearTime(fromDate);
		Date endDate = AppDateUtils.setTimeToEndOfDay(toDate);
		CafeOrderSearch search = new CafeOrderSearch();
		search.setCafeShopSn(loginUser.getCafeShopSn());
		search.setStartTime(startDate);
		search.setEndTime(endDate);
		search.setOrderField(finalOrderField);
		search.setSort(finalSort);
		loadTotalValues(model,search);
		Setting setting = settingService.findSetting(loginUser.getCafeShopSn(),"0001","01");
		//int recordPerPage = setting.getNumdata1().intValue();
		//StringBuffer extUrl = new StringBuffer();
		//extUrl.append("&fromDate=").append(AppDateUtils.toYYMMDDStr(fromDate));
		//extUrl.append("&toDate=").append(AppDateUtils.toYYMMDDStr(toDate));
		//int totalRecord = orderService.countRecordCafeOrderListGroupByTable(search);
		//paging(model,recordPerPage,totalRecord, pageValue,"/statistic/list/getCafeOrderGroupByTable",extUrl.toString());
		//search.setLimit(limit);
		//search.setOffset(offset);
		List<StatisticByDay> cafeOrderFormList = orderService.findLimitCafeOrderListGroupByDay(search);
		model.addAttribute("cafeOrderList",cafeOrderFormList);
	}
	
	@RequestMapping(value = "/statistic/list/getCafeOrderGroupByHour", method = {RequestMethod.POST})
	public String ajaxGetCafeOrderGroupByHour(Model model, HttpServletRequest request, HttpSession session,
			@RequestParam(value ="fromDate", required = false) final String fromDateStr,
			@RequestParam(value ="toDate", required = false) final String toDateStr,
			@RequestParam(value ="page", required = false) final String pageValue,
			@RequestParam(value ="orderField", required = false) final String orderField,
			@RequestParam(value ="sort", required = false) final String sort) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/statistic/list/getCafeOrderGroupByHour");
		try{
			Date fromDate = AppDateUtils.toYYMMDD(fromDateStr);
			Date toDate = AppDateUtils.toYYMMDD(toDateStr);
			loadOrderCafeGroupByHour(loginUser,model,fromDate, toDate,pageValue,orderField,sort);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "statistic/ajaxHourList";
	}
	
	private void loadOrderCafeGroupByHour(LoginUser loginUser,Model model,Date fromDate,Date toDate,String pageValue,String orderField,String sort){
		String finalOrderField = null;
		if(AppStrUtils.isEmpty(orderField)){
			finalOrderField = CafeOrderSearch.FIELD_TIME_FRAME;
		}else{
			finalOrderField = orderField;
		}
		String finalSort = null;
		if(AppStrUtils.isEmpty(sort)){
			finalSort = Constant.SORT_DESC;
		}else{
			finalSort = sort;
		}
		model.addAttribute("orderField",finalOrderField);
		if(Constant.SORT_DESC.equals(finalSort)){
			model.addAttribute("sort",Constant.SORT_ASC);
		}else{
			model.addAttribute("sort",Constant.SORT_DESC);
		}
		
		Date startDate = AppDateUtils.clearTime(fromDate);
		Date endDate = AppDateUtils.setTimeToEndOfDay(toDate);
		CafeOrderSearch search = new CafeOrderSearch();
		search.setCafeShopSn(loginUser.getCafeShopSn());
		search.setStartTime(startDate);
		search.setEndTime(endDate);
		search.setOrderField(finalOrderField);
		search.setSort(finalSort);
		loadTotalValues(model,search);
		//Setting setting = settingService.findSetting("0001","01");
		//int recordPerPage = setting.getNumdata1().intValue();
		//StringBuffer extUrl = new StringBuffer();
		//extUrl.append("&fromDate=").append(AppDateUtils.toYYMMDDStr(fromDate));
		//extUrl.append("&toDate=").append(AppDateUtils.toYYMMDDStr(toDate));
		//int totalRecord = orderService.countRecordCafeOrderListGroupByTable(search);
		//paging(model,recordPerPage,totalRecord, pageValue,"/statistic/list/getCafeOrderGroupByTable",extUrl.toString());
		//search.setLimit(limit);
		//search.setOffset(offset);
		List<StatisticByHour> cafeOrderFormList = orderService.findLimitCafeOrderListGroupByHour(search);
		model.addAttribute("cafeOrderList",cafeOrderFormList);
	}
	
	
	@RequestMapping(value = "/statistic/list/getCafeOrderGroupByTime", method = {RequestMethod.POST})
	public String ajaxGetCafeOrderGroupByTime(Model model, HttpServletRequest request, HttpSession session,
			@RequestParam(value ="fromDate", required = false) final String fromDateStr,
			@RequestParam(value ="toDate", required = false) final String toDateStr,
			@RequestParam(value ="page", required = false) final String pageValue) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/statistic/list/getCafeOrderGroupByTime");
		try{
			Date fromDate = AppDateUtils.toYYMMDD(fromDateStr);
			Date toDate = AppDateUtils.toYYMMDD(toDateStr);
			loadOrderCafeGroupByTime(loginUser,model,fromDate, toDate,pageValue);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "statistic/ajaxTimeList";
	}
	
	private void loadOrderCafeGroupByTime(LoginUser loginUser,Model model,Date fromDate,Date toDate,String pageValue){
		Date startDate = AppDateUtils.clearTime(fromDate);
		Date endDate = AppDateUtils.setTimeToEndOfDay(toDate);
		CafeOrderSearch search = new CafeOrderSearch();
		search.setCafeShopSn(loginUser.getCafeShopSn());
		search.setStartTime(startDate);
		search.setEndTime(endDate);
		loadTotalValues(model,search);
		//Setting setting = settingService.findSetting("0001","01");
		//int recordPerPage = setting.getNumdata1().intValue();
		//StringBuffer extUrl = new StringBuffer();
		//extUrl.append("&fromDate=").append(AppDateUtils.toYYMMDDStr(fromDate));
		//extUrl.append("&toDate=").append(AppDateUtils.toYYMMDDStr(toDate));
		//int totalRecord = orderService.countRecordCafeOrderListGroupByTable(search);
		//paging(model,recordPerPage,totalRecord, pageValue,"/statistic/list/getCafeOrderGroupByTable",extUrl.toString());
		//search.setLimit(limit);
		//search.setOffset(offset);
		List<StatisticByTime> cafeOrderFormList = orderService.findLimitCafeOrderListGroupByTime(search);
		model.addAttribute("cafeOrderList",cafeOrderFormList);
	}
	
	private void loadOrderCafeList(LoginUser loginUser,Model model,Date fromDate,Date toDate,String pageValue,String orderField, String sort){
		String finalOrderField = null;
		if(AppStrUtils.isEmpty(orderField)){
			finalOrderField = CafeOrderSearch.FIELD_ORDER_TIME;
		}else{
			finalOrderField = orderField;
		}
		String finalSort = null;
		if(AppStrUtils.isEmpty(sort)){
			finalSort = Constant.SORT_DESC;
		}else{
			finalSort = sort;
		}
		model.addAttribute("orderField",finalOrderField);
		if(Constant.SORT_DESC.equals(finalSort)){
			model.addAttribute("sort",Constant.SORT_ASC);
		}else{
			model.addAttribute("sort",Constant.SORT_DESC);
		}
		Date startDate = AppDateUtils.clearTime(fromDate);
		Date endDate = AppDateUtils.setTimeToEndOfDay(toDate);
		CafeOrderSearch search = new CafeOrderSearch();
		search.setCafeShopSn(loginUser.getCafeShopSn());
		search.setCafeTableSn(null);
		search.setStartTime(startDate);
		search.setEndTime(endDate);
		search.setOrderField(finalOrderField);
		search.setSort(finalSort);
		loadTotalValues(model,search);
		Setting setting = settingService.findSetting(loginUser.getCafeShopSn(),"0001","01");
		int recordPerPage = setting.getNumdata1().intValue();
		StringBuffer extUrl = new StringBuffer();
		extUrl.append("&fromDate=").append(AppDateUtils.toYYYYMMDDStr(fromDate));
		extUrl.append("&toDate=").append(AppDateUtils.toYYYYMMDDStr(toDate));
		extUrl.append("&orderField=").append(finalOrderField);
		extUrl.append("&sort=").append(finalSort);
		int totalRecord = orderService.countAllCafeOrderListStatusPay(search);
		paging(model,recordPerPage,totalRecord, pageValue,"/statistic/list/getCafeOrderList",extUrl.toString());
		search.setLimit(limit);
		search.setOffset(offset);
		List<CafeOrderForm> cafeOrderFormList = orderService.findLimitCafeOrderListStatusPay(search);
		model.addAttribute("cafeOrderList",cafeOrderFormList);
	}
	
	public List<CafeTableForm> findAllCafeTableFormList(Long cafeShopSn,boolean includeAll){
		List<CafeTableForm> cafeTableList = storeService.findAllCafeTableFormList(cafeShopSn);
		if(true==includeAll){
			CafeTableForm newTable = new CafeTableForm();
			newTable.setSn(0L);
			newTable.setName(getString("common.selectAll"));
			cafeTableList.add(0,newTable);
		}
		return cafeTableList;
	}
	
	@RequestMapping(value = "/statistic/delete/request", method = {RequestMethod.POST})
	public String deleteCafeOrder(Model model, HttpServletRequest request, HttpSession session,
			@RequestParam(value ="cafeOrderSn", required = false) final String cafeOrderSnStr,
			@RequestParam(value ="fromDate", required = false) final String fromDateStr,
			@RequestParam(value ="toDate", required = false) final String toDateStr,
			@RequestParam(value ="page", required = false) final String pageValue,
			@RequestParam(value ="orderField", required = false) final String orderField,
			@RequestParam(value ="sort", required = false) final String sort) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/statistic/delete/request");
		try{
			Long cafeOrderSn = AppNumUtils.toLong(cafeOrderSnStr);
			Date fromDate = AppDateUtils.toYYMMDD(fromDateStr);
			Date toDate = AppDateUtils.toYYMMDD(toDateStr);
			orderService.deleteCafeOrder(loginUser.getCafeShopSn(),cafeOrderSn);
			String finalSort = null;
			if(AppStrUtils.isEmpty(sort) || Constant.SORT_DESC.equals(sort)){
				finalSort = Constant.SORT_ASC;
			}else{
				finalSort = Constant.SORT_DESC;
			}
			loadOrderCafeList(loginUser,model, fromDate, toDate, pageValue,orderField,finalSort);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "statistic/ajaxList";
	}
}
