<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<spring:message code="cafeTable.deleteConfirm" var="cafeTable_deleteConfirm"/>
<div class="well with-header horizontal-scroll" style="padding-top: 40px;">
	<div class="header bg-blue" style="height: 36px;"><spring:message code="cafeTable.listTitle"/></div>
	 <div>
	 	<span style="font-weight:bold;color:blue;">
	 		<spring:message code="cafeOrder.othersTotalMoney"/>:</span>
	 	<span>${othersTotalMoney}</span>	
	 </div>
	<div id="editabledatatable_wrapper" class="dataTables_wrapper form-inline no-footer">
		<input id="sort" type="hidden" value="${sort}">
		
	    <table class="table table-striped table-hover table-bordered dataTable no-footer" 
	    	id="editabledatatable" role="grid" aria-describedby="editabledatatable_info">
	        <thead>
	            <tr role="row">
	                <th class="sorting_asc" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-sort="ascending" aria-label="">
		            </th>
		            <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="">
	                    <spring:message code="otherOutlay.name" />
	                </th>
	                <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="" >
							<spring:message code="otherOutlay.numberOfItem2" />
	                </th>
	                <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="">
	                	<spring:message code="otherOutlay.totalPrice" />
	                </th>
	            </tr>
	        </thead>
	        <tbody>
	            <c:if test="${!empty otherOutlayList}">
	                <c:set var="val" value="0" />
	                <c:forEach items="${otherOutlayList}" var="item">
	                    <tr role="row" class="odd">
	                        <c:set var="val" value="${val+1}" />
	                        <td class="sorting_1">${val}</td>
	                        <td>
								${item.otherOutlayName}
	                        </td>
	                        <td>${item.numOfOutlay}</td>
	                        <td>${item.totalPriceStr}</td>
	                    </tr>
	                </c:forEach>
	            </c:if>
	        </tbody>
	    </table>
	     <div class="row DTTTFooter">
	        <c:if test="${!empty otherOutlayList}">	
	        	<%@include file="../common/paging.jsp"%>
	        </c:if>
	    </div>
	</div>                            	
</div>


<script type="text/javascript">
	j(document).ready(function() {
	
	});
</script>