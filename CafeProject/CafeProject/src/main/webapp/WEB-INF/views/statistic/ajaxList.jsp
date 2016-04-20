<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<spring:message code="cafeOrder.totalMoney" var="cafeOrder_totalMoney"/>
<spring:message code="cafeOrder.othersTotalMoney" var="cafeOrder_othersTotalMoney"/>
<spring:message code="cafeOrder.totalCafeOrder" var="cafeOrder_totalCafeOrder"/>

<div class="well with-header horizontal-scroll" style="padding-top: 40px;">
	<div class="header bg-blue" style="height: 36px;"><spring:message code="cafeTable.listTitle"/></div>
	<div style="margin-bottom:10px;">
		
		<div style="margin-bottom:5px;">
			<span class="cf-total-money-title">${cafeOrder_totalCafeOrder}:</span>&nbsp;<span>${numOfCafeOrder}</span>
		</div>
		<div style="margin-bottom:5px;">
			<span class="cf-total-money-title">${cafeOrder_totalMoney}:</span>&nbsp;<span>${cafeOrderTotalMoney}</span>
		</div>
		<div>
			<span class="cf-total-money-title">${cafeOrder_othersTotalMoney}:</span>&nbsp;<span>${othersTotalMoney}</span>
			&nbsp;<a id="cf_statistic_other_outlay_link" href="#"><spring:message code="common.detail"/></a>
		</div>
		<a id="cf_statistic_other_outlay_detail" style="display:none;" href="#" target="_blank"></a>
	</div>
	<div id="editabledatatable_wrapper" class="dataTables_wrapper form-inline no-footer">
		<input id="sort" type="hidden" value="${sort}">
		<input id="orderField" type="hidden" value="${orderField}">
	    <table class="table table-striped table-hover table-bordered dataTable no-footer" 
	    	id="editabledatatable" role="grid" aria-describedby="editabledatatable_info">
	        <thead>
	            <tr role="row">
	                <th class="sorting_asc" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-sort="ascending" aria-label="">
		            </th>
		             <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="">
	                    <a id="tableName" href="#" class="table-sort">
							<spring:message code="cafeOrder.tableName" />
							<c:if test="${!empty orderField && orderField == 'tableName'}">
							<c:if test="${!empty sort && sort == 'asc'}">
								<span class="glyphicon glyphicon-triangle-top list-sort"></span>
							</c:if>
							<c:if test="${!empty sort && sort == 'desc'}">
								<span class="glyphicon glyphicon-triangle-bottom list-sort"></span>
							</c:if>	
							</c:if>
							<c:if test="${!empty orderField && orderField != 'tableName'}">
								<span class="glyphicon glyphicon-sort list-sort"></span>
							</c:if>
						</a>
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
						<a id="numOfFood" href="#" class="table-sort">
							<spring:message code="cafeOrder.numOfFood2" />
							<c:if test="${!empty orderField && orderField == 'numOfFood'}">
							<c:if test="${!empty sort && sort == 'asc'}">
								<span class="glyphicon glyphicon-triangle-top list-sort"></span>
							</c:if>
							<c:if test="${!empty sort && sort == 'desc'}">
								<span class="glyphicon glyphicon-triangle-bottom list-sort"></span>
							</c:if>	
							</c:if>
							<c:if test="${!empty orderField && orderField != 'numOfFood'}">
								<span class="glyphicon glyphicon-sort list-sort"></span>
							</c:if>
						</a>
	                </th>
	                
	                <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="">
	                    <a id="totalMoney" href="#" class="table-sort">
							<spring:message code="cafeOrder.totalMoney" />
							<c:if test="${!empty orderField && orderField == 'totalMoney'}">
							<c:if test="${!empty sort && sort == 'asc'}">
								<span class="glyphicon glyphicon-triangle-top list-sort"></span>
							</c:if>
							<c:if test="${!empty sort && sort == 'desc'}">
								<span class="glyphicon glyphicon-triangle-bottom list-sort"></span>
							</c:if>	
							</c:if>
							<c:if test="${!empty orderField && orderField != 'totalMoney'}">
								<span class="glyphicon glyphicon-sort list-sort"></span>
							</c:if>
						</a>
	                </th>
	                 <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="">
							<spring:message code="cafeOrder.discount" />
	                </th>
	                <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="">
	                    <a id="orderTime" href="#" class="table-sort">
							<spring:message code="cafeOrder.orderTime" />
							<c:if test="${!empty orderField && orderField == 'orderTime'}">
							<c:if test="${!empty sort && sort == 'asc'}">
								<span class="glyphicon glyphicon-triangle-top list-sort"></span>
							</c:if>
							<c:if test="${!empty sort && sort == 'desc'}">
								<span class="glyphicon glyphicon-triangle-bottom list-sort"></span>
							</c:if>	
							</c:if>
							<c:if test="${!empty orderField && orderField != 'orderTime'}">
								<span class="glyphicon glyphicon-sort list-sort"></span>
							</c:if>
						</a>
	                </th>
	                <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="">
							 <a id="payTime" href="#" class="table-sort">
							<spring:message code="cafeOrder.payTime" />
							<c:if test="${!empty orderField && orderField == 'payTime'}">
							<c:if test="${!empty sort && sort == 'asc'}">
								<span class="glyphicon glyphicon-triangle-top list-sort"></span>
							</c:if>
							<c:if test="${!empty sort && sort == 'desc'}">
								<span class="glyphicon glyphicon-triangle-bottom list-sort"></span>
							</c:if>	
							</c:if>
							<c:if test="${!empty orderField && orderField != 'payTime'}">
								<span class="glyphicon glyphicon-sort list-sort"></span>
							</c:if>
						</a>
	                </th>
	                 <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="">
							<spring:message code="cafeOrder.print" />
	                </th>
	                <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="">
							<spring:message code="cafeOrder.memo" />
	                </th>
	                <th class="sorting_disabled" rowspan="1" colspan="1" aria-label="">
	                </th>
	            </tr>
	        </thead>
	        <tbody id="ajaxListContent">
	            <c:if test="${!empty cafeOrderList}">
	                <c:set var="val" value="0" />
	                <c:forEach items="${cafeOrderList}" var="item">
	                <c:if test="${1!=item.isDiscount}">
	                    <tr role="row" class="odd cf_order_record cf_order_record_group_${item.cafeTableSn}">
	                 </c:if>
	                 <c:if test="${1==item.isDiscount}">
	                 	<tr role="row" class="cf-cafe-order-discount odd cf_order_record cf_order_record_group_${item.cafeTableSn}">
	                 </c:if>
	                        <c:set var="val" value="${val+1}" />
	                        <td class="sorting_1">${val}</td>
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
	                        	<div id="cf_cafe_price_${item.sn}">${item.priceStr}</div>
	                        </td>
	                        <td>
	                        	<div id="cf_cafe_num_of_food_${item.sn}">${item.numOfFood}</div>
	                        </td>
	          				<td>
	          					<div id="cf_cafe_total_money_${item.sn}">${item.totalMoneyStr}</div>
	          				</td>
	          				<td>
	          					<c:if test="${0==item.discount}">
	          						<div id="cf_cafe_total_money_${item.sn}">${item.discount}%</div>
	          					</c:if>
	          					<c:if test="${item.discount>0}">
	          						<div style="font-weight:bold;" id="cf_cafe_total_money_${item.sn}">${item.discount}%</div>
	          					</c:if>
	          				</td>
	          				<td>
	          					<div id="cf_cafe_order_time_${item.sn}">${item.orderTimeStr}</div>
	          				</td>
	          				<td>
	          					<div id="cf_cafe_pay_time_${item.sn}">${item.payTimeStr}</div>
	          				</td>
	          				<td>
	          					<div>${item.numOfPrint}</div>
	          				</td>
	                        <td>
	                        	<div id="cf_cafe_memo_${item.sn}">${item.memo}</div>
	                        </td>
	                        <td>
	                        	<a id="cf_statistic_record_print_${item.sn}" href="#" class="btn btn-primary btn-xs cf_statistic_record_print_group"> 
	                        		<i class="fa fa-camera-retro"></i><spring:message code="cafeOrder.print" />
								</a>
	                        	<c:if test="${1==p.statistic.delete}">
	                            <a id="cf_statistic_record_delete_${item.sn}" href="#" class="btn btn-danger btn-xs delete cf_statistic_record_delete_group">
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
	    <input id="hPagingSetup" name ="hPagingSetup" type="hidden" value="|#orderDetailList"></input>
	    <div class="row DTTTFooter">
	        <c:if test="${!empty cafeOrderList}">	
	        	<%@include file="../common/paging.jsp"%>
	        </c:if>
	    </div>
	</div>                            	
