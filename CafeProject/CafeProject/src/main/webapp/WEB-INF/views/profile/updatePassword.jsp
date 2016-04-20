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
							<spring:message code="staff.staffInfo"/>
						</h5>
						<!-- Search -->
						<div class="widget">
							<div class="widget-header bordered-bottom bordered-magenta">
	                        	<span class="widget-caption">
	                        		<%-- <spring:message code="staff.resetPassTitle" /> --%>
								</span>
							</div>
						</div>
						<!-- End Search -->
						<div class="well with-header  with-footer">
                            <div class="header bg-magenta"><spring:message code="staff.resetPassTitle" /></div>
                            <div class="table-toolbar">
							    
							</div>
                            <div class="table-responsive" align="center" style="width: 100%; display: inline-block; border:1px;">
                            	<form:form method="POST" id="submitForm" action="${pageContext.request.contextPath}/profile/update/updatePassword/request"
									class="form-horizontal form-bordered" align="center" modelAttribute="resetPassForm">
									
									<spring:message code="staff.currentPass" var="staff_currentPass"/>
									<spring:message code="staff.newPass" var="staff_newPass"/>
									<spring:message code="staff.retypeNewPass" var="staff_retypeNewPass"/>
									<c:if test="${!empty errorMessage}">
										<div class="alert-danger-ext" role="">${errorMessage}</div>
									</c:if>
									
									<div class="form-group">
										<form:label path="" class="col-md-4 col-md-offset-3 control-label">
											${staff_currentPass}</form:label>
										<div class="col-md-6 col-md-offset-3">
											<input type="password" class="form-control" id="currentPass"
												placeholder="${staff_currentPass}" required="required" autofocus="autofocus" maxlength="64"/>
											<form:input type="hidden" id="currentPassMd5" path="currentPass" maxlength="64"/>
										</div>
									</div>
									
									<div class="form-group">
										<form:label path="" class="col-md-4 col-md-offset-3 control-label">
											${staff_newPass}</form:label>
										<div class="col-md-6 col-md-offset-3">
											<input type="password" id="newPassword"
												class="form-control" placeholder="${staff_newPass}" required="required" maxlength="64"/>
											<form:input type="hidden" id="newPasswordMd5" path="newPassword" maxlength="64"/>
										</div>
									</div>
									
									<div class="form-group">
										<form:label path="" class="col-md-4 col-md-offset-3 control-label">
											${staff_retypeNewPass}</form:label>
										<div class="col-md-6 col-md-offset-3">
											<input type="password" id="retypeNewPassword"
												class="form-control" placeholder="${staff_retypeNewPass}" required="required" maxlength="64"/>
											<form:input type="hidden"  path="retypeNewPassword" id="retypeNewPasswordMd5"  maxlength="64"/>
										</div>
									</div>
								</form:form>
                            </div>
                        </div>
					</div>
					<div class="right-button-scrollbar">
	                	<button id="submitButton" type="button" class="btn btn-success">
							<i class="fa fa-check"></i><spring:message code="common.save" />
						</button>
						<script type="text/javascript">
							$("#submitButton").click(function() {
								passwordToMd5();
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
	function passwordToMd5(){
		var passwordMd5=''
		if(j('#currentPass').val().length>0){
			passwordMd5 = j.md5(j('#currentPass').val());
			j('#currentPassMd5').val(passwordMd5);
		}else{
			j('#currentPassMd5').val('');
		}
		
		if(j('#newPassword').val().length>0){
			passwordMd5 = j.md5(j('#newPassword').val());
			j('#newPasswordMd5').val(passwordMd5);
		}else{
			j('#newPasswordMd5').val('');
		}
		
		if(j('#retypeNewPassword').val().length>0){
			passwordMd5 = j.md5(j('#retypeNewPassword').val());
			j('#retypeNewPasswordMd5').val(passwordMd5);
		}else{
			j('#retypeNewPasswordMd5').val('');
		}
	}
	j(document).ready(function() {
		
	});
</script>
<!-- Footer -->
<%@include file="../templates/footer.jsp"%>
<!-- /Footer -->