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

<style>
.my-border {
	font-weight: bold;
}
</style>
                            
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
						<h5 class="row-title before-green">
							<i class="fa fa-tags green"></i>
							<spring:message code="staff.titleView" />
						</h5>
						
						<div class="well with-header  with-footer">
                            <div class="header bg-green"><spring:message code="staff.titleView" /></div>
                            <div class="table-responsive" align="center" style="width: 100%; display: inline-block; border:1px;">
                            	<input id="viewStaffSn" type="hidden" value="${viewStaff.sn}">
                            	<spring:message code="staff.fullName" var="staff_fullName"/>
								<spring:message code="staff.staffCode" var="staff_staffCode" />
								<spring:message code="staff.l.role" var="staff_l_role" />
								<spring:message code="staff.userId" var="staff_userId" />
								<spring:message code="staff.memo" var="staff_memo"/>
								<spring:message code="staff.change" var="staff_change"/>
								<spring:message code="staff.workingTime" var="staff_workingTime"/>
								<spring:message code="staff.password" var="staff_password"/>
								<spring:message code="common.change" var="common_change"/>
								
								<table class="table table-bordered" style="width:100%;">
									<tbody>
										<tr>
						                    <td class="td-title my-border" style="text-algin:left">${staff_userId}</td>
						                    <td colspan="2">${viewStaff.userId}</td>
						                 </tr>                           	
						                <tr>
						                    <td class="td-title my-border" align="left">${staff_fullName}</td>
						                    <td colspan="2">${viewStaff.name}</td>
						                </tr>
						                <tr>
						                    <td class="td-title my-border" >${staff_l_role}</td>
						                    <td colspan="2">${viewStaff.roleTitle}</td>
						                 </tr>
										<tr>
						                    <td class="td-title my-border" style="text-algin:left">${staff_memo}</td>
						                    <td colspan="2">${viewStaff.memo}</td>
						                </tr>
						                <tr>
						                    <td class="td-title my-border" style="text-algin:left">${staff_password}</td>
						                    <td colspan="2">
						                    	<span style="float: left; margin-right: 20px;">********</span>
					                    		<span style="float: left;">
													<a id="changePass" href="${pageContext.request.contextPath}/profile/update/updatePassword" class="btn btn-info btn-xs edit">
						                                <i class="fa fa-edit"></i>
						                                <spring:message code="common.edit" />
						                            </a>
												</span>
					                    	</td>
						                </tr>
						            </tbody>
								</table>
							</div>        
                        </div>
                    </div>
				</div>
			</div>
			<!-- /Page Body -->
		</div>
		<!-- /Page Content -->

	</div>
	<!-- /Page Container -->
	<!-- Main Container -->
</div>
<input id="output" type="hidden" value="">
<!-- POPUP START-->
<!-- POPUP END-->

<script type="text/javascript">
	var j = jQuery.noConflict();
	j(document).ready(function() {
	});
</script>

<!-- Footer -->
<%@include file="../templates/footer.jsp"%>
<!-- /Footer -->