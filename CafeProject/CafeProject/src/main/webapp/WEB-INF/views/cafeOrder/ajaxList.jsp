<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<spring:message code="cafeTable.deleteConfirm" var="cafeTable_deleteConfirm"/>
<c:if test="${3!=loginUser.roleSn}">
	<div style="margin-bottom:5px;">
		<span style="font-weight:bold;"><spring:message code="cafeOrder.numOfOrderInMonth"/>:</span>
		<span>${monthStatistic.numOfFoodStr}</span>
		&nbsp;&nbsp;
		<span style="font-weight:bold;"><spring:message code="cafeOrder.totalMoneyInMonth"/>:</span>
		<span>${monthStatistic.totalMoneyStr}</span>
		&nbsp;
		<span>
			<a href="${pageContext.request.contextPath}/statistic/list?display=1" target='_blank'><spring:message code="common.detail"/></a>
		</span>
		&nbsp;&nbsp;
		<span style="font-weight:bold;"><spring:message code="cafeOrder.totalMoneyPerDay"/>:</span>
		<span>${monthStatistic.moneyPerDayStr}</span>
		&nbsp;&nbsp;
		<span style="font-weight:bold;"><spring:message code="cafeOrder.totalProfit"/>:</span>
		<span style="color:#e74b37">${monthStatistic.totalProfitStr}</span>
		&nbsp;&nbsp;
		<span style="font-weight:bold;"><spring:message code="cafeOrder.totalMoneyOtherOutlayInMonth"/>:</span>
		<span>${monthStatistic.totalMoneyOtherOutlayStr}</span>
		&nbsp;<a href="${pageContext.request.contextPath}/otherOutlay/list" target='_blank'><spring:message code="common.detail"/></a>
	</div>
</c:if>
<div style="margin-bottom:5px;">
	<span style="font-weight:bold;"><spring:message code="cafeOrder.totalOrderToday"/>:</span>
	<span>${todayStatistic.numOfFoodToday}</span>
	&nbsp;&nbsp;
	<span style="font-weight:bold;"><spring:message code="cafeOrder.totalMoneyToday"/>:</span>
	<span>${todayStatistic.totalMoneyTodayStr}</span>
	&nbsp;
	<span>
		<a id="cf_num_of_cafe_order_link" href="#"><spring:message code="common.detail"/></a>
		<a id="cf_num_of_cafe_order_detail" style="display:none;" href="#" target="_blank"></a>
	</span>
	&nbsp;&nbsp;
	<span style="font-weight:bold;"><spring:message code="cafeOrder.totalExpectedMoneyToday"/>:</span>
	<span style="color:#e74b37">${todayStatistic.totalExpectedMoneyTodayStr}</span>
	&nbsp;&nbsp;
	<span style="font-weight:bold;"><spring:message code="cafeOrder.totalOtherOutlayToday"/>:</span>
	<span>${todayStatistic.otherOutlayTodayStr}</span>
	&nbsp;<a id="cf_statistic_other_outlay_link" href="#"><spring:message code="common.detail"/></a>
	<a id="cf_statistic_other_outlay_detail" style="display:none;" href="#" target="_blank"></a>