</div>


<script type="text/javascript">
	j(document).ready(function() {
		function openPrintPreview(cafeTableSn,cafeOrderSn) {
			var printUrl='';
			if(cafeTableSn.length>0){
				printUrl = '${pageContext.request.contextPath}/cafeOrder/view/printOrder?cafeTableSn=';
				printUrl += cafeTableSn;
			}else if(cafeOrderSn.length>0){
				printUrl = '${pageContext.request.contextPath}/cafeOrder/view/printOrder?cafeOrderSn=';
				printUrl += cafeOrderSn;
			}
			var printWindow=window.open(printUrl,'orderPrint',',type=fullWindow,fullscreen,scrollbars=yes');
			if(window.focus){
				printWindow.focus();
			} 
			printWindow.print();
			return false;
		}
		
		j('.cf_statistic_record_print_group').click(function(){
			var cafeOrderSn = j(this).attr('id').replace('cf_statistic_record_print_','');
			openPrintPreview('',cafeOrderSn);
		})
		
		j('.cf_statistic_record_delete_group').click(function(){
			var cafeOrderSn = j(this).attr('id').replace('cf_statistic_record_delete_','');
			deleteCafeOrder(cafeOrderSn);
		});
		
		j("#tableName").click(function(){
			searchCafeOrderModeListSort('tableName',j('#sort').val());
		});
		
		j("#numOfFood").click(function(){
			searchCafeOrderModeListSort('numOfFood',j('#sort').val());
		});
		
		j("#totalMoney").click(function(){
			searchCafeOrderModeListSort('totalMoney',j('#sort').val());
		});
		
		j("#orderTime").click(function(){
			searchCafeOrderModeListSort('orderTime',j('#sort').val());
		});
		
		j("#payTime").click(function(){
			searchCafeOrderModeListSort('payTime',j('#sort').val());
		});
		
		function searchCafeOrderModeListSort(orderField, sort){
    		dialogWating.open();
			j.ajax({
	   	        type: "POST",
	   	        url: "${pageContext.request.contextPath}/statistic/list/getCafeOrderList",
	   	         data:{
	   	        	orderField:orderField,
	   	        	sort:sort,
	   	        	fromDate:j('#cf_statistic_from_date').val(),
	   	        	toDate:j('#cf_statistic_to_date').val(),
	   	        	page:j('#currentPage').val()
	   	         },
	   	      	async: true,
	   	         success: function(response){
	    			j('#orderDetailList').html(response);
	   	        	dialogWating.close();
   	            }
   	          });
		}
		
		j('#cf_statistic_other_outlay_link').click(function(){
			var startDate = j('#cf_statistic_from_date').val();
			var endDate = j('#cf_statistic_to_date').val();
			var params = 'startDate='+startDate+'&endDate='+endDate;
			var href = '${pageContext.request.contextPath}/otherOutlay/list?'+params;
			j('#cf_statistic_other_outlay_detail').attr('href',href);
			j('#cf_statistic_other_outlay_detail')[0].click();
		});
		
		function deleteCafeOrder(cafeOrderSn){
			j.ajax({
	   	        type: "POST",
	   	        url: "${pageContext.request.contextPath}/statistic/delete/request",
	   	         data:{
	   	        	cafeOrderSn:cafeOrderSn,
	   	        	fromDate:j('#cf_statistic_from_date').val(),
	   	        	toDate:j('#cf_statistic_to_date').val(),
	   	        	page:j('#currentPage').val(),
	   	        	orderField:j("#orderField").val(),
	   	        	sort:j('#sort').val()
	   	         },
	   	      	async: true,
	   	         success: function(response){
	    			j('#orderDetailList').html(response);
	    			displayCafeOrderOfCurrentTable();
	    			updateCafeOrderStatisticForOneTable(j('#currentTableSn').val());
   	            }
   	        });
    	}
	});
</script>