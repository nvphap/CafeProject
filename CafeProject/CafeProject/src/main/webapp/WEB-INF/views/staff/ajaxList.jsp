<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<div class="well with-header horizontal-scroll" style="padding-top: 40px;">
	<div class="header bg-blue" style="height: 36px;"><spring:message code="staff.listStaffTitle"/></div>
	<div class="table-toolbar">
		<c:if test="${1==p.staff.create}">
	    <div class="add-new">
	        <a id="editabledatatable_new" href="javascript:doUrl();" class="btn btn-default">
	            <i class="fa fa-plus"></i><spring:message code="common.new" />
	        </a>
	        <script>
				function doUrl() {
				    window.location = '${pageContext.request.contextPath}/staff/add';
				}
			</script>
	    </div>
	    </c:if>
	</div>
	<div id="editabledatatable_wrapper" class="dataTables_wrapper form-inline no-footer">
		<input id="sort" type="hidden" value="${sort}">
		<input id="keySearch" type="hidden" value="${keySearch}">
		
	    <table class="table table-striped table-hover table-bordered dataTable no-footer" 
	    	id="editabledatatable" role="grid" aria-describedby="editabledatatable_info">
	        <thead>
	            <tr role="row">
	                <th class="sorting_asc" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-sort="ascending" aria-label="">
		            </th>
		             <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="">
	                    <spring:message code="staff.shopName" />
	                </th>
		            <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="">
	                    <spring:message code="staff.userId" />
	                </th>
	                <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="" >
	                    <spring:message code="staff.fullName" />
	                </th>
	                <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="">
						<spring:message code="staff.l.role" />
	                </th>
	                 
	                 <th class="sorting" tabindex="0" aria-controls="editabledatatable" rowspan="1" colspan="1" 
	                	aria-label="">
						<spring:message code="staff.lastUpdate" />
	                </th>
	                <th class="sorting_disabled" rowspan="1" colspan="1" aria-label="">
	                </th>
	            </tr>
	        </thead>
	        <tbody id="ajaxListContent">
	            <c:if test="${!empty staffList}">
	                <c:set var="val" value="0" />
	                <c:forEach items="${staffList}" var="item">
	                    <tr role="row" class="odd">
	                        <c:set var="val" value="${val+1}" />
	                        <td class="sorting_1">${val}</td>
	                        <td>${item.cafeShopName}</td>
	                        <td>${item.userId}</td>
	                        <td>
	                            <a href="${pageContext.request.contextPath}/staff/view?sn=${item.sn}" target="_blank">
									${item.name}
								</a>
	                        </td>
							<td>${item.roleTitle}</td>
							<td>${item.lastUpdate}</td>
	                        <td>
	                        	<c:if test="${1==p.staff.modify}">
	                        	<a href="${pageContext.request.contextPath}/staff/update?sn=${item.sn}"
	                            	class="btn btn-info btn-xs edit">
	                                <i class="fa fa-edit"></i>
	                                <spring:message code="common.edit" />
	                            </a>
	                            </c:if>
	                            <c:if test="${1==p.staff.delete}">
	                            <a href="#" data-toggle="modal" data-target="#myModal" class="btn btn-danger btn-xs delete" 
									data-action="${pageContext.request.contextPath}/staff/delete/request/?staffSn=${item.sn}&page=${currentPage}" 
									data-message="${staff_deleteConfirm}" data-render="#ajaxListData">
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
	        <c:if test="${!empty staffList}">	
	        	<%@include file="../common/paging.jsp"%>
	        </c:if>
	    </div>
	</div>                            	
</div>