package com.cafe.service.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.cafe.search.FoodSearch;
import com.cafe.search.OtherOutlayTranSearch;

/**
* The login action.
*
* @author  	phap.nguyen
* @version 	1.0
* @since   	2015-05-27 
* @update   
*/
public interface StoreService {
	public int insertFoodUnit(Long cafeShopSn,String name);
	public List<FoodUnitForm> findAllFoodUnitList(Long cafeShopSn);
	public int countAllFoodUnitList(Long cafeShopSn);
	public FoodUnit findFoodUnit(Long cafeShopSn,Long sn);
	public int updateFoodUnit(FoodUnit foodUnit);
	public int insertFood(Food food);
	public int countAllFoodList(Long cafeShopSn);
	public Food findFood(Long cafeShopSn,Long sn);
	public int updateFood(Food food);
	public List<Food> findAllFoodList(Long cafeShopSn);
	public List<FoodForm>findAllFoodFormList(Long cafeShopSn,Long foodSn,String orderField,String sort);
	public Food findFoodViaName(Long cafeShopSn,String name);
	public int deleteFoodLogic(Long cafeShopSn,Long sn);
	public CafeTable findCafeTableViaName(Long cafeShopSn,String name);
	public int updateCafeTable(CafeTable cafeTable);
	public int insertCafeTable(CafeTable cafeTable);
	public CafeTable findCafeTable(Long cafeShopSn,Long sn);
	public List<CafeTableForm>findAllCafeTableFormList(Long cafeShopSn);
	public int deleteCafeTableLogic(Long cafeShopSn,Long sn);
	public int insertOtherOutlayTran(OtherOutlayTran otherOutlayTran);
	public int insertOtherOutlay(OtherOutlay otherOutlay);
	public OtherOutlay findOtherOutlayViaName(Long cafeShopSn,String name);
	public List<OtherOutlayTranForm> findLimitOtherOutlayTranList(OtherOutlayTranSearch search);
	public int countAllOtherOutlayTranList(OtherOutlayTranSearch search);
	public OtherOutlayTranForm findOtherOutlayTranForm(Long cafeShopSn,Long otherOutlayTranSn);
	public int updateOtherOutlayTran(OtherOutlayTran otherOutlayTran);
	public int deleteOtherOutlayTran(Long cafeShopSn,Long sn);
	public List<OtherOutlay>findLimitAllOtherOutlayList(OtherOutlayTranSearch search);
	public List<Map<String,Object>>findAllFoodMapListForSuggestion(FoodSearch search);
	public int calculateMoneyOfOtherOutlayTran(OtherOutlayTranSearch search);
	public List<CafeTableForm>findAllCafeTableFormListOrderByIdx(Long cafeShopSn);
	public List<TableGroup> findAllTableGroupList(Long cafeShopSn);
	public List<CafeTable>findAllCafeTableInGroup(Long cafeShopSn,Long tableGroupSn);
	public List<FoodGroup> findAllFoodGroupList(Long cafeShopSn);
	public int countAllTypeOfOtherOutlayList(OtherOutlayTranSearch search);
	public List<OtherOutlayTranForm> findLimitOtherOutlayTranStatisticList(OtherOutlayTranSearch search);
	public List<CafeShop> findAllCafeShopList();
}
