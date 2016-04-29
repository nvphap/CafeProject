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
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe.base.controller.BaseController;
import com.cafe.dto.LoginUser;
import com.cafe.dto.PrintOrderDto;
import com.cafe.dto.StatisticByMonth;
import com.cafe.dto.TableStatistic;
import com.cafe.dto.TodayStatistic;
import com.cafe.entity.CafeOrder;
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
public class CafeOrderController extends BaseController{  
	
	private List<String> errors = new ArrayList<String>();
	private static Logger LOG;
	
	static{
		LOG= Logger.getLogger(CafeOrderController.class);
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
	
	
	@RequestMapping(value = "/cafeOrder/list", method = {RequestMethod.GET})
	public String initcafeOrderList(Model model, HttpServletRequest request, HttpSession session) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/cafeOrder/list");
		try{
			List<CafeTableForm> cafeTableList = findAllCafeTableFormList(loginUser.getCafeShopSn(),false);
			model.addAttribute("tableList",cafeTableList);
			if(cafeTableList.size()>0){
				model.addAttribute("selectedTable",cafeTableList.get(0).getSn());//all
			}
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "cafeOrder/list";
	}
	
	@RequestMapping(value = "/cafeOrder/list/ajaxList", method = {RequestMethod.POST})
	public String initAjaxGetCafeOrderList(Model model, HttpServletRequest request, HttpSession session) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/cafeOrder/list/ajaxList");
		try{
			loadOrderCafeList(loginUser,model);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "cafeOrder/ajaxList";
	}
	
	private void loadOrderCafeList(LoginUser loginUser,Model model){
		Calendar cal = Calendar.getInstance();
		Date currentDate= cal.getTime();
		Date startDate = AppDateUtils.clearTime(currentDate);
		Date endDate = AppDateUtils.setTimeToEndOfDay(cal);
		List<CafeOrderForm> cafeOrderFormList = orderService.findAllCafeOrderListStatusOrder(loginUser.getCafeShopSn(),null,null,null);
		model.addAttribute("cafeOrderList",cafeOrderFormList);
		
		TodayStatistic todayStatistic = findTodayStatistic(loginUser,startDate,endDate);
		model.addAttribute("todayStatistic",todayStatistic);
		
		StatisticByMonth statisticByMonth = findMonthStatistic(loginUser.getCafeShopSn());
		model.addAttribute("monthStatistic",statisticByMonth);
	}
	
	private TodayStatistic findTodayStatistic(LoginUser loginUser,Date startDate, Date endDate){
		TodayStatistic todayStatistic = new TodayStatistic();
		OtherOutlayTranSearch outlaySearch = new OtherOutlayTranSearch();
		outlaySearch.setCafeShopSn(loginUser.getCafeShopSn());
		outlaySearch.setStartDate(startDate);
		outlaySearch.setEndDate(endDate);
		int otherOutlayToday = storeService.calculateMoneyOfOtherOutlayTran(outlaySearch);
		todayStatistic.setOtherOutlayToday(otherOutlayToday);
		CafeOrderSearch search = new CafeOrderSearch();
		search.setCafeShopSn(loginUser.getCafeShopSn());
		search.setStartTime(startDate);
		search.setEndTime(endDate);
		int totalMoneyToday = orderService.calculateTotalMoney(search);
		int numOfFoodToday=orderService.countAllNumOfFoodStatusPay(search);
		int totalExpectedMoneyToday = orderService.calculateTotalExpectedMoney(search);
		todayStatistic.setNumOfFoodToday(numOfFoodToday);
		todayStatistic.setTotalMoneyToday(totalMoneyToday);
		todayStatistic.setTotalExpectedMoneyToday(totalExpectedMoneyToday);
		
		List<TableStatistic> statisticList = orderService.findCafeOrderStatistic(loginUser.getCafeShopSn(),null,null);
		todayStatistic.setTableStatisticList(statisticList);
		return todayStatistic;
	}
	
	private StatisticByMonth findMonthStatistic(Long cafeShopSn){
		Date startMonth= getStartDate(cafeShopSn,null);
		Date endMonth=getEndDate(cafeShopSn,null);
		
		StatisticByMonth statisticByMonth = orderService.findOrderStatisticInPeriodTime(cafeShopSn,startMonth,endMonth);
		Date today = Calendar.getInstance().getTime();
		int days = AppDateUtils.calDaysBetween2Days(startMonth,today);
		int averageMoney = statisticByMonth.getTotalMoney()/days;
		statisticByMonth.setMoneyPerDay(averageMoney);
		OtherOutlayTranSearch search = new OtherOutlayTranSearch();
		search.setCafeShopSn(cafeShopSn);
		search.setStartDate(startMonth);
		search.setEndDate(endMonth);
		int totalMoneyOutlay= storeService.calculateMoneyOfOtherOutlayTran(search);
		statisticByMonth.setTotalMoneyOtherOutlay(totalMoneyOutlay);
		return statisticByMonth;
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
				currentMonth = 12;
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
	
	public List<CafeTableForm> findAllCafeTableFormList(Long cafeShopSn,boolean includeAll){
		List<CafeTableForm> cafeTableList = storeService.findAllCafeTableFormListOrderByIdx(cafeShopSn);
		if(true==includeAll){
			CafeTableForm newTable = new CafeTableForm();
			newTable.setSn(0L);
			newTable.setName(getString("common.selectAll"));
			cafeTableList.add(0,newTable);
		}
		return cafeTableList;
	}
	
	@RequestMapping(value = "/cafeOrder/update/ajaxUpdateDiscountAllBill", method = {RequestMethod.POST})
	public String ajaxAddNewCafeOrder(Model model, HttpServletRequest request, HttpSession session,
			@RequestParam(value ="cafeTableSn", required = false) final String cafeTableSnStr,
			@RequestParam(value ="discount", required = false) final String discountStr) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/cafeOrder/update/ajaxUpdateDiscountAllBill");
		try{
			Long cafeTableSn = AppNumUtils.toLong(cafeTableSnStr);
			int discount=AppNumUtils.toIntValue(discountStr);
			orderService.updateDiscountOfAllBill(loginUser.getCafeShopSn(),cafeTableSn,discount);
			loadOrderCafeList(loginUser,model);
			model.addAttribute("selectedDiscount",(discount/5));
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "cafeOrder/ajaxList";
	}
	
	@RequestMapping(value = "/cafeOrder/update/ajaxPayAllOrderOfBill", method = {RequestMethod.POST})
	public String ajaxPayAllOrderOfBill(Model model, HttpServletRequest request, HttpSession session,
			@RequestParam(value ="cafeTableSn", required = false) final String cafeTableSnStr) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/cafeOrder/update/ajaxPayAllOrderOfBill");
		try{
			Long cafeTableSn = AppNumUtils.toLong(cafeTableSnStr);
			Date now = Calendar.getInstance().getTime();
			orderService.payAllOrderOfBill(loginUser.getCafeShopSn(),cafeTableSn,now,loginUser.getSn());
			loadOrderCafeList(loginUser,model);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "cafeOrder/ajaxList";
	}
	
	@RequestMapping(value = "/cafeOrder/add/ajaxAddNewCafeOrder", method = {RequestMethod.POST})
	public String ajaxAddNewCafeOrder(Model model, HttpServletRequest request, HttpSession session,
			@RequestParam(value ="foodSn", required = false) final String foodSnStr,
			@RequestParam(value ="cafeTableSn", required = false) final String cafeTableSnStr,
			@RequestParam(value ="numOfFood", required = false) final String numOfFoodStr,
			@RequestParam(value ="price", required = false) final String priceStr,
			@RequestParam(value ="totalMoney", required = false) final String totalMoneyStr,
			@RequestParam(value ="memo", required = false) final String memo,
			@RequestParam(value ="discount", required = false) final String discountStr) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/cafeOrder/add/ajaxAddNewCafeOrder");
		try{
			Long foodSn = AppNumUtils.toLong(foodSnStr);
			Long cafeTableSn = AppNumUtils.toLong(cafeTableSnStr);
			int numOfFood = AppNumUtils.toIntValue(numOfFoodStr);
			int price = AppNumUtils.toIntValue(priceStr);
			int totalMoney = AppNumUtils.toIntValue(totalMoneyStr);
			int discount=AppNumUtils.toIntValue(discountStr);
			CafeOrder newCafeOrder = new CafeOrder();
			newCafeOrder.setCafeTableSn(cafeTableSn);
			newCafeOrder.setCafeShopSn(loginUser.getCafeShopSn());
			newCafeOrder.setFoodSn(foodSn);
			newCafeOrder.setPrice(price);
			newCafeOrder.setNumOfFood(numOfFood);
			newCafeOrder.setTotalMoney(totalMoney);
			newCafeOrder.setMemo(memo);
			newCafeOrder.setOrderTime(Calendar.getInstance().getTime());
			newCafeOrder.setStatus(CafeOrder.STATUS_ORDER);
			newCafeOrder.setDiscount(discount);
			newCafeOrder.setCreateStaffSn(loginUser.getSn());
			newCafeOrder.setLastUpdateStaffSn(loginUser.getSn());
			orderService.insertNewCafeOrder(newCafeOrder);
			loadOrderCafeList(loginUser,model);
			
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "cafeOrder/ajaxList";
	}
	
	@RequestMapping(value = "/cafeOrder/update/ajaxUpdateCafeOrder", method = {RequestMethod.POST})
	public String ajaxUpdateCafeOrder(Model model, HttpServletRequest request, HttpSession session,
			@RequestParam(value ="cafeOrderSn", required = false) final String cafeOrderSnStr,
			@RequestParam(value ="foodSn", required = false) final String foodSnStr,
			@RequestParam(value ="cafeTableSn", required = false) final String cafeTableSnStr,
			@RequestParam(value ="numOfFood", required = false) final String numOfFoodStr,
			@RequestParam(value ="price", required = false) final String priceStr,
			@RequestParam(value ="totalMoney", required = false) final String totalMoneyStr,
			@RequestParam(value ="memo", required = false) final String memo,
			@RequestParam(value ="discount", required = false) final String discountStr) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/cafeOrder/update/ajaxUpdateCafeOrder");
		try{
			Long foodSn = AppNumUtils.toLong(foodSnStr);
			Long cafeTableSn = AppNumUtils.toLong(cafeTableSnStr);
			Long cafeOrderSn =  AppNumUtils.toLong(cafeOrderSnStr);
			int numOfFood = AppNumUtils.toIntValue(numOfFoodStr);
			int price = AppNumUtils.toIntValue(priceStr);
			int totalMoney = AppNumUtils.toIntValue(totalMoneyStr);
			int discount = AppNumUtils.toIntValue(discountStr);
			CafeOrder updateCafeOrder = new CafeOrder();
			updateCafeOrder.setSn(cafeOrderSn);
			updateCafeOrder.setCafeShopSn(loginUser.getCafeShopSn());
			updateCafeOrder.setCafeTableSn(cafeTableSn);
			updateCafeOrder.setFoodSn(foodSn);
			updateCafeOrder.setPrice(price);
			updateCafeOrder.setNumOfFood(numOfFood);
			updateCafeOrder.setTotalMoney(totalMoney);
			updateCafeOrder.setMemo(memo);
			updateCafeOrder.setDiscount(discount);
			updateCafeOrder.setLastUpdateStaffSn(loginUser.getSn());
			orderService.updateCafeOrder(updateCafeOrder);
			loadOrderCafeList(loginUser,model);
			
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "cafeOrder/ajaxList";
	}
	
	@RequestMapping(value = "/cafeOrder/delete/request", method = {RequestMethod.POST})
	public String deleteCafeOrder(@RequestParam("cafeOrderSn") Long cafeOrderSn,Model model, HttpServletRequest request, HttpSession session) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/cafeOrder/delete/request");
		try{
			orderService.deleteCafeOrder(loginUser.getCafeShopSn(),cafeOrderSn);
			loadOrderCafeList(loginUser,model);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "cafeOrder/ajaxList";
	}
	
	@RequestMapping(value = "/cafeOrder/update/payCafeOrder", method = {RequestMethod.POST})
	public String payCafeOrder(@RequestParam("cafeOrderSn") Long cafeOrderSn,Model model, HttpServletRequest request, HttpSession session) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/cafeOrder/update/payCafeOrder");
		try{
			Date now = Calendar.getInstance().getTime();
			orderService.payMoneyCafeOrder(loginUser.getCafeShopSn(),cafeOrderSn,now,loginUser.getSn());
			loadOrderCafeList(loginUser,model);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "cafeOrder/ajaxList";
	}
	
	@RequestMapping(value = "/cafeOrder/view/ajaxGetCafeOrderStatisticAllTable", method = {RequestMethod.POST})
	public @ResponseBody String ajaxGetCafeOrderStatisticAllTable(Model model, HttpServletRequest request, HttpSession session) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/cafeOrder/update/payCafeOrder");
		Gson gson = new GsonBuilder().create();
		try{
			//Calendar cal = Calendar.getInstance();
			//Date currentDate= cal.getTime();
			//Date startDate = AppDateUtils.clearTime(currentDate);
			//Date endDate = AppDateUtils.setTimeToEndOfDay(cal);
			List<TableStatistic> statisticList = orderService.findCafeOrderStatistic(loginUser.getCafeShopSn(),null,null);
			return gson.toJson(statisticList);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return AppStrUtils.EMPTY;
	}
	
	@RequestMapping(value = "/cafeOrder/view/ajaxGetCafeOrderStatisticOneTable", method = {RequestMethod.POST})
	public @ResponseBody String ajaxGetCafeOrderStatisticOneTable(@RequestParam("cafeTableSn") Long cafeTableSn,
			Model model, HttpServletRequest request, HttpSession session) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/cafeOrder/view/ajaxGetCafeOrderStatisticOneTable");
		int count =0;
		try{
			Calendar cal = Calendar.getInstance();
			Date currentDate= cal.getTime();
			Date startDate = AppDateUtils.clearTime(currentDate);
			Date endDate = AppDateUtils.setTimeToEndOfDay(cal);
			count =orderService.countCafeOrderOneTable(loginUser.getCafeShopSn(),cafeTableSn,startDate, endDate);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return String.valueOf(count);
	}
	
	
	@RequestMapping(value = "/cafeOrder/view/printOrder", method = {RequestMethod.GET})
	public String printCafeOrder(Model model, HttpServletRequest request, HttpSession session,
			@RequestParam(value ="cafeTableSn", required = false) final String cafeTableSnStr,
			@RequestParam(value ="cafeOrderSn", required = false) final String cafeOrderSnStr) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/cafeOrder/view/printOrder");
		try{
			PrintOrderDto printInfo = new PrintOrderDto(); 
			Long cafeTableSn = AppNumUtils.toLong(cafeTableSnStr);
			Long cafeOrderSn = AppNumUtils.toLong(cafeOrderSnStr);
			Setting setting = settingService.findSetting(loginUser.getCafeShopSn(),"0003","01");
			printInfo.setPhone(setting.getChardata1());
			printInfo.setMobile(setting.getChardata2());
			printInfo.setDatePrint(AppDateUtils.toYYYYMMDDHHMMStr(Calendar.getInstance().getTime()));
			printInfo.setAddress(setting.getClassname1());
			printInfo.setAddress2(setting.getClassname2());
			printInfo.setEmail(setting.getChardata3());
			printInfo.setWebsite(setting.getClassname3());
			List<CafeOrderForm> cafeOrderList = null;
			if(null!=cafeOrderSn){
				CafeOrderForm cafeOrder = orderService.findFullCafeOrder(loginUser.getCafeShopSn(),cafeOrderSn);
				if(null!=cafeOrder){
					cafeOrderList = new ArrayList<CafeOrderForm>();
					cafeOrderList.add(cafeOrder);
					printInfo.setTableName(cafeOrder.getCafeTableName());
					printInfo.setCafeOrderList(cafeOrderList);
					printInfo.setTotalMoney(cafeOrder.getTotalMoneyStr());
				}
			}else if(null!=cafeTableSn){
				cafeOrderList = orderService.findAllCafeOrderListStatusOrder(loginUser.getCafeShopSn(),null,null,cafeTableSn);
				if(cafeOrderList.size()>0){
					printInfo.setTableName(cafeOrderList.get(0).getCafeTableName());
				}
				printInfo.setCafeOrderList(cafeOrderList);
				List<TableStatistic> statisticList = orderService.findCafeOrderStatistic(loginUser.getCafeShopSn(),null,null);
				for (TableStatistic tableStatistic : statisticList) {
					if(cafeTableSn.longValue()==tableStatistic.getCafeTableSn()){
						printInfo.setTotalMoney(tableStatistic.getTotalMoneyStr());
					}
				}
			}
			orderService.updatePrint(loginUser.getCafeShopSn(),cafeTableSn, cafeOrderSn);
			model.addAttribute("printOrder",printInfo);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "cafeOrder/printOrder";
	}
}