</div>
<div class="well with-header horizontal-scroll">
	<div class="header bg-blue" style="height: 36px;"><spring:message code="cafeOrder.orderList"/></div>
	<div style="margin-bottom:5px;">
		<span style="font-weight:bold;color:blue;"><spring:message code="cafeOrder.totalMoney"/>:</span>
            <c:forEach items="${todayStatistic.tableStatisticList}" var="item">
             	 <span class="cf-table-total-money" id="cf_table_total_money_${item.cafeTableSn}">${item.totalMoneyStr}</span>
            </c:forEach>
            <span class="cf-table-total-money" id="cf_table_total_money_0">0</span>
		&nbsp;&nbsp;
		<span><spring:message code="cafeOrder.discount"/>:</span>
		 <span class="form-group form-input-therapist-timeline">
             <select name="cf_order_bill_discount" id="cf_order_bill_discount" class="form-control-ext ipt-short-70">
             	<c:set var="billDiscount" value="${selectedDiscount}"/>
				<c:forEach var="i" begin="0" end="6">
					<c:if test="${i==billDiscount}">
						<option selected="selected" value="${i*5}">${i*5}%</option>
					</c:if>
					<c:if test="${i!=billDiscount}">
						<option value="${i*5}">${i*5}%</option>
					</c:if>
				</c:forEach>
			</select>
		</span>
		&nbsp;&nbsp;
		<span>
			<a id="cf_cafe_bill_print" href="#" class="btn btn-primary btn-xs cf_cafe_bill_print_group"> 
	           <i class="fa fa-camera-retro"></i><spring:message code="cafeOrder.print" />
			</a>
		</span>
		&nbsp;&nbsp;
		<span>
			<a id="cf_cafe_bill_pay" href="#" class="btn btn-success btn-xs edit cf_cafe_bill_pay_group"> 
           		<i class="fa fa-share"></i> <spring:message code="cafeOrder.pay" />
			</a>
		</span>
	</div>
	<div id="editabledatatable_wrapper" class="dataTables_wrapper form-inline no-footer">
		
	    <table class="table table-striped table-hover table-bordered dataTable no-footer" 
	    	id="editabledatatable" role="grid" aria-describedby="editabledatatable_info">
	        <thead>
	            <tr role="row">
	                <th class="sorting_asc" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-sort="ascending" aria-label="">
		            </th>
		             <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="">
	                    <spring:message code="cafeOrder.tableName" />
	                </th>
		            <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="">
	                    <spring:message code="cafeOrder.foodName" />
	                </th>
	                <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="">
							<spring:message code="cafeOrder.price" />
	                </th>
	                <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="" >
							<spring:message code="cafeOrder.numOfFood2" />
	                </th>
	                
	                <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="">
	                    <spring:message code="cafeOrder.totalMoney" />
	                </th>
	                 <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="">
	                    <spring:message code="cafeOrder.discount" />
	                </th>
	                <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="">
	                    <spring:message code="cafeOrder.orderTime" />
	                </th>
	                <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="">
							<spring:message code="cafeOrder.memo" />
	                </th>
	                <th class="sorting_disabled" rowspan="1" colspan="1" aria-label="">
	                </th>
	                <th class="sorting_disabled" rowspan="1" colspan="1" aria-label="">
	                </th>
	            </tr>
	        </thead>
	        <tbody id="ajaxListContent">
	            <c:if test="${!empty cafeOrderList}">
	                <c:set var="val" value="0" />
	                <c:forEach items="${cafeOrderList}" var="item">
	                 	<c:if test="${1==item.inPast}">
	                    	<tr class="cf-order-notyet-pay odd cf_order_record cf_order_record_group_${item.cafeTableSn}">
	                    </c:if>
	                    <c:if test="${1!=item.inPast}">
	                    	<c:if test="${1!=item.isDiscount}">
	                    		<tr class="odd cf_order_record cf_order_record_group_${item.cafeTableSn}">
	                    	</c:if>
	                    	<c:if test="${1==item.isDiscount}">
	                    		<tr class="cf-cafe-order-discount odd cf_order_record cf_order_record_group_${item.cafeTableSn}">
	                    	</c:if>
	                    </c:if>
	                        <c:set var="val" value="${val+1}" />
	                        <td class="sorting_1">
	                        	<span>${item.orderIdx}</span>
	                        	<input type="hidden" id="cf_cafe_order_in_past_${item.sn}" value="${item.inPast}">
	                        </td>
	                        <td>
								<div>${item.cafeTableName}</div>
	                        </td>
	                        <td>
	                        	<div>
									<div id="cf_cafe_food_name_${item.sn}">${item.foodName}(${item.foodUnitName})</div>
									<input id="cf_cafe_food_sn_${item.sn}" value="${item.foodSn}" type="hidden"/>
								</div>
	                        </td>
	                        <td>
	                        	<div id="cf_cafe_price_display_${item.sn}">${item.priceStr}</div>
	                        	<input id="cf_cafe_price_${item.sn}" value="${item.price}" type="hidden"/>
	                        </td>
	                        <td>
	                        	<div id="cf_cafe_num_of_food_${item.sn}">${item.numOfFood}</div>
	                        </td>
	          				<td>
	          					<div id="cf_cafe_total_money_display_${item.sn}">${item.totalMoneyStr}</div>
	          					<input id="cf_cafe_total_money_${item.sn}" value="${item.totalMoney}" type="hidden"/>
	          				</td>
	          				<td>
	          					<c:if test="${0==item.discount}">
	          						<div id="cf_cafe_discount_display_${item.sn}">${item.discount}%</div>
	          					</c:if>
	          					<c:if test="${item.discount>0}">
	          						<div style="font-weight:bold;"id="cf_cafe_discount_display_${item.sn}">${item.discount}%</div>
	          					</c:if>
	          					<input id="cf_cafe_discount_value_${item.sn}" value="${item.discount}" type="hidden"/>
	          				</td>
	          				<td>
	          					<div>
	          						<div id="cf_cafe_order_time_${item.sn}">${item.orderTimeStr}</div>
	          						<input class="cf-cafe-order-order-date" id="cf_cafe_order_order_date_${item.sn}" type="hidden" value="${item.orderDateStr}">
	          					</div>
	          				</td>
	                        <td>
	                        	<div id="cf_cafe_memo_${item.sn}">${item.memo}</div>
	                        </td>
	                        <td>
	                        	<a id="cf_cafe_record_print_${item.sn}" href="#" class="btn btn-primary btn-xs cf_cafe_record_print_group"> 
	                        		<i class="fa fa-camera-retro"></i><spring:message code="cafeOrder.print" />
								</a>
	                        	<a id="cf_cafe_record_pay_${item.sn}" href="#" class="btn btn-success btn-xs edit cf_cafe_record_pay_group"> 
	                        		<i class="fa fa-share"></i> <spring:message code="cafeOrder.pay" />
								</a>
	                        </td>
	                        <td>
	                        	<c:if test="${1==p.cafeOrder.modify}">
	                        	<a id="cf_cafe_record_edit_${item.sn}" href="#" class="btn btn-info btn-xs edit cf_cafe_record_edit_group">
	                                <i class="fa fa-edit"></i>
	                                <spring:message code="common.edit" />
	                            </a>
	                            </c:if>
	                            <c:if test="${1==p.cafeOrder.delete}">
	                            <a id="cf_cafe_record_delete_${item.sn}" href="#" class="btn btn-danger btn-xs delete cf_cafe_record_delete_group">
	                            	<i class="fa fa-trash-o"></i>
	                                <spring:message code="common.delete"/>
								</a>
								</c:if>
	                        </td>
	                    </tr>
	                </c:forEach>
	            </c:if>
	        </tbody>
	    </table>
	</div>                            	
