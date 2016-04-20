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
							 <spring:message code="food.addFoodUnitTitle"/>
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
                            <div class="header bg-magenta"><spring:message code="food.addFoodUnitTitle" /></div>
                            <div class="table-toolbar">
							    
							</div>
                            <div class="table-responsive" align="center" style="width: 100%; display: inline-block; border:1px;" id="staff_update_content">
                            	<form:form method="POST" id="submitForm" 
									action="${pageContext.request.contextPath}/foodUnit/add/request"
									class="form-horizontal form-bordered" role="form" modelAttribute="foodUnit" >
								
									<spring:message code="food.unitName" var="food_unitName"/>
									
									
									<c:if test="${!empty errorMessage}">
									<div class="form-group"><div class="col-md-6 col-md-offset-3">
										<div class="alert alert-warning fade in" style="margin-bottom: 0px;">
						                    <i class="fa-fw fa fa-times"></i>
						                    <strong><spring:message code="common.titleError" /></strong> 
						                    <br/>${errorMessage}
						            	</div>
									</div></div>
									</c:if>
									<div class="form-group">
										<div class="col-md-2"></div>
										<form:label path="" class="col-md-2 control-label">
											${food_unitName}<span class="widget-caption danger"> (*)</span></form:label>
										<div class="col-md-6">
											<form:input type="text" path="name" class="form-control" 
												placeholder="${food_unitName}" required="required" autofocus="autofocus" 
												maxlength="45"/>
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
</script>

<!-- Footer -->
<%@include file="../templates/footer.jsp"%>
<!-- /Footer -->