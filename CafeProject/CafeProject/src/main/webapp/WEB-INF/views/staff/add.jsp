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
							<spring:message code="staff.listStaffTitle"/>
						</h5>
						<div class="widget">
							<div class="widget-header bordered-bottom bordered-palegreen">
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
                            <div class="header bg-magenta"><spring:message code="staff.addTitle" /></div>
                            <div class="table-toolbar">
							    
							</div>
                            <div class="table-responsive" align="center" style="width: 100%; display: inline-block; border:1px;" id="staff_update_content">
                            	<form:form method="POST" id="submitForm" 
									action="${pageContext.request.contextPath}/staff/add/request"
									class="form-horizontal form-bordered" role="form" modelAttribute="staffForm" >
								
									<spring:message code="staff.userId" var="staff_userId"/>
									<spring:message code="staff.password" var="staff_password"/>
									<spring:message code="staff.retypePass" var="staff_retypePass"/>
									<spring:message code="staff.staffCode" var="staff_staffCode"/>
									<spring:message code="staff.fullName" var="staff_fullName"/>
									<spring:message code="staff.mobile" var="staff_mobile"/>
									<spring:message code="staff.phone" var="staff_phone"/>
									<spring:message code="staff.address" var="staff_address"/>
									<spring:message code="staff.email" var="staff_email"/>
									<spring:message code="staff.l.role" var="staff_l_role"/>
									<spring:message code="staff.room" var="staff_room"/>
									<spring:message code="staff.care" var="staff_care"/>
									
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
											${staff_userId}<span class="widget-caption danger"> (*)</span></form:label>
										<div class="col-md-6">
											<form:input type="text" path="userId" class="form-control" 
												placeholder="${staff_userId}" required="required" autofocus="autofocus" 
												maxlength="40"/>
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-md-2"></div>
										<form:label path="" for="input-append"
											class="col-md-2 control-label">${staff_fullName}<span class="widget-caption danger"> (*)</span></form:label>
										<div class="col-md-6">
											<form:input type="text" id="name" path="name"
												class="form-control" placeholder="${staff_fullName}" required="required" 
												maxlength="64"/>
										</div>
									</div>
	
									<div class="form-group">
										<div class="col-md-2"></div>
										<form:label path="" for="input-append"
											class="col-md-2 control-label">${staff_l_role}<span class="widget-caption danger"> (*)</span></form:label>
										<div class="col-md-6">
											<form:select path="selectRole" class="form-control">
				                   				 <form:options items="${roleList}"/>
				                			</form:select>
										</div>
									</div>
	
									<div class="form-group">
										<div class="col-md-2"></div>
										<form:label path="" for="input-append"
											class="col-md-2 control-label">
											<spring:message code="staff.memo"/>
										</form:label>
										<div class="col-md-6">
											<form:textarea path="memo" id="staffMemo" name="staffMemo" class="textarea-full"></form:textarea>
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-md-2"></div>
										<form:label path="" for="input-append"
											class="col-md-2 control-label">${staff_password}<span class="widget-caption danger"> (*)</span></form:label>
										<div class="col-md-6">
											<input type="password" id="password"
												class="form-control" placeholder="${staff_password}" 
												required="required" maxlength="64"/>
											<form:input type="hidden" id="passwordMd5" path="password" maxlength="64"/>
										</div>
									</div>
	
									<div class="form-group">
										<div class="col-md-2"></div>
										<form:label path="" for="input-append"
											class="col-md-2 control-label">${staff_retypePass}<span class="widget-caption danger"> (*)</span></form:label>
										<div class="col-md-6">
											<input type="password" id="retypePassword"
												class="form-control" placeholder="${staff_retypePass}" required="required" 
												maxlength="64"/>
												<form:input type="hidden" id="retypePasswordMd5" path="retypePassword" maxlength="64"/>
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
		if(j('#password').val().length>0){
			passwordMd5 = j.md5(j('#password').val());
			j('#passwordMd5').val(passwordMd5);
		}else{
			j('#passwordMd5').val('');
		}
		
		if(j('#retypePassword').val().length>0){
			passwordMd5 = j.md5(j('#retypePassword').val());
			j('#retypePasswordMd5').val(passwordMd5);
		}else{
			j('#retypePasswordMd5').val('');
		}
	}
</script>

<!-- Footer -->
<%@include file="../templates/footer.jsp"%>
<!-- /Footer -->