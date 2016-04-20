<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<div class="well with-header horizontal-scroll" style="padding-top: 40px;">
	<div class="header bg-blue" style="height: 36px;"><spring:message code="food.foodUnitMgt"/></div>
	<div class="table-toolbar">
		<c:if test="${1==p.foodUnit.create}">
	    <div class="add-new">
	        <a id="editabledatatable_new" href="javascript:doUrl();" class="btn btn-default">
	            <i class="fa fa-plus"></i><spring:message code="common.new" />
	        </a>
	        <script>
				function doUrl() {
				    window.location = '${pageContext.request.contextPath}/foodUnit/add';
				}
			</script>
	    </div>
	    </c:if>
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
	                    <spring:message code="food.unitName" />
	                </th>
	                <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="" >
	                	<spring:message code="food.lastUpdate" />
	                </th>
	                <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="" >
	                </th>
	            </tr>
	        </thead>
	        <tbody id="ajaxListContent">
	            <c:if test="${!empty foodUnitList}">
	                <c:set var="val" value="0" />
	                <c:forEach items="${foodUnitList}" var="item">
	                    <tr role="row" class="odd">
	                        <c:set var="val" value="${val+1}" />
	                        <td class="sorting_1">${val}</td>
	                        <td>
								${item.name}
	                        </td>
	                        <td>${item.lastUpdate}</td>
	                        <td>
	                        	<c:if test="${1==p.foodUnit.modify}">
	                        	<a href="${pageContext.request.contextPath}/foodUnit/update?sn=${item.sn}"
	                            	class="btn btn-info btn-xs edit">
	                                <i class="fa fa-edit"></i>
	                                <spring:message code="common.edit" />
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
	/* var j = jQuery.noConflict(); */
	j(document).ready(function() {
		
	});
</script>