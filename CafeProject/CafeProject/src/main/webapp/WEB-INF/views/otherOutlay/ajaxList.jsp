<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<spring:message code="cafeTable.deleteConfirm" var="cafeTable_deleteConfirm"/>
<div class="well with-header horizontal-scroll" style="padding-top: 40px;">
	<div class="header bg-blue" style="height: 36px;"><spring:message code="cafeTable.listTitle"/></div>
	<div class="table-toolbar">
		<c:if test="${1==p.otherOutlay.create}">
	    <div class="add-new">
	        <a id="editabledatatable_new" href="javascript:doUrl();" class="btn btn-default">
	            <i class="fa fa-plus"></i><spring:message code="common.new" />
	        </a>
	        <script>
				function doUrl() {
				    window.location = '${pageContext.request.contextPath}/otherOutlay/add';
				}
			</script>
	    </div>
	    </c:if>
	</div>
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
	                    <a id="sortTotalPrice" href="#" class="table-sort">
							<spring:message code="otherOutlay.totalPrice" />
							<c:if test="${!empty orderField && orderField == 'totalPrice'}">
							<c:if test="${!empty sort && sort == 'asc'}">
								<span class="glyphicon glyphicon-triangle-top list-sort"></span>
							</c:if>
							<c:if test="${!empty sort && sort == 'desc'}">
								<span class="glyphicon glyphicon-triangle-bottom list-sort"></span>
							</c:if>	
							</c:if>
							<c:if test="${!empty orderField && orderField != 'totalPrice'}">
								<span class="glyphicon glyphicon-sort list-sort"></span>
							</c:if>
						</a>
	                </th>
	                <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="">
	                    <spring:message code="otherOutlay.memo" />
	                </th>
	                <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="">
	                    <a id="sortTimeTransaction" href="#" class="table-sort">
							<spring:message code="otherOutlay.timeTransaction" />
							<c:if test="${!empty orderField && orderField == 'timeTransaction'}">
							<c:if test="${!empty sort && sort == 'asc'}">
								<span class="glyphicon glyphicon-triangle-top list-sort"></span>
							</c:if>
							<c:if test="${!empty sort && sort == 'desc'}">
								<span class="glyphicon glyphicon-triangle-bottom list-sort"></span>
							</c:if>	
							</c:if>
							<c:if test="${!empty orderField && orderField != 'timeTransaction'}">
								<span class="glyphicon glyphicon-sort list-sort"></span>
							</c:if>
						</a>
	                </th>
	                <th class="sorting_disabled" rowspan="1" colspan="1" aria-label="">
	                </th>
	            </tr>
	        </thead>
	        <tbody id="ajaxListContent">
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
	                        <td>${item.memo}</td>
							<td>${item.timeTransactionStr}</td>
	                        <td>
	                        	<c:if test="${1==p.otherOutlay.modify}">
	                        	<a href="${pageContext.request.contextPath}/otherOutlay/update?sn=${item.sn}"
	                            	class="btn btn-info btn-xs edit">
	                                <i class="fa fa-edit"></i>
	                                <spring:message code="common.edit" />
	                            </a>
	                            </c:if>
	                            <c:if test="${1==p.otherOutlay.delete}">
	                            <a href="#" data-toggle="modal" data-target="#myModal" class="btn btn-danger btn-xs delete" 
									data-action="${pageContext.request.contextPath}/otherOutlay/delete/request/?otherOutlaySn=${item.sn}" 
									data-render="#ajaxListData">
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
	     <div class="row DTTTFooter">
	        <c:if test="${!empty otherOutlayList}">	
	        	<%@include file="../common/paging.jsp"%>
	        </c:if>
	    </div>
	</div>                            	
</div>


<script type="text/javascript">
	j(document).ready(function() {
		j("#sortTimeTransaction").click(function(){
			ajaxGetOtherOutlayTranList('timeTransaction',j('#sort').val());
		});
		
		j("#sortTotalPrice").click(function(){
			ajaxGetOtherOutlayTranList('totalPrice',j('#sort').val());
		});
		
		function ajaxGetOtherOutlayTranList(orderFieldValue,sortValue){
			var url = '${pageContext.request.contextPath}/otherOutlay/list/ajaxList';
			dialogWating.open();
			j.ajax({
				url : url,
				type : 'POST',
				data : {orderField:orderFieldValue,
					sort:sortValue,
					startDate:j('#dateFrom').val(),
					endDate:j('#dateTo').val(),
					page:j('#currentPage').val()},
				async : false,
				success : function(response) {
					if (response) {
						setTimeout(function(){
							dialogWating.close();
							j('#ajaxListData').html(response);
						}, 2000);
					}
				}
			});
		}
	});
</script>