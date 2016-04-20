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
						<h5 class="row-title before-blue">
							<i class="fa fa-tags blue"></i>
							<spring:message code="permission.listTitle" />
						</h5>
						<!-- Search -->
						<div class="widget">
							<div class="widget-header bordered-bottom bordered-palegreen">
								<span class="widget-caption"><spring:message code="common.search"/></span>
							</div>
							<div class="widget-body">
								<form class="form-horizontal form-bordered" role="form">
									<div>
										<span>
											<spring:message code="permission.roleName" />
										</span>
										&nbsp;&nbsp;
										<span>
											<select name="selectRole" id="selectRole" class="form-control-ext ipt-short">
												<c:if test="${!empty roleList}">
													<c:forEach items="${roleList}" var="item">
														<option value="<c:out value="${item.key}"/>">
															<c:out value="${item.value}" />
														</option>
													</c:forEach>
												</c:if>
											</select>
										</span>
									</div>
								</form>
							</div>
						</div>
						<!-- End Search -->
						
						<!-- Ajax -->
						<div id="ajaxWatingData"></div>
						<div id="ajaxListData"></div>
						<!-- End Ajax -->
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


<script type="text/javascript">
	var j = jQuery.noConflict();

	j(document).ready(function() {
		getPermissionList();
		function getPermissionList() {
			var url = '${pageContext.request.contextPath}/permission/list/ajaxGetClientPermissionList';
			dialogWating.open();
			j.ajax({
				url : url,
				type : 'POST',
				data : {roleSn:j('#selectRole').val()},
				async : false,
				success : function(response) {
					j('#ajaxListData').html(response);
					dialogWating.close();
				}
			});
		}
		
		j("#selectRole").change(function(){
			getPermissionList();
		});
	});
</script>


<!-- Footer -->
<%@include file="../templates/footer.jsp"%>
<!-- /Footer -->