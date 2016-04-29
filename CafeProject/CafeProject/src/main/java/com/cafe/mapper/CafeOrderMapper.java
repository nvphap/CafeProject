package com.cafe.mapper;

import java.util.List;
import java.util.Map;


import com.cafe.entity.CafeOrder;



/**
* The login action.
*
* @author  	phap.nguyen
* @version 	1.0
* @since   	2015-05-27 
* @update   
*/
public interface CafeOrderMapper {
	public List<Map<String,Object>> findAllCafeOrderListStatusOrder(Map<String,Object> map);
	public int insertNewCafeOrder(CafeOrder cafeOrder);
	public int deleteCafeOrder(Map<String,Object> map);
	public int updateCafeOrder(CafeOrder cafeOrder);
	public int payMoneyCafeOrder(Map<String,Object> map);
	public List<Map<Long,Long>>findStatisticOfAllTable(Map<String,Object> map);
	public int countCafeOrderOneTable(Map<String,Object> map);
	public List<Map<String,Object>>findLimitCafeOrderListStatusPay(Map<String,Object> map);
	public int countAllCafeOrderListStatusPay(Map<String,Object> map);
	public int calculateTotalMoney(Map<String,Object> map);
	public int countAllNumOfFoodStatusPay(Map<String,Object> map);
	public List<Map<String,Object>> findLimitCafeOrderListGroupByFood(Map<String,Object> map);
	public List<Map<String,Object>> findLimitCafeOrderListGroupByDate(Map<String,Object> map);
	public int countRecordCafeOrderListGroupByDate(Map<String,Object> map);
	public List<Map<String,Object>>findLimitCafeOrderListGroupByTable(Map<String,Object> map);
	public int countRecordCafeOrderListGroupByTable(Map<String,Object> map);
	public List<Map<String,Object>>findLimitCafeOrderListGroupByDay(Map<String,Object> map);
	public List<Map<String,Object>>findLimitCafeOrderListGroupByHour(Map<String,Object> map);
	public List<Map<String,Object>>findLimitCafeOrderListGroupByTime(Map<String,Object> map);
	public int updateDiscountOfAllBill(Map<String,Object> map);
	public int payAllOrderOfBill(Map<String,Object> map);
	public Map<String,Object> findFullCafeOrder(Map<String,Object> map);
	public int updatePrintAllOrderOfBill(Map<String,Object> map);
	public int updatePrintOfCafeOrder(Map<String,Object> map);
	public Map<String,Object> findOrderStatisticInPeriodTime(Map<String,Object> map);
	public int calculateTotalExpectedMoney(Map<String,Object> map);
}
