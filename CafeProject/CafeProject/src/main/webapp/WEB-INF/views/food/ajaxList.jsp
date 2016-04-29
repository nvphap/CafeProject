<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<spring:message code="food.deleteConfirm" var="food_deleteConfirm"/>
<div class="well with-header horizontal-scroll" style="padding-top: 40px;">
	<div class="header bg-blue" style="height: 36px;"><spring:message code="food.listTitle"/></div>
	<div class="table-toolbar">
		<c:if test="${1==p.food.create}">
	    <div class="add-new">
	        <a id="editabledatatable_new" href="javascript:doUrl();" class="btn btn-default">
	            <i class="fa fa-plus"></i><spring:message code="common.new" />
	        </a>
	        <script>
				function doUrl() {
				    window.location = '${pageContext.request.contextPath}/food/add';
				}
			</script>
	    </div>
	    </c:if>
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
						<spring:message code="food.foodGroup" />
	                </th>
		            <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="">
	                    <a id="name" href="#" class="table-sort">
							<spring:message code="food.foodName" />
							<c:if test="${!empty orderField && orderField == 'name'}">
							<c:if test="${!empty sort && sort == 'asc'}">
								<span class="glyphicon glyphicon-triangle-top list-sort"></span>
							</c:if>
							<c:if test="${!empty sort && sort == 'desc'}">
								<span class="glyphicon glyphicon-triangle-bottom list-sort"></span>
							</c:if>	
							</c:if>
							<c:if test="${!empty orderField && orderField != 'name'}">
								<span class="glyphicon glyphicon-sort list-sort"></span>
							</c:if>
						</a>
	                </th>
	                <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="" >
	                    <a id="sortPrice" href="#" class="table-sort">
							<spring:message code="food.price" />
							<c:if test="${!empty orderField && orderField == 'price'}">
							<c:if test="${!empty sort && sort == 'asc'}">
								<span class="glyphicon glyphicon-triangle-top list-sort"></span>
							</c:if>
							<c:if test="${!empty sort && sort == 'desc'}">
								<span class="glyphicon glyphicon-triangle-bottom list-sort"></span>
							</c:if>	
							</c:if>
							<c:if test="${!empty orderField && orderField != 'price'}">
								<span class="glyphicon glyphicon-sort list-sort"></span>
							</c:if>
						</a>
	                </th>
	                <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="">
						<spring:message code="food.profit" />
	                </th>
	                <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="">
	                    <spring:message code="food.memo" />
	                </th>
	                <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="">
	                    <a id="lastUpdate" href="#" class="table-sort">
							<spring:message code="food.lastUpdate" />
							<c:if test="${!empty orderField && orderField == 'lastUpdate'}">
							<c:if test="${!empty sort && sort == 'asc'}">
								<span class="glyphicon glyphicon-triangle-top list-sort"></span>
							</c:if>
							<c:if test="${!empty sort && sort == 'desc'}">
								<span class="glyphicon glyphicon-triangle-bottom list-sort"></span>
							</c:if>	
							</c:if>
							<c:if test="${!empty orderField && orderField != 'lastUpdate'}">
								<span class="glyphicon glyphicon-sort list-sort"></span>
							</c:if>
						</a>
	                </th>
	                <th class="sorting_disabled" rowspan="1" colspan="1" aria-label="">
	                </th>
	            </tr>
	        </thead>
	        <tbody id="ajaxListContent">
	            <c:if test="${!empty foodList}">
	                <c:set var="val" value="0" />
	                <c:forEach items="${foodList}" var="item">
	                    <tr role="row" class="odd">
	                        <c:set var="val" value="${val+1}" />
	                        <td class="sorting_1">${val}</td>
	                         <td>
								${item.foodGroupName}
	                        </td>
	                        <td>
								${item.name}
	                        </td>
	                        <td>${item.priceStr}</td>
	                        <td>${item.profitStr}</td>
							<td>${item.memo}</td>
							<td>${item.lastUpdate}</td>
	                        <td>
	                        	<c:if test="${1==p.food.modify}">
	                        	<a href="${pageContext.request.contextPath}/food/update?sn=${item.sn}"
	                            	class="btn btn-info btn-xs edit">
	                                <i class="fa fa-edit"></i>
	                                <spring:message code="common.edit" />
	                            </a>
	                            </c:if>
	                            <c:if test="${1==p.food.delete}">
	                            <a href="#" data-toggle="modal" data-target="#myModal" class="btn btn-danger btn-xs delete" 
									data-action="${pageContext.request.contextPath}/food/delete/request/?foodSn=${item.sn}&sort=${sort}&orderField=${orderField}" 
									data-message="${food_deleteConfirm}" data-render="#ajaxListData">
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
		j("#sortPrice").click(function(){
			getAjaxFoodListSort('price',j('#sort').val());
		});
		
		j("#lastUpdate").click(function(){
			getAjaxFoodListSort('lastUpdate',j('#sort').val());
		});
		
		j("#name").click(function(){
			getAjaxFoodListSort('name',j('#sort').val());
		});
		function getAjaxFoodListSort(orderField,sortValue){
			var url = '${pageContext.request.contextPath}/food/list/ajaxList';
			dialogWating.open();
			j.ajax({
				url : url,
				type : 'POST',
				data : {sort:sortValue,
					orderField:orderField},
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