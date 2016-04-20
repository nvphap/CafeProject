<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Header -->
<%@include file="templates/header.jsp"%>
<!-- /Header -->

<!-- Navbar -->
<%@include file="templates/topNavbar.jsp"%>
<!-- /Navbar -->

<!-- Main Container -->
<div class="main-container container-fluid">
	<!-- Page Container -->
	<div class="page-container">

		<!-- Page Sidebar -->
		<%@include file="templates/sideBarMenu.jsp"%>
		<!-- /Page Sidebar -->

		<!-- Page Content -->
		<div class="page-content">
			<!-- Page Breadcrumb -->
			<%@include file="templates/breadCrumb.jsp"%>
			<!-- /Page Breadcrumb -->

			<!-- Page Body -->
			<div class="page-body">
				<div class="row">
					<div class="col-lg-12 col-sm-12 col-xs-12">
						<h5 class="row-title before-blue">
							<i class="fa fa-tags red"></i>
							<spring:message code="common.permissionHeader" />
						</h5>
						<!-- Search -->
						<div class="widget">
							<div class="widget-header bordered-bottom bordered-red">
								<span class="widget-caption"></span>
							</div>
							<div class="widget-body">
								<div>
									<span><spring:message code="common.titlePermission" /></span>
								</div>
							</div>
						</div>
						<!-- End Search -->
						<div id="ajaxListData">
							
						</div>
						<!-- End Ajax -->
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

<!-- Footer -->
<%@include file="templates/footer.jsp"%>
<!-- /Footer -->