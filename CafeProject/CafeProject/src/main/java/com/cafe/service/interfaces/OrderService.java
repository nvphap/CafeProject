package com.cafe.service.interfaces;

import java.util.Date;
import java.util.List;

import com.cafe.dto.StatisticByMonth;
import com.cafe.dto.TableStatistic;
import com.cafe.dto.StatisticByDate;
import com.cafe.dto.StatisticByDay;
import com.cafe.dto.StatisticByHour;
import com.cafe.dto.StatisticByTable;
import com.cafe.dto.StatisticByTime;
import com.cafe.entity.CafeOrder;
import com.cafe.form.CafeOrderForm;
import com.cafe.search.CafeOrderSearch;

/**
* The login action.
*
* @author  	phap.nguyen
* @version 	1.0
* @since   	2015-05-27 
* @update   
*/
public interface OrderService {
	public List<CafeOrderForm> findAllCafeOrderListStatusOrder(Long cafeShopSn,Date startTime,Date endTime,Long cafeTableSn);
	public int insertNewCafeOrder(CafeOrder newCafeOrder);
	public int deleteCafeOrder(Long cafeShopSn,Long cafeOrderSn);
	public int updateCafeOrder(CafeOrder cafeOrder);
	public int payMoneyCafeOrder(Long cafeShopSn,Long cafeOrderSn, Date payTime,Long lastUpdateStaffSn);
	public List<TableStatistic> findCafeOrderStatistic(Long cafeShopSn,Date startTime,Date endTime);
	public int countCafeOrderOneTable(Long cafeShopSn,Long cafeTableSn,Date startTime, Date endTime);
	public List<CafeOrderForm> findLimitCafeOrderListStatusPay(CafeOrderSearch search);
	public int countAllCafeOrderListStatusPay(CafeOrderSearch search);
	public int calculateTotalMoney(CafeOrderSearch search);
	public int countAllNumOfFoodStatusPay(CafeOrderSearch search);
	public List<CafeOrderForm> findLimitCafeOrderListGroupByFood(CafeOrderSearch search);
	public List<StatisticByDate> findLimitCafeOrderListGroupByDate(CafeOrderSearch search);
	public int countRecordCafeOrderListGroupByDate(CafeOrderSearch search);
	public int countRecordCafeOrderListGroupByTable(CafeOrderSearch search);
	public List<StatisticByTable> findLimitCafeOrderListGroupByTable(CafeOrderSearch search);
	public List<StatisticByDay> findLimitCafeOrderListGroupByDay(CafeOrderSearch search);
	public List<StatisticByHour> findLimitCafeOrderListGroupByHour(CafeOrderSearch search);
	public List<StatisticByTime> findLimitCafeOrderListGroupByTime(CafeOrderSearch search);
	public int updateDiscountOfAllBill(Long cafeShopSn,Long cafeTableSn,int discount);
	public int payAllOrderOfBill(Long cafeShopSn,Long cafeTableSn,Date payTime,Long lastUpdateStaffSn);
	public CafeOrderForm findFullCafeOrder(Long cafeShopSn,Long cafeOrderSn);
	public int updatePrintAllOrderOfBill(Long cafeShopSn,Long cafeTableSn);
	public int updatePrintOfCafeOrder(Long cafeShopSn,Long cafeOrderSn);
	public int updatePrint(Long cafeShopSn,Long cafeTableSn,Long cafeOrderSn);
	public StatisticByMonth findOrderStatisticInPeriodTime(Long cafeShopSn,Date startTime,Date endTime);
}
