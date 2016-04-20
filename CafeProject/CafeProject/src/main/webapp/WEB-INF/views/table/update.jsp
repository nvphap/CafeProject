<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Header -->
<%@include file="../templates/header.jsp"%>
<!-- /Header -->

<!-- Navbar -->
<%@include file="../templates/topNavbar.jsp"%>
<!-- /Navbar -->

<!-- Main Container -->
<div class="main-container container-fluid">
	<!-- Page Container -->
	<div class="page-container">

		<!-- Page Sidebar -->
		<%@include file="../templates/sideBarMenu.jsp"%>
		<!-- /Page Sidebar -->

		<!-- Page Content -->
		<div class="page-content">
			<!-- Page Breadcrumb -->
			<%@include file="../templates/breadCrumb.jsp"%>
			<!-- /Page Breadcrumb -->

			<!-- Page Body -->
			<div class="page-body">
				<div class="row">
					<div class="col-lg-12 col-sm-12 col-xs-12">
						<h5 class="row-title before-magenta">
							<i class="fa fa-tags magenta"></i>
							<spring:message code="cafeTable.updateTitle"/>
						</h5>
						<div class="widget">
							<div class="widget-header bordered-bottom bordered-magenta">
	                        	<span class="widget-caption">
	                        		<%-- <spring:message code="staff.addTitle"/> --%>
								</span>
							</div>
						
							<!-- Form Input -->
							<!-- <div class="widget-body">
								
							</div> -->
						</div>
						<!-- End Form Input -->
						<div class="well with-header  with-footer">
                            <div class="header bg-magenta"><spring:message code="cafeTable.updateTitle" /></div>
                            <div class="table-toolbar">
							    
							</div>
                            <div class="table-responsive" align="center" style="width: 100%; display: inline-block; border:1px;" id="staff_update_content">
                            	<form:form method="POST" id="submitForm" 
									action="${pageContext.request.contextPath}/table/update/request"
									class="form-horizontal form-bordered" role="form" modelAttribute="cafeTable" >
								
									<spring:message code="cafeTable.name" var="cafeTable_name"/>
									<spring:message code="cafeTable.location" var="cafeTable_location"/>
									<spring:message code="cafeTable.tableGroupName" var="cafeTable_tableGroupName"/>
									<spring:message code="cafeTable.order" var="cafeTable_order"/>
									
									<c:if test="${!empty errorMessage}">
									<div class="form-group"><div class="col-md-6 col-md-offset-3">
										<div class="alert alert-warning fade in" style="margin-bottom: 0px;">
						                    <i class="fa-fw fa fa-times"></i>
						                    <strong><spring:message code="common.titleError" /></strong> 
						                    <br/>${errorMessage}
						            	</div>
									</div></div>
									</c:if>
									<form:input type="hidden" path="sn"/>
									<div class="form-group">
										<div class="col-md-2"></div>
										<form:label path="" class="col-md-2 control-label">
											${cafeTable_name}<span class="widget-caption danger"> (*)</span></form:label>
										<div class="col-md-6">
											<form:input type="text" path="name" class="form-control" 
												placeholder="${cafeTable_name}" required="required" autofocus="autofocus" 
												maxlength="128"/>
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-md-2"></div>
										<form:label path="" for="input-append"
											class="col-md-2 control-label">${cafeTable_location}<span class="widget-caption danger"> (*)</span></form:label>
										<div class="col-md-6">
											<form:input type="text" id="location" path="location"
												class="form-control" placeholder="${cafeTable_location}" required="required" maxlength="256"/>
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-2"></div>
										<form:label path="" for="input-append"
											class="col-md-2 control-label">${cafeTable_tableGroupName}<span class="widget-caption danger"> (*)</span></form:label>
										<div class="col-md-6">
											<select name="tableGroupSn" id="cf_table_group" class="form-control">
											    <c:forEach items="${tableGroupList}" var="group" >
											    	<c:if test="${group.sn==selectedGroup}">
											    		<option value="${group.sn}" selected="selected">${group.name}</option>
											    	</c:if>
											    	<c:if test="${group.sn!=selectedGroup}">
											    		<option value="${group.sn}">${group.name}</option>
											    	</c:if>
											    </c:forEach>
											</select>
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-2"></div>
										<form:label path="" for="input-append"
											class="col-md-2 control-label">${cafeTable_order}<span class="widget-caption danger"> (*)</span></form:label>
										<div class="col-md-6" id="cf_cafe_table_order">
											<%@include file="ajaxOrderList.jsp"%>
										</div>
									</div>
								</form:form>
                            </div>
                        </div>
					</div>
					
					<div class="right-button-scrollbar">
	                	<button id="submitButton" type="button" class="btn btn-success">
							<i class="fa fa-plus"></i><spring:message code="common.save" />
						</button>
						<script type="text/javascript">
							$("#submitButton").click(function() {
								  $("#submitForm").submit();
								});
						</script>
						<button id="backPage" type="button" class="btn btn-info" onclick="history.back(-1)">
							<spring:message code="common.back" />
						</button>
					</div>
	                
	                <!-- Top Page -->
	                	<%@include file="../common/moveToTop.jsp"%>
	                <!-- Top Page -->
	            </div>
			</div>
			<!-- /Page Body -->
		</div>
		<!-- /Page Content -->

	</div>
	<!-- /Page Container -->
	<!-- Main Container -->
</div>

<script type="text/javascript">
	var j = jQuery.noConflict();
	j(document).ready(function() {
		j("#price").keydown(function(e){
			isNumber(e);
		});
		
		function isNumber(event){
			var key = window.event ? event.keyCode : event.which;
		    if (event.keyCode == 8 || event.keyCode == 46
		     || event.keyCode == 37 || event.keyCode == 39){
		        return true;
		    }else if((key < 48) || (key > 57 && key < 96) || (key > 105 )){
		    	event.preventDefault();
		        return false;
		    }else{
		    	return true;
		    }
		    return true;
        }
		
		/* j('#cf_table_group').change(function(){
			var tableGroupSn = j('#cf_table_group').val();
			ajaxGetOrderList(tableGroupSn);
		});
		
		function ajaxGetOrderList(tableGroupSn){
			j.ajax({
	   	        type: "POST",
	   	        url: "${pageContext.request.contextPath}/table/view/ajaxGetOrderList",
	   	         data:{
	   	        	tableGroupSn:tableGroupSn
	   	         },
	   	      	async: true,
	   	        success: function(response){
	    			j('#cf_cafe_table_order').html(response);
   	            }
   	        });
		} */
	});
</script>

<!-- Footer -->
<%@include file="../templates/footer.jsp"%>
<!-- /Footer -->