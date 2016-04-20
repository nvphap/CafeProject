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
	                    <a id="timeFrame" href="#" class="table-sort">
							<spring:message code="statistic.timeFrame" />
							<c:if test="${!empty orderField && orderField == 'timeFrame'}">
							<c:if test="${!empty sort && sort == 'asc'}">
								<span class="glyphicon glyphicon-triangle-top list-sort"></span>
							</c:if>
							<c:if test="${!empty sort && sort == 'desc'}">
								<span class="glyphicon glyphicon-triangle-bottom list-sort"></span>
							</c:if>	
							</c:if>
							<c:if test="${!empty orderField && orderField != 'timeFrame'}">
								<span class="glyphicon glyphicon-sort list-sort"></span>
							</c:if>
						</a>
	                </th>
	                <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="">
						<a id="numOfFood" href="#" class="table-sort">
							<spring:message code="cafeOrder.numOfFood" />
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
	                	aria-label="" >
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
	            </tr>
	        </thead>
	        <tbody id="ajaxListContent">
	            <c:if test="${!empty cafeOrderList}">
	                <c:set var="val" value="0" />
	                <c:forEach items="${cafeOrderList}" var="item">
	                    <tr role="row" class="odd cf_order_record">
	                        <c:set var="val" value="${val+1}" />
	                        <td class="sorting_1">${val}</td>
	                        <td>
	                        	<div>
									${item.startHour}&nbsp;<b>~</b>&nbsp;${item.endHour}
								</div>
	                        </td>
	                        <td>
	                        	<div>${item.numOfFoodStr}</div>
	                        </td>
	                        <td>
	                        	<div>${item.totalMoneyStr}</div>
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
		j('#cf_statistic_other_outlay_link').click(function(){
			var startDate = j('#cf_statistic_from_date').val();
			var endDate = j('#cf_statistic_to_date').val();
			var params = 'startDate='+startDate+'&endDate='+endDate;
			var href = '${pageContext.request.contextPath}/otherOutlay/list?'+params;
			j('#cf_statistic_other_outlay_detail').attr('href',href);
			j('#cf_statistic_other_outlay_detail')[0].click();
		});
		
		j("#timeFrame").click(function(){
			searchCafeOrderModeHourSort('timeFrame',j('#sort').val());
		});
		
		j("#numOfFood").click(function(){
			searchCafeOrderModeHourSort('numOfFood',j('#sort').val());
		});
		
		j("#totalMoney").click(function(){
			searchCafeOrderModeHourSort('totalMoney',j('#sort').val());
		});
		
		function searchCafeOrderModeHourSort(orderField,sort){
    		dialogWating.open();
			j.ajax({
	   	        type: "POST",
	   	        url: "${pageContext.request.contextPath}/statistic/list/getCafeOrderGroupByHour",
	   	         data:{
	   	        	fromDate:j('#cf_statistic_from_date').val(),
	   	        	toDate:j('#cf_statistic_to_date').val(),
	   	        	orderField:orderField,
	   	        	sort:sort
	   	         },
	   	      	async: true,
	   	         success: function(response){
	    			j('#orderDetailList').html(response);
	   	        	dialogWating.close();
   	            }
   	          });
		}
	});
</script>