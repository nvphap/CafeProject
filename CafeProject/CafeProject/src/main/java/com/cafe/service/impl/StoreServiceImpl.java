package com.cafe.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.cafe.entity.CafeShop;
import com.cafe.entity.CafeTable;
import com.cafe.entity.Food;
import com.cafe.entity.FoodGroup;
import com.cafe.entity.FoodUnit;
import com.cafe.entity.OtherOutlay;
import com.cafe.entity.OtherOutlayTran;
import com.cafe.entity.TableGroup;
import com.cafe.form.CafeTableForm;
import com.cafe.form.FoodForm;
import com.cafe.form.FoodUnitForm;
import com.cafe.form.OtherOutlayTranForm;
import com.cafe.mapper.CafeShopMapper;
import com.cafe.mapper.CafeTableMapper;
import com.cafe.mapper.FoodGroupMapper;
import com.cafe.mapper.FoodMapper;
import com.cafe.mapper.FoodUnitMapper;
import com.cafe.mapper.OtherOutlayMapper;
import com.cafe.mapper.OtherOutlayTranMapper;
import com.cafe.mapper.TableGroupMapper;
import com.cafe.search.FoodSearch;
import com.cafe.search.OtherOutlayTranSearch;
import com.cafe.service.interfaces.StoreService;
import com.cafe.utils.AppDateUtils;
import com.cafe.utils.AppNumUtils;
import com.cafe.utils.AppStrUtils;
import com.cafe.utils.Constant;
import com.cafe.utils.MyBatisUtil;