</div>


<script type="text/javascript">
	j(document).ready(function() {
		j('#cf_num_of_cafe_order_link').click(function(){
			var today = getToday();
			var endDate = j('#cf_statistic_to_date').val();
			var params = 'fromDate='+today+'&toDate='+today;
			var href = '${pageContext.request.contextPath}/statistic/list?display=1&'+params;
			j('#cf_num_of_cafe_order_detail').attr('href',href);
			j('#cf_num_of_cafe_order_detail')[0].click();
		});
		
		function getToday(){
			var now = new Date();
			var year = now.getFullYear();
			var month = now.getMonth()+1;
			var dayOfMonth=now.getDate();
			return year+'-'+month+'-'+dayOfMonth;
		}
		
		j('.cf_cafe_record_delete_group').click(function(){
			var cafeOrderSn = j(this).attr('id').replace('cf_cafe_record_delete_','');
			deleteCafeOrder(cafeOrderSn);
		});
		
		j('#cf_cafe_bill_pay').click(function(){
			payAllOrderOfBill();
		});
		
		function payAllOrderOfBill(){
			j.ajax({
	   	        type: "POST",
	   	        url: "${pageContext.request.contextPath}/cafeOrder/update/ajaxPayAllOrderOfBill",
	   	         data:{
	   	        	cafeTableSn:j('#currentTableSn').val()
	   	         },
	   	      	async: true,
	   	         success: function(response){
	    			j('#orderDetailList').html(response);
	    			displayCafeOrderOfCurrentTable();
	    			resetCafeOrder();
	    			updateStatusOfAddUpdateBtn();
	    			resetBillInfo();
	    			updateCafeOrderStatisticForOneTable(j('#currentTableSn').val());
   	            }
   	        });
    	}
		
		j('#cf_cafe_bill_print').click(function(){
			var cafeTableSn = j('#currentTableSn').val();
			openPrintPreview(cafeTableSn,'');
		});
		
		j('#cf_order_bill_discount').change(function(){
			updateDiscountAllBill();
		});
		
		function updateDiscountAllBill(){
			j.ajax({
	   	        type: "POST",
	   	        url: "${pageContext.request.contextPath}/cafeOrder/update/ajaxUpdateDiscountAllBill",
	   	         data:{
	   	        	cafeTableSn:j('#currentTableSn').val(),
	   				discount:j('#cf_order_bill_discount').val()
	   	         },
	   	      	async: true,
	   	         success: function(response){
	    			j('#orderDetailList').html(response);
	    			displayCafeOrderOfCurrentTable();
	    			resetCafeOrder();
	    			updateStatusOfAddUpdateBtn();
	    			updateCafeOrderStatisticForOneTable(j('#currentTableSn').val());
   	            }
   	        });
    	}
		
		function resetBillInfo(){
			j('#cf_order_bill_discount').val(0);
		}
		
		j('#cf_statistic_other_outlay_link').click(function(){
			var today = new Date();
			var dd = today.getDate();
			var mm = today.getMonth()+1; //January is 0!
			var yyyy = today.getFullYear();
			var startDate = yyyy+'-'+mm+'-'+dd;
			var endDate = yyyy+'-'+mm+'-'+dd;
			var params = 'startDate='+startDate+'&endDate='+endDate;
			var href = '${pageContext.request.contextPath}/otherOutlay/list?'+params;
			j('#cf_statistic_other_outlay_detail').attr('href',href);
			j('#cf_statistic_other_outlay_detail')[0].click();
		});
		
		function deleteCafeOrder(cafeOrderSn){
			j.ajax({
	   	        type: "POST",
	   	        url: "${pageContext.request.contextPath}/cafeOrder/delete/request",
	   	         data:{
	   	        	cafeOrderSn:cafeOrderSn
	   	         },
	   	      	async: true,
	   	         success: function(response){
	    			j('#orderDetailList').html(response);
	    			displayCafeOrderOfCurrentTable();
	    			updateCafeOrderStatisticForOneTable(j('#currentTableSn').val());
   	            }
   	        });
    	}
		
		j('.cf_cafe_record_edit_group').click(function(){
			var cafeOrderSn = j(this).attr('id').replace('cf_cafe_record_edit_','');
			fromRecordToForm(cafeOrderSn);
			updateStatusOfAddUpdateBtn();
		});
		
		j('.cf_cafe_record_print_group').click(function(){
			var cafeOrderSn = j(this).attr('id').replace('cf_cafe_record_print_','');
			openPrintPreview('',cafeOrderSn);
		});
		
		j('.cf_cafe_record_pay_group').click(function(){
			var cafeOrderSn = j(this).attr('id').replace('cf_cafe_record_pay_','');
			payCafeOrder(cafeOrderSn);
		});
		
		function payCafeOrder(cafeOrderSn){
			j.ajax({
	   	        type: "POST",
	   	        url: "${pageContext.request.contextPath}/cafeOrder/update/payCafeOrder",
	   	         data:{
	   	        	cafeOrderSn:cafeOrderSn
	   	         },
	   	      	async: true,
	   	         success: function(response){
	    			j('#orderDetailList').html(response);
	    			displayCafeOrderOfCurrentTable();
	    			updateCafeOrderStatisticForOneTable(j('#currentTableSn').val());
   	            }
   	        });
    	}
		
		function fromRecordToForm(cafeOrderSn){
			var foodName = j('#cf_cafe_food_name_'+cafeOrderSn).text();
			var price=j('#cf_cafe_price_'+cafeOrderSn).val();
			var foodSn = j('#cf_cafe_food_sn_'+cafeOrderSn).val();
			var numOfFood=j('#cf_cafe_num_of_food_'+cafeOrderSn).text();
			var totalMoney = j('#cf_cafe_total_money_'+cafeOrderSn).val();
			var memo = j('#cf_cafe_memo_'+cafeOrderSn).text();
			var discount=j('#cf_cafe_discount_value_'+cafeOrderSn).val();
			j('#cf_cafe_order_sn').val(cafeOrderSn);
			j('#cf_food_name').val(j.trim(foodName));
			j('#cf_food_sn').val(j.trim(foodSn));
			j('#cf_food_price').val(price);
			j('#cf_num_of_food').val(j.trim(numOfFood));
			j('#cf_total_money').val(totalMoney);
			j('#cf_memo').val(j.trim(memo));
			j('#cf_order_percent_discount').val(discount);
		}
	});
</script>