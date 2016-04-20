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
							<spring:message code="statistic.title" />
						</h5>
						<div class="widget">
							<div class="widget-header bordered-bottom bordered-palegreen">
								<span class="widget-caption"><spring:message code="common.search"/></span>
							</div>
								
							<div class="widget-body">
								<!-- barRoom.jsp - END -->
								<div >
                                    <form class="form-inline form-therapist-timeline" role="form">
                                    	<div class="input-group">
                                            <div class="input-group-btn">
                                                <select id="cf_statistic_year_select" class="form-control-ext ipt-short" style="cursor: pointer;">
												    <c:forEach items="${yearList}" var="year" >
												    	<c:if test="${year.key==selectedYear}">
												    		<option value="${year.key}" selected="selected">${year.name}</option>
												    	</c:if>
												    	<c:if test="${year.key!=selectedYear}">
												    		<option value="${year.key}">${year.name}</option>
												    	</c:if>
												    </c:forEach>
												</select>
                                            </div>
                                        </div>
                                    </form>
                                 </div>
								<!--START AJAX-->
								<div id="cf_statistic_month_list" style="margin-top:10px;">
									
								</div>
								<!--END AJAX-->
							</div>
						</div>
						<!--END MAIN CONTENT-->
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
<!--END MAIN CONTENT-->

<!--START POPUP Modal HTML-->

<!--END POPUP Modal HTML-->
<!-- -->
<script type="text/javascript">
	var j = jQuery.noConflict();
	
	function searchCafeOrderModeMonth(){
		dialogWating.open();
		j.ajax({
   	        type: "POST",
   	        url: "${pageContext.request.contextPath}/statistic/list/getCafeOrderGroupByMonth",
   	         data:{
   	        	year:j('#cf_statistic_year_select').val()
   	         },
   	      	async: true,
   	         success: function(response){
    			j('#cf_statistic_month_list').html(response);
   	        	dialogWating.close();
	            }
	          });
	}
	
	j(document).ready(function() {
		searchCafeOrderModeMonth();
		
		j('#cf_statistic_year_select').change(function(){
			searchCafeOrderModeMonth();
		});
	});
</script>
<!-- -->
<style type="text/css">
    .bs-example{
    	margin: 20px;
    }
</style>
<%@include file="../templates/footer.jsp"%>	