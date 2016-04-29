package com.cafe.controller;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.cafe.dto.LoginUser;
import com.cafe.entity.Food;
import com.cafe.entity.FoodGroup;
import com.cafe.entity.FoodUnit;
import com.cafe.entity.Role;
import com.cafe.form.FoodForm;
import com.cafe.form.FoodUnitForm;
import com.cafe.search.FoodSearch;
import com.cafe.service.interfaces.SettingService;
import com.cafe.service.interfaces.StoreService;
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
public class FoodController extends BaseController{  
	
	private List<String> errors = new ArrayList<String>();
	private static Logger LOG;
	
	static{
		LOG= Logger.getLogger(FoodController.class);
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
	
	@RequestMapping(value = "/cafeOrder/view/ajaxGetFoodRecipes",  method = {RequestMethod.POST},produces = Constant.JSON_TEXT_HTML_UTF_8)
	public @ResponseBody String ajaxGetFoodRecipes(@RequestParam("foodSn") Long foodSn,Model model, HttpServletRequest request, HttpSession session) {
		LOG.info("/cafeOrder/view/ajaxGetFoodRecipes");
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		try{
			Food food = storeService.findFood(loginUser.getCafeShopSn(),foodSn);
			if(null!=food){
				if(FoodForm.RECIPES_PUBLIC==food.getPublicRecipes()){
					return food.getRecipes();
				}else if(Role.ROLE_STAFF!=loginUser.getRoleSn()){
					return food.getRecipes();
				}
			}
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return AppStrUtils.EMPTY;
	}
	
	@RequestMapping(value = "/food/update", method = {RequestMethod.GET})
	public String initUpdateFood(@RequestParam("sn") Long sn,Model model, HttpServletRequest request, HttpSession session) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/food/update");
		try{
			Food food = storeService.findFood(loginUser.getCafeShopSn(),sn);
			model.addAttribute("foodUnitList",createFoodUnitList(loginUser.getCafeShopSn()));
			model.addAttribute("foodGroupList",createFoodGroupList(loginUser.getCafeShopSn()));
			model.addAttribute("food",food);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "food/update";
	}
	
	@RequestMapping(value = "/food/update/request", method = {RequestMethod.POST})
	public String updateFood(@ModelAttribute Food food,Model model, HttpServletRequest request, HttpSession session) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/food/update/request");
		try{
			food.setCafeShopSn(loginUser.getCafeShopSn());
			FoodForm.rejectBlank(food);
			if(false==checkInCaseInsertUpdateFood(model,food)){
				model.addAttribute("foodUnitList",createFoodUnitList(loginUser.getCafeShopSn()));
				model.addAttribute("foodGroupList",createFoodGroupList(loginUser.getCafeShopSn()));
				return "/food/update";
			}
			storeService.updateFood(food);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "redirect:/food/list";
	}
	
	private boolean checkInCaseInsertUpdateFood(Model model,Food food){
		errors.clear();
		Food findFood = storeService.findFoodViaName(food.getCafeShopSn(),food.getName());
		if(null!=food.getSn()){//update
			if(null!=findFood && !findFood.getSn().equals(food.getSn())){
				errors.add(getString("food.duplicateFoodName"));
			}	
		}else{//insert
			if(null!=findFood){
				errors.add(getString("food.duplicateFoodName"));
			}
		}
		int price = AppNumUtils.toIntValue(food.getPrice());
		if(price<=0){
			errors.add("food.priceNotZero");
		}
		
		int profit = AppNumUtils.toIntValue(food.getProfit());
		if(profit>=price){
			errors.add("food.profitPriceErr");
		}
		if(errors.size()>0){
			model.addAttribute(ERROR_MSG,errors);
			return false;
		}
		return true;
	}
	
	@RequestMapping(value = "/food/add", method = {RequestMethod.GET})
	public String initAddFood(Model model, HttpServletRequest request, HttpSession session) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/food/add");
		try{
			Food food = new Food();
			model.addAttribute("foodUnitList",createFoodUnitList(loginUser.getCafeShopSn()));
			model.addAttribute("foodGroupList",createFoodGroupList(loginUser.getCafeShopSn()));
			model.addAttribute("food",food);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "food/add";
	}
	
	private Map<Long,String> createFoodUnitList(Long cafeShopSn){
		List<FoodUnitForm> foodUnitList = storeService.findAllFoodUnitList(cafeShopSn);
		Map<Long,String> foodUnitMap = new LinkedHashMap<Long,String>();
		for (FoodUnitForm foodUnit : foodUnitList) {
			foodUnitMap.put(foodUnit.getSn(),foodUnit.getName());
		}
		return foodUnitMap;
	}
	
	private Map<Long,String> createFoodGroupList(Long cafeShopSn){
		List<FoodGroup> foodGroupList = storeService.findAllFoodGroupList(cafeShopSn);
		Map<Long,String> foodUnitMap = new LinkedHashMap<Long,String>();
		for (FoodGroup foodGroup : foodGroupList) {
			foodUnitMap.put(foodGroup.getSn(),foodGroup.getName());
		}
		return foodUnitMap;
	}
	
	@RequestMapping(value = "/food/add/request", method = {RequestMethod.POST})
	public String addNewFoodUnit(@ModelAttribute Food food,Model model, HttpServletRequest request, HttpSession session) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/food/add/request");
		try{
			food.setCafeShopSn(loginUser.getCafeShopSn());
			FoodForm.rejectBlank(food);
			if(false==checkInCaseInsertUpdateFood(model,food)){
				model.addAttribute("foodUnitList",createFoodUnitList(loginUser.getCafeShopSn()));
				model.addAttribute("foodGroupList",createFoodGroupList(loginUser.getCafeShopSn()));
				return "/food/add";
			}
			storeService.insertFood(food);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "redirect:/food/list";
	}
	
	@RequestMapping(value = "/food/list", method = {RequestMethod.GET})
	public String initFoodList(Model model, HttpServletRequest request, HttpSession session) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/food/list");
		try{

		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "food/list";
	}
	
	@RequestMapping(value = "/food/list/ajaxList", method = {RequestMethod.POST})
	public String initAjaxGetFoodList(Model model, HttpServletRequest request, HttpSession session,
			@RequestParam(value = "foodSn", required = false) final String foodSnStr,
			@RequestParam(value = "sort", required = false) final String sort,
			@RequestParam(value = "orderField", required = false) final String orderField) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/food/list/ajaxList");
		try{
			Long foodSn = AppNumUtils.toLong(foodSnStr);
			loadFoodList(loginUser,model,foodSn,orderField,sort);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "food/ajaxList";
	}
	
	private void loadFoodList(LoginUser loginUser,Model model,Long foodSn,String orderField,String sort){
		String finalOrderField = null;
		if(AppStrUtils.isEmpty(orderField)){
			finalOrderField = FoodSearch.ORDER_LAST_UPFATE;
		}else{
			finalOrderField = orderField;
		}
		String finalSort = null;
		if(AppStrUtils.isEmpty(sort)){
			finalSort = Constant.SORT_DESC;
		}else{
			finalSort = sort;
		}
		List<FoodForm> foodFormList = storeService.findAllFoodFormList(loginUser.getCafeShopSn(),foodSn,finalOrderField,finalSort);
		model.addAttribute("orderField",finalOrderField);
		if(Constant.SORT_DESC.equals(finalSort)){
			model.addAttribute("sort",Constant.SORT_ASC);
		}else{
			model.addAttribute("sort",Constant.SORT_DESC);
		}
		model.addAttribute("foodList",foodFormList);
	}
	
	@RequestMapping(value = "/food/delete/request", method = {RequestMethod.POST})
	public String deleteFood(@RequestParam("foodSn") Long sn,Model model, HttpServletRequest request, HttpSession session,
			@RequestParam(value = "sort", required = false) final String sort,
			@RequestParam(value = "orderField", required = false) final String orderField) {
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/food/delete/request");
		try{
			storeService.deleteFoodLogic(loginUser.getCafeShopSn(),sn);
			loadFoodList(loginUser,model,null,orderField,sort);
		}catch (Exception ex){
			LOG.error(getExceptionContent(ex));
		}
		return "food/ajaxList";
	}
	
	
	
	/**
	* 
	* @author  	phap.nguyen
	* @version 	1.0
	* @since   	2015-06-12 
	* @update   
	*/
	
	@RequestMapping(value = "/suggestion/view/ajaxGetAutoFoodList", method = RequestMethod.POST)
	public @ResponseBody String[] ajaxGetAutoFoodList(Model model,HttpSession session,
			@RequestParam(value = "keySearch", required = false) final String keySearch,
			@RequestParam(value = "page", required = false) final String pageStr){
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		LOG.info("/suggestion/view/ajaxGetAutoOtherOutlayList");
		String[] result = new String[2];
		int page = toPageValue(pageStr);
		try{
			//Setting setting = settingService.findSetting(hospitalSn,"0002","02");
			FoodSearch search = new FoodSearch();
			search.setCafeShopSn(loginUser.getCafeShopSn());
			search.setKeyword(keySearch);
			search.setOffset(((page-1)*Constant.SUGGESTION_PAGE_SIZE));
			search.setLimit(Constant.SUGGESTION_PAGE_SIZE);
			List<Map<String,Object>> foodMapList= storeService.findAllFoodMapListForSuggestion(search);
			String highLightKeyword=AppStrUtils.EMPTY;
			if(!AppStrUtils.isEmpty(keySearch)){
				highLightKeyword="<b class='autocomplete-highlight'>"+keySearch+"</b>";	
			}
			List<AutoItem> staffList = toAutoFoodList(foodMapList,keySearch,highLightKeyword);
			Gson gsone = new GsonBuilder().create();
			String jsonEvent=gsone.toJson(staffList,List.class);
			if(staffList.size()>0){//may be have more data
				page+=1;
			}
			result[0]=AppStrUtils.toEmpty(page);
			result[1]=jsonEvent;
			return result;
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
	
	private List<AutoItem> toAutoFoodList(List<Map<String,Object>> otherFoodList,String keyword, String highLightKeyword){
		List<AutoItem> autoFoodList = new ArrayList<AutoItem>();
		if(null!=otherFoodList){
			AutoItem newItem = null;
			Map<String,Object> newFoodMap = null;
			for (int i=0;i<otherFoodList.size();i++) {
				newFoodMap = otherFoodList.get(i);
				newItem = toAutoOtherOutlay(newFoodMap,keyword,highLightKeyword);
				autoFoodList.add(newItem);
			}
		}
		return autoFoodList;
	}
	
	private AutoItem toAutoOtherOutlay(Map<String,Object> foodMap, String keyword,String hightLightKeyword){
		AutoItem newItem = new AutoItem();
		newItem.setSn(AppNumUtils.toLong(foodMap.get(Food.DB_SN)));
		String foodName = AppStrUtils.toEmpty(foodMap.get(Food.DB_NAME));
		String foodUnitName = AppStrUtils.toEmpty(foodMap.get(FoodUnit.DB_FOOD_UNIT_NAME));
		String price = AppStrUtils.toEmpty(foodMap.get(Food.DB_PRICE));
		String memo = AppStrUtils.toEmpty(foodMap.get(Food.DB_MEMO));
		//set display
		StringBuffer data = new StringBuffer(foodName);
		data.append("(").append(foodUnitName).append(")");
		newItem.setDisplay(data.toString());
		//set title
		foodName = foodName.replace(keyword, hightLightKeyword);
		data = new StringBuffer(foodName);
		foodUnitName = foodUnitName.replace(keyword, hightLightKeyword);
		data.append("(").append(foodUnitName).append(")");
		newItem.setValue1(AppNumUtils.toLong(price));
		price = price.replace(keyword, hightLightKeyword);
		data.append(AppStrUtils.BLANK).append(price);
		newItem.setTitle(data.toString());
		if(!AppStrUtils.isEmpty(memo)){
			memo = memo.replace(keyword, hightLightKeyword);
			newItem.setDescription(memo);
		}
		return newItem;
	}
}