/**
* The login action.
*
* @author  	phap.nguyen
* @version 	1.0
* @since   	2015-05-27 
* @update   
*/
// This service handle FOOD_UNIT, FOOD,OTHER_OUTLAY, OTHER_OUTLAY_TRAN, OUTLAY_UNIT table
public class StoreServiceImpl implements StoreService {
	public int insertFoodUnit(Long cafeShopSn,String name){
		int count=0;
		if(AppStrUtils.isEmpty(name)){
			return 0;
		}
		SqlSession session = MyBatisUtil.openSession();
		try {
			FoodUnitMapper mapper = session.getMapper(FoodUnitMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(FoodUnit.COL_NAME,name);
			map.put(FoodUnit.COL_CAFE_SHOP_SN,cafeShopSn);
			count =mapper.insertFoodUnit(map);
			session.commit();
		} finally {
			session.close();
		}
		return count;
	}
	
	public int insertFood(Food food){
		int count=0;
		if(null==food){
			return 0;
		}
		SqlSession session = MyBatisUtil.openSession();
		try {
			FoodMapper mapper = session.getMapper(FoodMapper.class);
			count =mapper.insertFood(food);
			session.commit();
		} finally {
			session.close();
		}
		return count;
	}
	
	public int insertCafeTable(CafeTable cafeTable){
		int count=0;
		if(null==cafeTable){
			return 0;
		}
		SqlSession session = MyBatisUtil.openSession();
		try {
			CafeTableMapper mapper = session.getMapper(CafeTableMapper.class);
			count =mapper.insertCafeTable(cafeTable);
			session.commit();
		} finally {
			session.close();
		}
		return count;
	}
	
	public List<FoodUnitForm> findAllFoodUnitList(Long cafeShopSn){
		List<FoodUnitForm> foodUnitFormList =  new ArrayList<FoodUnitForm>();
		SqlSession session = MyBatisUtil.openSession();
		try {
			FoodUnitMapper mapper = session.getMapper(FoodUnitMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(FoodUnit.COL_CAFE_SHOP_SN,cafeShopSn);
			List<FoodUnit> foodUnitList = mapper.findAllFoodUnitList(map);
			FoodUnitForm newForm = null;
			for (FoodUnit foodUnit : foodUnitList) {
				newForm = new FoodUnitForm(foodUnit);
				foodUnitFormList.add(newForm);
			}
		} finally {
			session.close();
		}
		return foodUnitFormList;
	}
	
	public List<Food> findAllFoodList(Long cafeShopSn){
		SqlSession session = MyBatisUtil.openSession();
		try {
			FoodMapper mapper = session.getMapper(FoodMapper.class);
			Map<String,Object> map =new HashMap<String,Object>();
			map.put(Food.COL_CAFE_SHOP_SN,cafeShopSn);
			return mapper.findAllFoodList(map);
		} finally {
			session.close();
		}
	}
	
	public int countAllFoodUnitList(Long cafeShopSn){
		SqlSession session = MyBatisUtil.openSession();
		try {
			FoodUnitMapper mapper = session.getMapper(FoodUnitMapper.class);
			Map<String,Object> map =new HashMap<String,Object>();
			map.put(Food.COL_CAFE_SHOP_SN,cafeShopSn);
			return mapper.countAllFoodUnitList(map);
		} finally {
			session.close();
		}
	}
	
	public int countAllFoodList(Long cafeShopSn){
		SqlSession session = MyBatisUtil.openSession();
		try {
			FoodMapper mapper = session.getMapper(FoodMapper.class);
			Map<String,Object> map =new HashMap<String,Object>();
			map.put(Food.COL_CAFE_SHOP_SN,cafeShopSn);
			return mapper.countAllFoodList(map);
		} finally {
			session.close();
		}
	}
	
	public FoodUnit findFoodUnit(Long cafeShopSn,Long sn){
		SqlSession session = MyBatisUtil.openSession();
		try {
			FoodUnitMapper mapper = session.getMapper(FoodUnitMapper.class);
			Map<String,Object> map =new HashMap<String,Object>();
			map.put(FoodUnit.COL_CAFE_SHOP_SN,cafeShopSn);
			map.put(FoodUnit.COL_SN,sn);
			return mapper.findFoodUnit(map);
		} finally {
			session.close();
		}
	}
	
	public Food findFood(Long cafeShopSn,Long sn){
		SqlSession session = MyBatisUtil.openSession();
		try {
			FoodMapper mapper = session.getMapper(FoodMapper.class);
			Map<String,Object> map =new HashMap<String,Object>();
			map.put(Food.COL_CAFE_SHOP_SN,cafeShopSn);
			map.put(Food.COL_SN,sn);
			return mapper.findFood(map);
		} finally {
			session.close();
		}
	}
	
	public CafeTable findCafeTable(Long cafeShopSn,Long sn){
		SqlSession session = MyBatisUtil.openSession();
		try {
			CafeTableMapper mapper = session.getMapper(CafeTableMapper.class);
			Map<String,Object> map =new HashMap<String,Object>();
			map.put(Food.COL_CAFE_SHOP_SN,cafeShopSn);
			map.put(Food.COL_SN,sn);
			return mapper.findCafeTable(map);
		} finally {
			session.close();
		}
	}
	
	public Food findFoodViaName(Long cafeShopSn,String name){
		SqlSession session = MyBatisUtil.openSession();
		try {
			FoodMapper mapper = session.getMapper(FoodMapper.class);
			Map<String,Object> map =new HashMap<String,Object>();
			map.put(Food.COL_CAFE_SHOP_SN,cafeShopSn);
			map.put(Food.COL_NAME,name);
			return mapper.findFoodViaName(map);	
		} finally {
			session.close();
		}
	}
	
	public CafeTable findCafeTableViaName(Long cafeShopSn,String name){
		SqlSession session = MyBatisUtil.openSession();
		try {
			CafeTableMapper mapper = session.getMapper(CafeTableMapper.class);
			Map<String,Object> map =new HashMap<String,Object>();
			map.put(Food.COL_CAFE_SHOP_SN,cafeShopSn);
			map.put(Food.COL_NAME,name);
			return mapper.findCafeTableViaName(map);
		} finally {
			session.close();
		}
	}
	
	public int updateFoodUnit(FoodUnit foodUnit){
		int count=0;
		if(AppStrUtils.isEmpty(foodUnit.getName())){
			return 0;
		}
		SqlSession session = MyBatisUtil.openSession();
		try {
			FoodUnitMapper mapper = session.getMapper(FoodUnitMapper.class);
			count =mapper.updateFoodUnit(foodUnit);
			session.commit();
		} finally {
			session.close();
		}
		return count;
	}
	
	public int updateFood(Food food){
		int count=0;
		if(null==food){
			return 0;
		}
		SqlSession session = MyBatisUtil.openSession();
		try {
			FoodMapper mapper = session.getMapper(FoodMapper.class);
			count =mapper.updateFood(food);
			session.commit();
		} finally {
			session.close();
		}
		return count;
	}
	
	public int updateCafeTable(CafeTable cafeTable){
		int count=0;
		if(null==cafeTable){
			return 0;
		}
		SqlSession session = MyBatisUtil.openSession();
		try {
			CafeTableMapper mapper = session.getMapper(CafeTableMapper.class);
			count =mapper.updateCafeTable(cafeTable);
			session.commit();
		} finally {
			session.close();
		}
		return count;
	}
	 
	public List<FoodForm>findAllFoodFormList(Long cafeShopSn,Long foodSn,String orderField,String sort){
		List<FoodForm> foodFormList = new ArrayList<FoodForm>();
		SqlSession session = MyBatisUtil.openSession();
		try {
			FoodMapper mapper = session.getMapper(FoodMapper.class);
			Map<String,Object> params = new HashMap<String,Object>();
			if(AppStrUtils.isEmpty(sort)){
				sort = null;
			}
			params.put(Food.COL_SN, foodSn);
			params.put(Food.COL_CAFE_SHOP_SN,cafeShopSn);
			params.put(Constant.SQL_SORT,sort);
			params.put(Constant.SQL_ORDER_FIELD,orderField);
			List<Map<String,Object>> foodMapList= mapper.findAllFoodMapList(params);
			FoodForm foodForm = null;
			for (Map<String, Object> map : foodMapList) {
				foodForm = toFoodForm(map);
				foodFormList.add(foodForm);
			}
		} finally {
			session.close();
		}
		return foodFormList;
	}
	
	public List<CafeTableForm>findAllCafeTableFormListOrderByIdx(Long cafeShopSn){
		List<CafeTableForm> cafeTableFormList = new ArrayList<CafeTableForm>();
		SqlSession session = MyBatisUtil.openSession();
		try{
			CafeTableMapper mapper = session.getMapper(CafeTableMapper.class);
			Map<String,Object> params = new HashMap<String,Object>();
			params.put(Food.COL_CAFE_SHOP_SN,cafeShopSn);
			List<Map<String,Object>> tableMapList= mapper.findAllCafeTableListOrderByIdx(params);
			CafeTableForm cafeTableForm = null;
			for (Map<String, Object> map : tableMapList) {
				cafeTableForm = toCafeTableForm(map);
				cafeTableFormList.add(cafeTableForm);
			}
		} finally {
			session.close();
		}
		return cafeTableFormList;
	}
	
	private CafeTableForm toCafeTableForm(Map<String,Object> map){
		CafeTableForm newForm = null;
		if(null!=map){
			newForm = new CafeTableForm();
			newForm.setSn(AppNumUtils.toLong(map.get(CafeTable.DB_SN)));
			newForm.setName(AppStrUtils.toEmpty(map.get(CafeTable.DB_NAME)));
			newForm.setLocation(AppStrUtils.toEmpty(map.get(CafeTable.DB_LOCATION)));
			newForm.setPosition(AppNumUtils.toIntValue(map.get(CafeTable.DB_POSITION)));
			newForm.setTableGroupSn(AppNumUtils.toLong(map.get(CafeTable.DB_TABLE_GROUP_SN)));
			newForm.setTableGroupName(AppStrUtils.toEmpty(map.get(TableGroup.DB_TABLE_GROUP_NAME)));
			newForm.setGroupPos(AppNumUtils.toIntValue(map.get(TableGroup.DB_TABLE_GROUP_POS)));
			newForm.setLastUpdate(AppDateUtils.toYYYYMMDDHHMMStr((Date)map.get(CafeTable.DB_LAST_UPDATE)));
		}
		return newForm;
	}
	
	public List<CafeTableForm>findAllCafeTableFormList(Long cafeShopSn){
		List<CafeTableForm> cafeTableFormList = new ArrayList<CafeTableForm>();
		SqlSession session = MyBatisUtil.openSession();
		try {
			CafeTableMapper mapper = session.getMapper(CafeTableMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(Food.COL_CAFE_SHOP_SN,cafeShopSn);
			List<CafeTable> cafeTableList= mapper.findAllCafeTableList(map);
			CafeTableForm cafeTableForm = null;
			for (CafeTable cafeTable : cafeTableList) {
				cafeTableForm = new CafeTableForm(cafeTable);
				cafeTableFormList.add(cafeTableForm);
			}
		} finally {
			session.close();
		}
		return cafeTableFormList;
	}
	
	private FoodForm toFoodForm(Map<String,Object> map){
		if(null!=map){
			FoodForm foodForm = new FoodForm();
			foodForm.setSn(AppNumUtils.toLong(map.get(Food.DB_SN)));
			String foodUnitName = AppStrUtils.toEmpty(map.get(FoodUnit.DB_FOOD_UNIT_NAME));
			foodForm.setName(AppStrUtils.toEmpty(map.get(Food.DB_NAME))+"("+foodUnitName+")");
			foodForm.setPrice(AppNumUtils.toIntValue(map.get(Food.DB_PRICE)));
			foodForm.setProfit(AppNumUtils.toIntValue(map.get(Food.DB_PROFIT)));
			/*foodForm.setPriceStr(AppStrUtils.priceToString(foodForm.getPrice()));*/
			foodForm.setFoodUnitSn(AppNumUtils.toLong(map.get(Food.DB_FOOD_UNIT_SN)));
			foodForm.setMemo(AppStrUtils.toEmpty(map.get(Food.DB_MEMO)));
			foodForm.setLastUpdate(AppDateUtils.toYYYYMMDDHHMMStr((Date)(map.get(Food.DB_LAST_UPDATE))));
			foodForm.setFoodGroupSn(AppNumUtils.toLong(map.get(Food.DB_FOOD_GROUP_SN)));
			foodForm.setFoodGroupName(AppStrUtils.toEmpty(map.get(FoodGroup.DB_FOOD_GROUP_NAME)));
			return foodForm;
		}
		return null;
	}

	public int deleteFoodLogic(Long cafeShopSn,Long sn){
		int count=0;
		if(null==sn){
			return 0;
		}
		SqlSession session = MyBatisUtil.openSession();
		try {
			FoodMapper mapper = session.getMapper(FoodMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(Food.COL_CAFE_SHOP_SN, cafeShopSn);
			map.put(Food.COL_SN,sn);
			count =mapper.deleteFoodLogic(map);
			session.commit();
		} finally {
			session.close();
		}
		return count;
	}
	
	public int deleteCafeTableLogic(Long cafeShopSn,Long sn){
		int count=0;
		if(null==sn){
			return 0;
		}
		SqlSession session = MyBatisUtil.openSession();
		try {
			CafeTableMapper mapper = session.getMapper(CafeTableMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(Food.COL_CAFE_SHOP_SN, cafeShopSn);
			map.put(Food.COL_SN,sn);
			count =mapper.deleteCafeTableLogic(map);
			session.commit();
		} finally {
			session.close();
		}
		return count;
	}
	
	public int insertOtherOutlayTran(OtherOutlayTran otherOutlayTran){
		int count=0;
		if(null==otherOutlayTran){
			return 0;
		}
		SqlSession session = MyBatisUtil.openSession();
		try {
			OtherOutlayTranMapper mapper = session.getMapper(OtherOutlayTranMapper.class);
			count =mapper.insertOtherOutlayTran(otherOutlayTran);
			session.commit();
		} finally {
			session.close();
		}
		return count;
	}
	
	public int insertOtherOutlay(OtherOutlay otherOutlay){
		int count=0;
		if(null==otherOutlay){
			return 0;
		}
		SqlSession session = MyBatisUtil.openSession();
		try {
			OtherOutlayMapper mapper = session.getMapper(OtherOutlayMapper.class);
			count =mapper.insertOtherOutlay(otherOutlay);
			session.commit();
		} finally {
			session.close();
		}
		return count;
	}
	
	public OtherOutlay findOtherOutlayViaName(Long cafeShopSn,String name){
		if(AppStrUtils.isEmpty(name)){
			return null;
		}
		SqlSession session = MyBatisUtil.openSession();
		try {
			OtherOutlayMapper mapper = session.getMapper(OtherOutlayMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(OtherOutlay.COL_CAFE_SHOP_SN,cafeShopSn);
			map.put(OtherOutlay.COL_NAME,name);
			return mapper.findOtherOutlayByName(map);
		} finally {
			session.close();
		}
	}
	
	public List<OtherOutlayTranForm> findLimitOtherOutlayTranList(OtherOutlayTranSearch search){
		List<OtherOutlayTranForm> otherOutlayTranList = new ArrayList<OtherOutlayTranForm>();
		if(null==search){
			return otherOutlayTranList;
		}
		SqlSession session = MyBatisUtil.openSession();
		try {
			OtherOutlayTranMapper mapper = session.getMapper(OtherOutlayTranMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(OtherOutlayTran.COL_CAFE_SHOP_SN,search.getCafeShopSn());
			map.put(OtherOutlayTran.COL_START_TIME,search.getStartDate());
			map.put(OtherOutlayTran.COL_END_TIME,search.getEndDate());
			map.put(Constant.SQL_ORDER_FIELD,search.getOrderField());
			map.put(Constant.SQL_SORT,search.getSort());
			map.put(Constant.SQL_OFFSET,search.getOffset());
			map.put(Constant.SQL_LIMIT,search.getLimit());
			map.put(OtherOutlayTran.COL_OTHER_OUTLAY_SN,search.getOtherOutlaySn());
			if(AppStrUtils.isEmpty(search.getNameSearch())){
				map.put(OtherOutlayTran.COL_NAME_SEARCH,null);
			}else{
				map.put(OtherOutlayTran.COL_NAME_SEARCH,AppStrUtils.SQL_PERCENT
						+search.getNameSearch()+AppStrUtils.SQL_PERCENT);
			}
			List<Map<String,Object>> otherOutlayTranMapList=mapper.findLimitOtherOutlayTranList(map);
			OtherOutlayTranForm otherOutlayTranForm = null;
			for (Map<String, Object> map2 : otherOutlayTranMapList) {
				otherOutlayTranForm = toOtherOutlayTranForm(map2);
				otherOutlayTranList.add(otherOutlayTranForm);
			}
		} finally {
			session.close();
		}
		return otherOutlayTranList;
	}
	
	public int countAllOtherOutlayTranList(OtherOutlayTranSearch search){
		int count =0;
		if(null==search){
			return 0;
		}
		SqlSession session = MyBatisUtil.openSession();
		try {
			OtherOutlayTranMapper mapper = session.getMapper(OtherOutlayTranMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(OtherOutlayTran.COL_CAFE_SHOP_SN,search.getCafeShopSn());
			map.put(OtherOutlayTran.COL_START_TIME,search.getStartDate());
			map.put(OtherOutlayTran.COL_END_TIME,search.getEndDate());
			map.put(OtherOutlayTran.COL_OTHER_OUTLAY_SN,search.getOtherOutlaySn());
			if(AppStrUtils.isEmpty(search.getNameSearch())){
				map.put(OtherOutlayTran.COL_NAME_SEARCH,null);
			}else{
				map.put(OtherOutlayTran.COL_NAME_SEARCH,AppStrUtils.SQL_PERCENT
						+search.getNameSearch()+AppStrUtils.SQL_PERCENT);
			}
			count = mapper.countAllOtherOutlayTranList(map);
		} finally {
			session.close();
		}
		return count;
	}
	
	private OtherOutlayTranForm toOtherOutlayTranForm(Map<String,Object> map){
		if(null!=map){
			OtherOutlayTranForm newForm = new OtherOutlayTranForm();
			newForm.setSn(AppNumUtils.toLong(map.get(OtherOutlayTran.DB_SN)));
			newForm.setNumOfOutlay(AppNumUtils.toIntValue(map.get(OtherOutlayTran.DB_NUM_OF_OUTLAY)));
			newForm.setTotalPrice(AppNumUtils.toIntValue(map.get(OtherOutlayTran.DB_TOTAL_PRICE)));
			newForm.setTotalPriceStr(AppStrUtils.priceToString(newForm.getTotalPrice()));
			newForm.setOtherOutlaySn(AppNumUtils.toLong(map.get(OtherOutlayTran.DB_OTHER_OUTLAY_SN)));
			newForm.setOtherOutlayName(AppStrUtils.toEmpty(map.get(OtherOutlay.DB_NAME)));
			newForm.setMemo(AppStrUtils.toEmpty(map.get(OtherOutlayTran.DB_MEMO)));
			newForm.setTimeTransactionStr(AppDateUtils.toYYYYMMDDHHMMStr((Date)map.get(OtherOutlayTran.DB_TIME_TRANSACTION)));
			return newForm;
		}
		return null;
	}
	
	public OtherOutlayTranForm findOtherOutlayTranForm(Long cafeShopSn,Long otherOutlayTranSn){
		if(null==otherOutlayTranSn){
			return null;
		}
		SqlSession session = MyBatisUtil.openSession();
		try {
			OtherOutlayTranMapper mapper = session.getMapper(OtherOutlayTranMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(OtherOutlayTran.COL_CAFE_SHOP_SN,cafeShopSn);
			map.put(OtherOutlayTran.COL_SN,otherOutlayTranSn);
			Map<String,Object> otherOutlayTranMap = mapper.findOtherOutlayTranMap(map);
			OtherOutlayTranForm otherOutlayTranForm = toOtherOutlayTranForm(otherOutlayTranMap);
			return otherOutlayTranForm;
		} finally {
			session.close();
		}
	}
	
	public int updateOtherOutlayTran(OtherOutlayTran otherOutlayTran){
		int count =0;
		if(null==otherOutlayTran){
			return 0;
		}
		SqlSession session = MyBatisUtil.openSession();
		try {
			OtherOutlayTranMapper mapper = session.getMapper(OtherOutlayTranMapper.class);
			count = mapper.updateOtherOutlayTran(otherOutlayTran);
			session.commit();
		} finally {
			session.close();
		}
		return count;
	}
	
	public int deleteOtherOutlayTran(Long cafeShopSn,Long sn){
		int count =0;
		if(null==sn){
			return 0;
		}
		SqlSession session = MyBatisUtil.openSession();
		try {
			OtherOutlayTranMapper mapper = session.getMapper(OtherOutlayTranMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(OtherOutlayTran.COL_CAFE_SHOP_SN,cafeShopSn);
			map.put(OtherOutlayTran.COL_SN,sn);
			count = mapper.deleteOtherOutlayTran(map);
			session.commit();
		} finally {
			session.close();
		}
		return count;
	}
	
	public List<OtherOutlay>findLimitAllOtherOutlayList(OtherOutlayTranSearch search){
		if(null==search){
			return new ArrayList<OtherOutlay>();
		}
		SqlSession session = MyBatisUtil.openSession();
		try {
			OtherOutlayMapper mapper = session.getMapper(OtherOutlayMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(OtherOutlayTran.COL_CAFE_SHOP_SN,search.getCafeShopSn());
			if(!AppStrUtils.isEmpty(search.getKeyword())){
				map.put(Constant.SQL_KEYWORD,AppStrUtils.SQL_PERCENT+ search.getKeyword()+AppStrUtils.SQL_PERCENT);
			}else{
				map.put(Constant.SQL_KEYWORD,null);
			}
			map.put(Constant.SQL_LIMIT,search.getLimit());
			map.put(Constant.SQL_OFFSET,search.getOffset());
			return mapper.findLimitAllOtherOutlayList(map);
		} finally {
			session.close();
		}
	}
	
	public List<Map<String,Object>>findAllFoodMapListForSuggestion(FoodSearch search){
		if(null==search){
			return new ArrayList<Map<String,Object>>();
		}
		SqlSession session = MyBatisUtil.openSession();
		try {
			FoodMapper mapper = session.getMapper(FoodMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(Food.COL_CAFE_SHOP_SN,search.getCafeShopSn());
			if(!AppStrUtils.isEmpty(search.getKeyword())){
				map.put(Constant.SQL_KEYWORD,AppStrUtils.SQL_PERCENT+ search.getKeyword()+AppStrUtils.SQL_PERCENT);
			}else{
				map.put(Constant.SQL_KEYWORD,null);
			}
			map.put(Constant.SQL_LIMIT,search.getLimit());
			map.put(Constant.SQL_OFFSET,search.getOffset());
			return mapper.findAllFoodMapListForSuggestion(map);
		} finally {
			session.close();
		}
	}
	
	public int calculateMoneyOfOtherOutlayTran(OtherOutlayTranSearch search){
		SqlSession session = MyBatisUtil.openSession();
		try {
			OtherOutlayTranMapper mapper = session.getMapper(OtherOutlayTranMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(OtherOutlayTran.COL_CAFE_SHOP_SN,search.getCafeShopSn());
			map.put(OtherOutlayTran.COL_START_TIME,search.getStartDate());
			map.put(OtherOutlayTran.COL_END_TIME,search.getEndDate());
			map.put(OtherOutlayTran.COL_OTHER_OUTLAY_SN,search.getOtherOutlaySn());
			if(AppStrUtils.isEmpty(search.getNameSearch())){
				map.put(OtherOutlayTran.COL_NAME_SEARCH,null);
			}else{
				map.put(OtherOutlayTran.COL_NAME_SEARCH,AppStrUtils.SQL_PERCENT
						+search.getNameSearch()+AppStrUtils.SQL_PERCENT);
			}
			return mapper.calculateMoneyOfOtherOutlayTran(map);
		} finally {
			session.close();
		}
	}
	
	public List<TableGroup> findAllTableGroupList(Long cafeShopSn){
		SqlSession session = MyBatisUtil.openSession();
		try {
			TableGroupMapper mapper = session.getMapper(TableGroupMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(OtherOutlayTran.COL_CAFE_SHOP_SN,cafeShopSn);
			return mapper.findAllTableGroupList(map);
		} finally {
			session.close();
		}
	}
	
	public List<CafeTable>findAllCafeTableInGroup(Long cafeShopSn,Long tableGroupSn){
		SqlSession session = MyBatisUtil.openSession();
		try {
			CafeTableMapper mapper = session.getMapper(CafeTableMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(CafeTable.COL_CAFE_SHOP_SN,cafeShopSn);
			map.put(CafeTable.COL_TABLE_GROUP_SN,tableGroupSn);
			return mapper.findAllCafeTableInGroup(map);
		} finally {
			session.close();
		}
	}
	
	public List<FoodGroup> findAllFoodGroupList(Long cafeShopSn){
		SqlSession session = MyBatisUtil.openSession();
		try {
			FoodGroupMapper mapper = session.getMapper(FoodGroupMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(FoodGroup.COL_CAFE_SHOP_SN,cafeShopSn);
			return mapper.findAllFoodGroupList(map);
		} finally {
			session.close();
		}
	}
	
	public int countAllTypeOfOtherOutlayList(OtherOutlayTranSearch search){
		if(null==search){
			return 0;
		}
		SqlSession session = MyBatisUtil.openSession();
		try {
			OtherOutlayTranMapper mapper = session.getMapper(OtherOutlayTranMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(FoodGroup.COL_CAFE_SHOP_SN,search.getCafeShopSn());
			map.put(OtherOutlayTran.COL_START_TIME,search.getStartDate());
			map.put(OtherOutlayTran.COL_END_TIME,search.getEndDate());
			map.put(OtherOutlayTran.COL_OTHER_OUTLAY_SN,search.getOtherOutlaySn());
			if(AppStrUtils.isEmpty(search.getNameSearch())){
				map.put(OtherOutlayTran.COL_NAME_SEARCH,null);
			}else{
				map.put(OtherOutlayTran.COL_NAME_SEARCH,AppStrUtils.SQL_PERCENT
						+search.getNameSearch()+AppStrUtils.SQL_PERCENT);
			}
			return mapper.countAllTypeOfOtherOutlayList(map);
		} finally {
			session.close();
		}
	}
	
	public List<OtherOutlayTranForm> findLimitOtherOutlayTranStatisticList(OtherOutlayTranSearch search){
		List<OtherOutlayTranForm> otherOutlayTranList = new ArrayList<OtherOutlayTranForm>();
		if(null==search){
			return otherOutlayTranList;
		}
		SqlSession session = MyBatisUtil.openSession();
		try {
			OtherOutlayTranMapper mapper = session.getMapper(OtherOutlayTranMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(OtherOutlayTran.COL_CAFE_SHOP_SN,search.getCafeShopSn());
			map.put(OtherOutlayTran.COL_START_TIME,search.getStartDate());
			map.put(OtherOutlayTran.COL_END_TIME,search.getEndDate());
			map.put(Constant.SQL_OFFSET,search.getOffset());
			map.put(Constant.SQL_LIMIT,search.getLimit());
			map.put(OtherOutlayTran.COL_OTHER_OUTLAY_SN,search.getOtherOutlaySn());
			if(AppStrUtils.isEmpty(search.getNameSearch())){
				map.put(OtherOutlayTran.COL_NAME_SEARCH,null);
			}else{
				map.put(OtherOutlayTran.COL_NAME_SEARCH,AppStrUtils.SQL_PERCENT
						+search.getNameSearch()+AppStrUtils.SQL_PERCENT);
			}
			List<Map<String,Object>> otherOutlayTranMapList=mapper.findLimitOtherOutlayTranStatisticList(map);
			OtherOutlayTranForm otherOutlayTranForm = null;
			for (Map<String, Object> map2 : otherOutlayTranMapList) {
				otherOutlayTranForm = toOtherOutlayTranForm(map2);
				otherOutlayTranList.add(otherOutlayTranForm);
			}
		} finally {
			session.close();
		}
		return otherOutlayTranList;
	}
	
	public List<CafeShop> findAllCafeShopList(){
		SqlSession session = MyBatisUtil.openSession();
		try {
			CafeShopMapper mapper = session.getMapper(CafeShopMapper.class);
			return mapper.findAllCafeShopList();
		} finally {
			session.close();
		}
	}
}
