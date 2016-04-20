<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<style>
.myCheckBox {
    opacity: inherit !important;
    position: inherit !important;
}
</style>

<div class="well with-header" style="padding-top: 40px;">
	<div class="header bg-blue" style="height: 36px;"></div>
	<div class="table-toolbar">
	    <div class="add-new">
	    </div>
	</div>
	<div id="editabledatatable_wrapper" class="dataTables_wrapper form-inline no-footer">
		<table class="table table-striped table-hover table-bordered dataTable no-footer" 
	    	id="editabledatatable" role="grid" aria-describedby="editabledatatable_info">
	        <thead>
	            <tr>
					<th style="width:5%;"><spring:message code="common.no" /></th>
					<th style="width:35%;"><spring:message code="permission.groupName" /></th>
					<th style="width:12%;"><spring:message code="permission.view" /></th>
					<th style="width:12%;"><spring:message code="permission.create" /></th>
					<th style="width:12%;"><spring:message code="permission.modify" /></th>
					<th style="width:12%;"><spring:message code="permission.delete" /></th>
					<th style="width:12%;"><spring:message code="permission.print" /></th>
				</tr>
	        </thead>
	        <tbody id="ajaxListContent">
	            <c:if test="${!empty permissionList}">
	                <c:set var="val" value="0" />
	                <c:forEach items="${permissionList}" var="item">
	                    <tr role="row" class="odd">
	                        <c:set var="val" value="${val+1}" />
	                        <td class="sorting_1">${val}</td>
	                        <td>
								${item.pageGroupName}
							</td>
							<td>
								<c:if test="${1==item.hasView}">
									<c:if test="${1==p.permission.modify}">
									<input id="view_${item.sn}" type="checkbox" ${item.viewChecked} name="permission" 
										value="${item.sn}" class="myCheckBox">
									</c:if>
									<c:if test="${1!=p.permission.modify}">
										<i class="glyphicon glyphicon-ok"></i>
									</c:if>
								</c:if>
								
							</td>
							<td>
								<c:if test="${1==item.hasCreate}">
									<c:if test="${1==p.permission.modify}">
									<input id="add_${item.sn}" type="checkbox" ${item.addChecked} name="permission" 
										value="${item.sn}" class="myCheckBox">
									</c:if>
									<c:if test="${1!=p.permission.modify}">
										<i class="glyphicon glyphicon-ok"></i>
									</c:if>
								</c:if>
							</td>
							<td>
								<c:if test="${1==item.hasModify}">
									<c:if test="${1==p.permission.modify}">
									<input id="modify_${item.sn}" type="checkbox" ${item.modifyChecked} name="permission" 
										value="${item.sn}" class="myCheckBox">
									</c:if>
									<c:if test="${1!=p.permission.modify}">
										<i class="glyphicon glyphicon-ok"></i>
									</c:if>
								</c:if>
							</td>
							<td>
								<c:if test="${1==item.hasDelete}">
									<c:if test="${1==p.permission.modify}">
									<input id="delete_${item.sn}" type="checkbox" ${item.deleteChecked} name="permission" 
										value="${item.sn}" class="myCheckBox">
									</c:if>
									<c:if test="${1!=p.permission.modify}">
										<i class="glyphicon glyphicon-ok"></i>
									</c:if>
								</c:if>
							</td>
							<td>
								<c:if test="${1==item.hasPrint}">
									<c:if test="${1==p.permission.modify}">
									<input id="print_${item.sn}" type="checkbox" ${item.printChecked} name="permission" 
										value="${item.sn}" class="myCheckBox">
									</c:if>
									<c:if test="${1!=p.permission.modify}">
										<i class="glyphicon glyphicon-ok"></i>
									</c:if>
								</c:if>
							</td>
	                    </tr>
	                </c:forEach>
	            </c:if>
	        </tbody>
	    </table>
	    <div class="row DTTTFooter">
	        <c:if test="${!empty permissionList}">	
	        	<%@include file="../common/paging.jsp"%>
	        </c:if>
	    </div>
	</div>                            	
</div>


<script type="text/javascript">
	var j = jQuery.noConflict();
	j(document).ready(function() {
		j('input:checkbox[name="permission"]').click(function(){
			var valueStr;
		   if(this.checked){
			   valueStr='1';
			}else{
				valueStr='0';
			}
		   var permissionSn=j(this).attr("value");//sn of selected record
		   var typeValue = j(this).attr("id");
		   typeValue=typeValue.replace('_'+permissionSn,'');
		   updatePermission(permissionSn,typeValue,valueStr);
		   
		});
		
		function updatePermission(permissionSnValue,typeValue,valueStr) {
			var url = '${pageContext.request.contextPath}/permission/list/ajaxUpdateClientPermission';
			j.ajax({
				url : url,
				type : 'POST',
				data : {permissionSn:permissionSnValue,
					type:typeValue,
					value:valueStr},
				async : true,
				success : function(response) {
					
				}
			});
		}
	});
</script>