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
	                    <spring:message code="statistic.month" />
	                </th>
		            <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="">
	                    <spring:message code="statistic.timeFrame" />
	                </th>
	                <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="">
	                    <spring:message code="cafeOrder.totalMoneyPerDay" />
	                </th>
	                <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="">
	                    <spring:message code="statistic.totalProfit" />
	                </th>
	                <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="">
							<spring:message code="cafeOrder.numOfFood" />
	                </th>
	                <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="" >
						<spring:message code="cafeOrder.totalMoney" />
	                </th>
	                <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="" >
						<spring:message code="otherOutlay.moneyOfOtherOutlay" />
	                </th>
	            </tr>
	        </thead>
	        <tbody id="ajaxListContent">
	            <c:if test="${!empty monthStatisticList}">
	                <c:set var="val" value="0" />
	                <c:forEach items="${monthStatisticList}" var="item">
	                    <tr role="row" class="odd cf_order_record">
	                        <c:set var="val" value="${val+1}" />
	                        <td class="sorting_1">${val}</td>
	                        <td>
	                        	<div>
									${item.monthOfYear}
								</div>
	                        </td>
	                        <td>
	                        	<div>
									${item.timeFrameStart} <b>~</b>${item.timeFrameEnd}
								</div>
	                        </td>
	                        <td>
	                        	<div>
									${item.moneyPerDayStr}
								</div>
	                        </td>
	                        <td>
	                        	<div>
									${item.totalProfitStr}
								</div>
	                        </td>
	                        <td>
	                        	<div>${item.numOfFoodStr}</div>
	                        </td>
	                        <td>
	                        	<div>
	                        		<span>${item.totalMoneyStr}</span>
	                        		<c:if test="${item.totalMoney>0}">
		                        		&nbsp;
										<span>
											<a href="${pageContext.request.contextPath}/statistic/list?display=1&fromDate=${item.timeFrameStart}&toDate=${item.timeFrameEnd}" target='_blank'><spring:message code="common.detail"/></a>
										</span>
									</c:if>
	                        	</div>
	                        </td>
	                        <td>
	                        	<div>
	                        		<span>
	                        			${item.totalMoneyOtherOutlayStr}
	                        		</span>
	                        		<c:if test="${item.totalMoneyOtherOutlay>0}">
		                        		&nbsp;
		                        		<span>
		                        			<a href="${pageContext.request.contextPath}/otherOutlay/list?mode=2&startDate=${item.timeFrameStart}&endDate=${item.timeFrameEnd}" target='_blank'><spring:message code="common.detail"/></a>
		                        		</span>
	                        		</c:if>
	                        	</div>
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
		
		j("#numOfFood").click(function(){
			searchCafeOrderModeDaySort('numOfFood',j('#sort').val());
		});
		
		j("#totalMoney").click(function(){
			searchCafeOrderModeDaySort('totalMoney',j('#sort').val());
		});
		
		function searchCafeOrderModeDaySort(orderField,sort){
    		dialogWating.open();
			j.ajax({
	   	        type: "POST",
	   	        url: "${pageContext.request.contextPath}/statistic/list/getCafeOrderGroupByDay",
	   	         data:{
	   	        	 orderField:orderField,
	   	        	 sort:sort,
	   	        	fromDate:j('#cf_statistic_from_date').val(),
	   	        	toDate:j('#cf_statistic_to_date').val()
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