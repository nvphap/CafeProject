package com.cafe.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.cafe.dto.StatisticByMonth;
import com.cafe.dto.TableStatistic;
import com.cafe.dto.StatisticByDate;
import com.cafe.dto.StatisticByDay;
import com.cafe.dto.StatisticByHour;
import com.cafe.dto.StatisticByTable;
import com.cafe.dto.StatisticByTime;
import com.cafe.entity.CafeOrder;
import com.cafe.entity.CafeTable;
import com.cafe.entity.Food;
import com.cafe.entity.FoodUnit;
import com.cafe.form.CafeOrderForm;
import com.cafe.mapper.CafeOrderMapper;
import com.cafe.search.CafeOrderSearch;
import com.cafe.service.interfaces.OrderService;
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
// This service handle STAFF, CARE_FOR_STAFF table
public class OrderServiceImpl implements OrderService {
	
	public int countAllCafeOrderListStatusPay(CafeOrderSearch search){
		SqlSession session = MyBatisUtil.openSession();
		try {
			CafeOrderMapper mapper = session.getMapper(CafeOrderMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(CafeOrder.COL_CAFE_SHOP_SN,search.getCafeShopSn());
			map.put(CafeOrder.COL_START_TIME,search.getStartTime());
			map.put(CafeOrder.COL_END_TIME,search.getEndTime());
			map.put(Constant.SQL_LIMIT,search.getLimit());
			map.put(Constant.SQL_OFFSET,search.getOffset());
			map.put(CafeOrder.COL_CAFE_TABLE_SN,search.getCafeTableSn());
			return mapper.countAllCafeOrderListStatusPay(map);
		} finally {
			session.close();
		}
	}
	
	public int calculateTotalMoney(CafeOrderSearch search){
		SqlSession session = MyBatisUtil.openSession();
		try {
			CafeOrderMapper mapper = session.getMapper(CafeOrderMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(CafeOrder.COL_CAFE_SHOP_SN,search.getCafeShopSn());
			map.put(CafeOrder.COL_START_TIME,search.getStartTime());
			map.put(CafeOrder.COL_END_TIME,search.getEndTime());
			map.put(CafeOrder.COL_CAFE_TABLE_SN,search.getCafeTableSn());
			return mapper.calculateTotalMoney(map);
		} finally {
			session.close();
		}
	}
	
	public int calculateTotalExpectedMoney(CafeOrderSearch search){
		SqlSession session = MyBatisUtil.openSession();
		try {
			CafeOrderMapper mapper = session.getMapper(CafeOrderMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(CafeOrder.COL_CAFE_SHOP_SN,search.getCafeShopSn());
			map.put(CafeOrder.COL_START_TIME,search.getStartTime());
			map.put(CafeOrder.COL_END_TIME,search.getEndTime());
			map.put(CafeOrder.COL_CAFE_TABLE_SN,search.getCafeTableSn());
			return mapper.calculateTotalExpectedMoney(map);
		} finally {
			session.close();
		}
	}
	
	public List<CafeOrderForm> findLimitCafeOrderListStatusPay(CafeOrderSearch search){
		List<CafeOrderForm> cafeOrderFormList = new ArrayList<CafeOrderForm>();
		SqlSession session = MyBatisUtil.openSession();
		try {
			CafeOrderMapper mapper = session.getMapper(CafeOrderMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(CafeOrder.COL_CAFE_SHOP_SN,search.getCafeShopSn());
			map.put(CafeOrder.COL_START_TIME,search.getStartTime());
			map.put(CafeOrder.COL_END_TIME,search.getEndTime());
			map.put(Constant.SQL_LIMIT,search.getLimit());
			map.put(Constant.SQL_OFFSET,search.getOffset());
			map.put(CafeOrder.COL_CAFE_TABLE_SN,search.getCafeTableSn());
			map.put(Constant.SQL_ORDER_FIELD,search.getOrderField());
			map.put(Constant.SQL_SORT,search.getSort());
			List<Map<String,Object>> cafeOrderMapList = mapper.findLimitCafeOrderListStatusPay(map);
			CafeOrderForm newOrder = null;
			Map<Long,Long> orderMap = new HashMap<Long,Long>();
			int count =0;
			for (Map<String, Object> map2 : cafeOrderMapList) {
				newOrder = toCafeOrderForm(map2);
				if(null!=newOrder){
					count=getNextOrder(orderMap,newOrder.getCafeTableSn());
					newOrder.setOrderIdx(count);
					cafeOrderFormList.add(newOrder);
				}
			}
			
		} finally {
			session.close();
		}
		return cafeOrderFormList;
	}
	
	
	public List<CafeOrderForm> findAllCafeOrderListStatusOrder(Long cafeShopSn,Date startTime,Date endTime,Long cafeTableSn){
		List<CafeOrderForm> cafeOrderFormList = new ArrayList<CafeOrderForm>();
		SqlSession session = MyBatisUtil.openSession();
		try {
			CafeOrderMapper mapper = session.getMapper(CafeOrderMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(CafeOrder.COL_CAFE_SHOP_SN,cafeShopSn);
			map.put(CafeOrder.COL_START_TIME,startTime);
			map.put(CafeOrder.COL_END_TIME,endTime);
			map.put(CafeOrder.COL_CAFE_TABLE_SN,cafeTableSn);
			List<Map<String,Object>> cafeOrderMapList = mapper.findAllCafeOrderListStatusOrder(map);
			CafeOrderForm newOrder = null;
			Map<Long,Long> orderMap = new HashMap<Long,Long>();
			int count =0;
			Date now = Calendar.getInstance().getTime();
			Date orderTime = null;
			for (Map<String, Object> map2 : cafeOrderMapList) {
				newOrder = toCafeOrderForm(map2);
				if(null!=newOrder){
					count=getNextOrder(orderMap,newOrder.getCafeTableSn());
					newOrder.setOrderIdx(count);
					orderTime = AppDateUtils.setTimeToEndOfDay(newOrder.getOrderTime());
					if(orderTime.before(now)){// in past
						newOrder.setInPast(1);
					}else{
						newOrder.setInPast(0);
					}
					cafeOrderFormList.add(newOrder);
				}
			}
			
		} finally {
			session.close();
		}
		return cafeOrderFormList;
	}
	
	private int getNextOrder(Map<Long,Long> orderMap, Long cafeTableSn){
		Object orderIdx = orderMap.get(cafeTableSn);
		int nextValue = AppNumUtils.toIntValue(orderIdx)+1;
		orderMap.put(cafeTableSn,new Long(nextValue));
		return nextValue;
	}
	
	private CafeOrderForm toCafeOrderForm(Map<String,Object> map){
		if(null!=map){
			CafeOrderForm newOrder = new CafeOrderForm();
			newOrder.setSn(AppNumUtils.toLong(map.get(CafeOrder.DB_SN)));
			newOrder.setCafeTableSn(AppNumUtils.toLong(map.get(CafeOrder.DB_CAFE_TABLE_SN)));
			newOrder.setCafeTableName(AppStrUtils.toEmpty(map.get(CafeTable.DB_CAFE_TABLE_NAME)));
			newOrder.setFoodSn(AppNumUtils.toLong(map.get(CafeOrder.DB_FOOD_SN)));
			newOrder.setFoodName(AppStrUtils.toEmpty(map.get(Food.DB_FOOD_NAME)));
			newOrder.setPrice(AppNumUtils.toIntValue(map.get(CafeOrder.DB_PRICE)));
			newOrder.setPriceStr(AppStrUtils.priceToString(newOrder.getPrice()));
			newOrder.setNumOfFood(AppNumUtils.toIntValue(map.get(CafeOrder.DB_NUM_OF_FOOD)));
			newOrder.setMemo(AppStrUtils.toEmpty(map.get(CafeOrder.DB_MEMO)));
			newOrder.setFoodUnitSn(AppNumUtils.toLong(map.get(CafeOrder.DB_FOOD_UNIT_SN)));
			newOrder.setFoodUnitName(AppStrUtils.toEmpty(map.get(FoodUnit.DB_FOOD_UNIT_NAME)));
			newOrder.setTotalMoney(AppNumUtils.toIntValue(map.get(CafeOrder.DB_TOTAL_MONEY)));
			newOrder.setTotalMoneyStr(AppStrUtils.priceToString(newOrder.getTotalMoney()));
			newOrder.setOrderTime((Date)map.get(CafeOrder.DB_ORDER_TIME));
			String orderTimeStr  = AppDateUtils.toYYYYMMDDHHMMStr(newOrder.getOrderTime());
			newOrder.setOrderTimeStr(orderTimeStr);
			String orderDateStr = AppDateUtils.toYYYYMMDDStr(newOrder.getOrderTime());
			newOrder.setOrderDateStr(orderDateStr);
			Object payTime = map.get(CafeOrder.DB_PAY_TIME);
			if(null!=payTime){
				String payTimeStr = AppDateUtils.toYYYYMMDDHHMMStr((Date)payTime);
				newOrder.setPayTimeStr(payTimeStr);
			}
			newOrder.setDiscount(AppNumUtils.toIntValue(map.get(CafeOrder.DB_DISCOUNT)));
			newOrder.setNumOfPrint(AppNumUtils.toIntValue(map.get(CafeOrder.DB_NUM_OF_PRINT)));
			if(newOrder.getTotalMoney()!=newOrder.getPrice()*newOrder.getNumOfFood()){
				newOrder.setIsDiscount(1);
			}else{
				newOrder.setIsDiscount(0);
			}
			return newOrder;
		}
		return null;
	}
	
	public int insertNewCafeOrder(CafeOrder newCafeOrder){
		int count =0;
		if(null==newCafeOrder){
			return 0;
		}
		SqlSession session = MyBatisUtil.openSession();
		try {
			CafeOrderMapper mapper = session.getMapper(CafeOrderMapper.class);
			count = mapper.insertNewCafeOrder(newCafeOrder);
			session.commit();
		} finally {
			session.close();
		}
		return count;
	}
	
	public int deleteCafeOrder(Long cafeShopSn,Long cafeOrderSn){
		int count =0;
		if(null==cafeOrderSn){
			return 0;
		}
		SqlSession session = MyBatisUtil.openSession();
		try {
			CafeOrderMapper mapper = session.getMapper(CafeOrderMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(CafeOrder.COL_CAFE_SHOP_SN,cafeShopSn);
			map.put(CafeOrder.COL_SN,cafeOrderSn);
			count = mapper.deleteCafeOrder(map);
			session.commit();
		} finally {
			session.close();
		}
		return count;
	}
	
	public int updateCafeOrder(CafeOrder cafeOrder){
		int count =0;
		if(null==cafeOrder){
			return 0;
		}
		SqlSession session = MyBatisUtil.openSession();
		try {
			CafeOrderMapper mapper = session.getMapper(CafeOrderMapper.class);
			count = mapper.updateCafeOrder(cafeOrder);
			session.commit();
		} finally {
			session.close();
		}
		return count;
	}
	
	public int payMoneyCafeOrder(Long cafeShopSn,Long cafeOrderSn, Date payTime,Long lastUpdateStaffSn){
		int count =0;
		if(null==cafeOrderSn){
			return 0;
		}
		SqlSession session = MyBatisUtil.openSession();
		try {
			CafeOrderMapper mapper = session.getMapper(CafeOrderMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(CafeOrder.COL_SN,cafeOrderSn);
			map.put(CafeOrder.COL_CAFE_SHOP_SN,cafeShopSn);
			map.put(CafeOrder.COL_PAY_TIME,payTime);
			map.put(CafeOrder.COL_LAST_UPDATE_STAFF_SN,lastUpdateStaffSn);
			count = mapper.payMoneyCafeOrder(map);
			session.commit();
		} finally {
			session.close();
		}
		return count;
	}
	
	public List<TableStatistic> findCafeOrderStatistic(Long cafeShopSn,Date startTime,Date endTime){
		List<TableStatistic> statisticList = new ArrayList<TableStatistic>();
		SqlSession session = MyBatisUtil.openSession();
		try {
			CafeOrderMapper mapper = session.getMapper(CafeOrderMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(CafeOrder.COL_CAFE_SHOP_SN,cafeShopSn);
			map.put(CafeOrder.COL_START_TIME,startTime);
			map.put(CafeOrder.COL_END_TIME,endTime);
			List<Map<Long,Long>> statisticOrderMapList = mapper.findStatisticOfAllTable(map);
			TableStatistic newStatistic = null;
			for (Map<Long, Long> map2 : statisticOrderMapList) {
				newStatistic = new TableStatistic();
				newStatistic.setCafeTableSn(AppNumUtils.toLong(map2.get(CafeOrder.DB_CAFE_TABLE_SN)));
				newStatistic.setNumOfOrder(AppNumUtils.toIntValue(map2.get(CafeOrder.DB_NUM_OF_ORDER)));
				newStatistic.setTotalMoney(AppNumUtils.toIntValue(map2.get(CafeOrder.DB_TOTAL_MONEY)));
				statisticList.add(newStatistic);
			}
		} finally {
			session.close();
		}
		return statisticList;
	}
	
	public int countCafeOrderOneTable(Long cafeShopSn,Long cafeTableSn,Date startTime, Date endTime){
		SqlSession session = MyBatisUtil.openSession();
		try {
			CafeOrderMapper mapper = session.getMapper(CafeOrderMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(CafeOrder.COL_CAFE_SHOP_SN,cafeShopSn);
			map.put(CafeOrder.COL_START_TIME,startTime);
			map.put(CafeOrder.COL_END_TIME,endTime);
			map.put(CafeOrder.COL_CAFE_TABLE_SN,cafeTableSn);
			return mapper.countCafeOrderOneTable(map);
		} finally {
			session.close();
		}
	}
	
	public int countAllNumOfFoodStatusPay(CafeOrderSearch search){
		SqlSession session = MyBatisUtil.openSession();
		try {
			CafeOrderMapper mapper = session.getMapper(CafeOrderMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(CafeOrder.COL_CAFE_SHOP_SN,search.getCafeShopSn());
			map.put(CafeOrder.COL_START_TIME,search.getStartTime());
			map.put(CafeOrder.COL_END_TIME,search.getEndTime());
			map.put(CafeOrder.COL_CAFE_TABLE_SN,search.getCafeTableSn());
			return mapper.countAllNumOfFoodStatusPay(map);
		} finally {
			session.close();
		}
	}
	
	public List<CafeOrderForm> findLimitCafeOrderListGroupByFood(CafeOrderSearch search){
		List<CafeOrderForm> cafeOrderList = new ArrayList<CafeOrderForm>();
		SqlSession session = MyBatisUtil.openSession();
		try {
			CafeOrderMapper mapper = session.getMapper(CafeOrderMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(CafeOrder.COL_CAFE_SHOP_SN,search.getCafeShopSn());
			map.put(CafeOrder.COL_START_TIME,search.getStartTime());
			map.put(CafeOrder.COL_END_TIME,search.getEndTime());
			map.put(Constant.SQL_LIMIT,search.getLimit());
			map.put(Constant.SQL_OFFSET,search.getOffset());
			map.put(Constant.SQL_ORDER_FIELD,search.getOrderField());
			map.put(Constant.SQL_SORT,search.getSort());
			List<Map<String,Object>> statisticMapList = mapper.findLimitCafeOrderListGroupByFood(map);
			CafeOrderForm newForm = null;
			for (Map<String, Object> map2 : statisticMapList) {
				newForm = fromGroupByFoodToForm(map2);
				cafeOrderList.add(newForm);
			}
		} finally {
			session.close();
		}
		return cafeOrderList;
	}
	
	public List<StatisticByDate> findLimitCafeOrderListGroupByDate(CafeOrderSearch search){
		List<StatisticByDate> statisticByDateList = new ArrayList<StatisticByDate>();
		SqlSession session = MyBatisUtil.openSession();
		try {
			CafeOrderMapper mapper = session.getMapper(CafeOrderMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(CafeOrder.COL_CAFE_SHOP_SN,search.getCafeShopSn());
			map.put(CafeOrder.COL_START_TIME,search.getStartTime());
			map.put(CafeOrder.COL_END_TIME,search.getEndTime());
			map.put(Constant.SQL_LIMIT,search.getLimit());
			map.put(Constant.SQL_OFFSET,search.getOffset());
			map.put(Constant.SQL_ORDER_FIELD,search.getOrderField());
			map.put(Constant.SQL_SORT,search.getSort());
			List<Map<String,Object>> statisticMapList = mapper.findLimitCafeOrderListGroupByDate(map);
			StatisticByDate newForm = null;
			for (Map<String, Object> map2 : statisticMapList) {
				newForm = fromGroupByDateToForm(map2);
				statisticByDateList.add(newForm);
			}
		} finally {
			session.close();
		}
		return statisticByDateList;
	}
	
	public int countRecordCafeOrderListGroupByDate(CafeOrderSearch search){
		SqlSession session = MyBatisUtil.openSession();
		try {
			CafeOrderMapper mapper = session.getMapper(CafeOrderMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(CafeOrder.COL_CAFE_SHOP_SN,search.getCafeShopSn());
			map.put(CafeOrder.COL_START_TIME,search.getStartTime());
			map.put(CafeOrder.COL_END_TIME,search.getEndTime());
			map.put(Constant.SQL_LIMIT,search.getLimit());
			map.put(Constant.SQL_OFFSET,search.getOffset());
			return  mapper.countRecordCafeOrderListGroupByDate(map);
		} finally {
			session.close();
		}
	}
	
	public int countRecordCafeOrderListGroupByTable(CafeOrderSearch search){
		SqlSession session = MyBatisUtil.openSession();
		try {
			CafeOrderMapper mapper = session.getMapper(CafeOrderMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(CafeOrder.COL_CAFE_SHOP_SN,search.getCafeShopSn());
			map.put(CafeOrder.COL_START_TIME,search.getStartTime());
			map.put(CafeOrder.COL_END_TIME,search.getEndTime());
			map.put(Constant.SQL_LIMIT,search.getLimit());
			map.put(Constant.SQL_OFFSET,search.getOffset());
			return  mapper.countRecordCafeOrderListGroupByTable(map);
		} finally {
			session.close();
		}
	}
	
	public List<StatisticByTable> findLimitCafeOrderListGroupByTable(CafeOrderSearch search){
		List<StatisticByTable> statisticByTableList = new ArrayList<StatisticByTable>();
		SqlSession session = MyBatisUtil.openSession();
		try {
			CafeOrderMapper mapper = session.getMapper(CafeOrderMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(CafeOrder.COL_CAFE_SHOP_SN,search.getCafeShopSn());
			map.put(CafeOrder.COL_START_TIME,search.getStartTime());
			map.put(CafeOrder.COL_END_TIME,search.getEndTime());
			map.put(Constant.SQL_LIMIT,search.getLimit());
			map.put(Constant.SQL_OFFSET,search.getOffset());
			map.put(Constant.SQL_ORDER_FIELD,search.getOrderField());
			map.put(Constant.SQL_SORT,search.getSort());
			List<Map<String,Object>> statisticMapList = mapper.findLimitCafeOrderListGroupByTable(map);
			StatisticByTable newForm = null;
			for (Map<String, Object> map2 : statisticMapList) {
				newForm = fromGroupByTableToForm(map2);
				statisticByTableList.add(newForm);
			}
		} finally {
			session.close();
		}
		return statisticByTableList;
	}
	
	public List<StatisticByDay> findLimitCafeOrderListGroupByDay(CafeOrderSearch search){
		List<StatisticByDay> statisticByDayList = new ArrayList<StatisticByDay>();
		SqlSession session = MyBatisUtil.openSession();
		try {
			CafeOrderMapper mapper = session.getMapper(CafeOrderMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(CafeOrder.COL_CAFE_SHOP_SN,search.getCafeShopSn());
			map.put(CafeOrder.COL_START_TIME,search.getStartTime());
			map.put(CafeOrder.COL_END_TIME,search.getEndTime());
			map.put(Constant.SQL_ORDER_FIELD,search.getOrderField());
			map.put(Constant.SQL_SORT,search.getSort());
			//map.put(Constant.SQL_LIMIT,search.getLimit());
			//map.put(Constant.SQL_OFFSET,search.getOffset());
			List<Map<String,Object>> statisticMapList = mapper.findLimitCafeOrderListGroupByDay(map);
			StatisticByDay newForm = null;
			for (Map<String, Object> map2 : statisticMapList) {
				newForm = fromGroupByDayToForm(map2);
				statisticByDayList.add(newForm);
			}
		} finally {
			session.close();
		}
		return statisticByDayList;
	}
	
	public StatisticByDay fromGroupByDayToForm(Map<String,Object> map){
		if(null!=map){
			StatisticByDay newForm = new StatisticByDay();
			newForm.setDayName(AppStrUtils.toEmpty(map.get(CafeOrder.DB_DAY_ORDER_NAME)));
			newForm.setTotalMoney(AppNumUtils.toIntValue(map.get(CafeOrder.DB_TOTAL_MONEY)));
			newForm.setNumOfFood(AppNumUtils.toIntValue(map.get(CafeOrder.DB_NUM_OF_FOOD)));
			return newForm;
		}
		return null;
	}
	
	public List<StatisticByHour> findLimitCafeOrderListGroupByHour(CafeOrderSearch search){
		List<StatisticByHour> statisticByDayList = new ArrayList<StatisticByHour>();
		SqlSession session = MyBatisUtil.openSession();
		try {
			CafeOrderMapper mapper = session.getMapper(CafeOrderMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(CafeOrder.COL_CAFE_SHOP_SN,search.getCafeShopSn());
			map.put(CafeOrder.COL_START_TIME,search.getStartTime());
			map.put(CafeOrder.COL_END_TIME,search.getEndTime());
			map.put(Constant.SQL_ORDER_FIELD,search.getOrderField());
			map.put(Constant.SQL_SORT,search.getSort());
			//map.put(Constant.SQL_LIMIT,search.getLimit());
			//map.put(Constant.SQL_OFFSET,search.getOffset());
			List<Map<String,Object>> statisticMapList = mapper.findLimitCafeOrderListGroupByHour(map);
			StatisticByHour newForm = null;
			for (Map<String, Object> map2 : statisticMapList) {
				newForm = fromGroupByHourToForm(map2);
				statisticByDayList.add(newForm);
			}
		} finally {
			session.close();
		}
		return statisticByDayList;
	}
	
	public StatisticByHour fromGroupByHourToForm(Map<String,Object> map){
		if(null!=map){
			StatisticByHour newForm = new StatisticByHour();
			newForm.setHour(AppNumUtils.toIntValue(map.get(CafeOrder.DB_HOUR_ORDER)));
			newForm.setTotalMoney(AppNumUtils.toIntValue(map.get(CafeOrder.DB_TOTAL_MONEY)));
			newForm.setNumOfFood(AppNumUtils.toIntValue(map.get(CafeOrder.DB_NUM_OF_FOOD)));
			return newForm;
		}
		return null;
	}
	
	public List<StatisticByTime> findLimitCafeOrderListGroupByTime(CafeOrderSearch search){
		List<StatisticByTime> statisticByDayList = new ArrayList<StatisticByTime>();
		SqlSession session = MyBatisUtil.openSession();
		try {
			CafeOrderMapper mapper = session.getMapper(CafeOrderMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(CafeOrder.COL_CAFE_SHOP_SN,search.getCafeShopSn());
			map.put(CafeOrder.COL_START_TIME,search.getStartTime());
			map.put(CafeOrder.COL_END_TIME,search.getEndTime());
			//map.put(Constant.SQL_LIMIT,search.getLimit());
			//map.put(Constant.SQL_OFFSET,search.getOffset());
			List<Map<String,Object>> statisticMapList = mapper.findLimitCafeOrderListGroupByTime(map);
			StatisticByTime newForm = null;
			for (Map<String, Object> map2 : statisticMapList) {
				newForm = fromGroupByTimeToForm(map2);
				statisticByDayList.add(newForm);
			}
		} finally {
			session.close();
		}
		return statisticByDayList;
	}
	
	public StatisticByTime fromGroupByTimeToForm(Map<String,Object> map){
		if(null!=map){
			StatisticByTime newForm = new StatisticByTime();
			newForm.setTotalMoney(AppNumUtils.toIntValue(map.get(CafeOrder.DB_TOTAL_MONEY)));
			newForm.setNumOfFood(AppNumUtils.toIntValue(map.get(CafeOrder.DB_NUM_OF_FOOD)));
			newForm.setStartHour(AppStrUtils.toEmpty(map.get(CafeOrder.DB_START_TIME)));
			newForm.setEndHour(AppStrUtils.toEmpty(map.get(CafeOrder.DB_END_TIME)));
			return newForm;
		}
		return null;
	}
	
	public StatisticByTable fromGroupByTableToForm(Map<String,Object> map){
		if(null!=map){
			StatisticByTable newForm = new StatisticByTable();
			newForm.setCafeTableSn(AppNumUtils.toLong(map.get(CafeOrder.DB_CAFE_TABLE_SN)));
			newForm.setCafeTableName(AppStrUtils.toEmpty(map.get(CafeTable.DB_NAME)));
			newForm.setTotalMoney(AppNumUtils.toIntValue(map.get(CafeOrder.DB_TOTAL_MONEY)));
			newForm.setNumOfFood(AppNumUtils.toIntValue(map.get(CafeOrder.DB_NUM_OF_FOOD)));
			return newForm;
		}
		return null;
	}
	
	public StatisticByDate fromGroupByDateToForm(Map<String,Object> map){
		if(null!=map){
			StatisticByDate newForm = new StatisticByDate();
			newForm.setDateStr(AppDateUtils.toYYYYMMDDStr((Date)map.get(CafeOrder.DB_ORDER_DATE)));
			newForm.setNumOfFood(AppNumUtils.toIntValue(map.get(CafeOrder.DB_NUM_OF_FOOD)));
			newForm.setTotalMoney(AppNumUtils.toIntValue(map.get(CafeOrder.DB_TOTAL_MONEY)));
			newForm.setFirstOrderTime(AppDateUtils.toYYYYMMDDHHMMStr((Date)map.get(CafeOrder.DB_FIRST_ORDER)));
			newForm.setLastOrderTime(AppDateUtils.toYYYYMMDDHHMMStr((Date)map.get(CafeOrder.DB_LAST_ORDER)));
			newForm.setDayOfWeek(AppStrUtils.toEmpty(map.get(CafeOrder.DB_DAY_ORDER_NAME)));
			return newForm;
		}
		return null;
	}
	
	
	public CafeOrderForm fromGroupByFoodToForm(Map<String,Object> map){
		if(null!=map){
			CafeOrderForm newForm = new CafeOrderForm();
			newForm.setFoodSn(AppNumUtils.toLong(map.get(CafeOrder.DB_FOOD_SN)));
			newForm.setFoodName(AppStrUtils.toEmpty(map.get(Food.DB_FOOD_NAME)));
			newForm.setFoodUnitSn(AppNumUtils.toLong(map.get(CafeOrder.DB_FOOD_UNIT_SN)));
			newForm.setFoodUnitName(AppStrUtils.toEmpty(map.get(FoodUnit.DB_FOOD_UNIT_NAME)));
			newForm.setNumOfFood(AppNumUtils.toIntValue(map.get(CafeOrder.DB_NUM_OF_FOOD)));
			newForm.setTotalMoney(AppNumUtils.toIntValue(map.get(CafeOrder.DB_TOTAL_MONEY)));
			newForm.setTotalMoneyStr(AppStrUtils.priceToString(newForm.getTotalMoney()));
			newForm.setPrice(AppNumUtils.toIntValue(map.get(Food.DB_PRICE)));
			newForm.setPriceStr(AppStrUtils.priceToString(newForm.getPrice()));
			return newForm;
		}
		return null;
	}
	
	public int updateDiscountOfAllBill(Long cafeShopSn,Long cafeTableSn,int discount){
		SqlSession session = MyBatisUtil.openSession();
		int count = 0;
		try {
			CafeOrderMapper mapper = session.getMapper(CafeOrderMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(CafeOrder.COL_CAFE_SHOP_SN,cafeShopSn);
			map.put(CafeOrder.COL_DISCOUNT,discount);
			map.put(CafeOrder.COL_CAFE_TABLE_SN,cafeTableSn);
			count = mapper.updateDiscountOfAllBill(map);
			session.commit();
		} finally {
			session.close();
		}
		return count;
	}
	
	public int payAllOrderOfBill(Long cafeShopSn,Long cafeTableSn,Date payTime,Long lastUpdateStaffSn){
		SqlSession session = MyBatisUtil.openSession();
		int count = 0;
		try {
			CafeOrderMapper mapper = session.getMapper(CafeOrderMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(CafeOrder.COL_CAFE_SHOP_SN,cafeShopSn);
			map.put(CafeOrder.COL_PAY_TIME,payTime);
			map.put(CafeOrder.COL_CAFE_TABLE_SN,cafeTableSn);
			map.put(CafeOrder.COL_LAST_UPDATE_STAFF_SN,lastUpdateStaffSn);
			count = mapper.payAllOrderOfBill(map);
			session.commit();
		} finally {
			session.close();
		}
		return count;
	}
	
	public CafeOrderForm findFullCafeOrder(Long cafeShopSn,Long cafeOrderSn){
		SqlSession session = MyBatisUtil.openSession();
		try {
			CafeOrderMapper mapper = session.getMapper(CafeOrderMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(CafeOrder.COL_CAFE_SHOP_SN,cafeShopSn);
			map.put(CafeOrder.COL_SN,cafeOrderSn);
			Map<String,Object> cafeOrderMap= mapper.findFullCafeOrder(map);
			CafeOrderForm cafeOrder = toCafeOrderForm(cafeOrderMap);
			return cafeOrder;
		} finally {
			session.close();
		}
	}
	
	public int updatePrintAllOrderOfBill(Long cafeShopSn,Long cafeTableSn){
		SqlSession session = MyBatisUtil.openSession();
		try {
			CafeOrderMapper mapper = session.getMapper(CafeOrderMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(CafeOrder.COL_CAFE_SHOP_SN,cafeShopSn);
			map.put(CafeOrder.COL_CAFE_TABLE_SN,cafeTableSn);
			int count =mapper.updatePrintAllOrderOfBill(map);
			session.commit();
			return count;
		} finally {
			session.close();
		}
	}
	
	public int updatePrintOfCafeOrder(Long cafeShopSn,Long cafeOrderSn){
		SqlSession session = MyBatisUtil.openSession();
		try {
			CafeOrderMapper mapper = session.getMapper(CafeOrderMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(CafeOrder.COL_CAFE_SHOP_SN,cafeShopSn);
			map.put(CafeOrder.COL_SN,cafeOrderSn);
			int count =mapper.updatePrintOfCafeOrder(map);
			session.commit();
			return count;
		} finally {
			session.close();
		}
	}
	
	public int updatePrint(Long cafeShopSn,Long cafeTableSn,Long cafeOrderSn){
		if(null!=cafeTableSn){
			return updatePrintAllOrderOfBill(cafeShopSn,cafeTableSn);
		}
		if(null!=cafeOrderSn){
			return updatePrintOfCafeOrder(cafeShopSn,cafeOrderSn);
		}
		return 0;
	}
	
	public StatisticByMonth findOrderStatisticInPeriodTime(Long cafeShopSn,Date startTime,Date endTime){
		SqlSession session = MyBatisUtil.openSession();
		try {
			CafeOrderMapper mapper = session.getMapper(CafeOrderMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(CafeOrder.COL_CAFE_SHOP_SN,cafeShopSn);
			map.put(CafeOrder.COL_START_TIME,startTime);
			map.put(CafeOrder.COL_END_TIME,endTime);
			Map<String,Object> cafeOrderMap= mapper.findOrderStatisticInPeriodTime(map);
			StatisticByMonth statisticByMonth = toStatisticByMonth(cafeOrderMap);
			int daysBetween = AppDateUtils.calDaysBetween2Days(startTime,endTime);
			int avergaMoney = (int)(statisticByMonth.getTotalMoney()/daysBetween);
			statisticByMonth.setMoneyPerDay(avergaMoney);
			return statisticByMonth;
		} finally {
			session.close();
		}
	}
	
	private StatisticByMonth toStatisticByMonth(Map<String,Object> map){
		StatisticByMonth statisticByMonth = new StatisticByMonth();
		statisticByMonth.setNumOfFood(AppNumUtils.toIntValue(map.get(CafeOrder.DB_NUM_OF_FOOD)));
		statisticByMonth.setTotalMoney(AppNumUtils.toIntValue(map.get(CafeOrder.DB_TOTAL_MONEY)));
		return statisticByMonth;
	}
}
