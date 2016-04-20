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
				<spring:message code="cafeOrder.foodName" var="cafeOrder_foodName" />
				<div class="row">
					<div class="col-lg-12 col-sm-12 col-xs-12">
						<h5 class="row-title before-blue">
							<i class="fa fa-tags blue"></i>
							<spring:message code="food.listTitle"/>
						</h5>
						<!-- Search -->
						<div class="widget">
							<div class="widget-header bordered-bottom bordered-palegreen">
								<span class="widget-caption"><spring:message code="common.search"/></span>
							</div>
							<div class="widget-body">
								<div >
                                    <form class="form-inline form-therapist-timeline" role="form">
	                                    <div>
	                                        <div class="form-group form-input-therapist-timeline">
	                                            <span class="input-icon icon-right inverted" data-title="">
	                                            	<input id="cf_food_price" type="hidden" class="form-control">
													<input id="cf_food_sn" type="hidden" class="form-control">
													<input id="cf_food_name" class="form-control-ext ipt-medium form-control" placeholder="${cafeOrder_foodName}">
													<input id="autoFoodSetup" type="hidden" value="cf_food_sn|cf_food_name|1|1|cf_food_price" class="form-control">
													<%@include file="../suggestion/oneFoodSuggestion.jsp"%>
													<i id="cf_cafe_clear_food" class="fa fa-times red clear-icon"></i>
	                                            </span>
	                                        </div>
	                                      </div>
                                    </form>
                                </div>
							</div>
						</div>
						<!-- End Search -->
						<!-- Ajax -->
						<div id="ajaxListData"></div>
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

<!-- Popup Delete -->
<%@include file="../common/ajaxDeleteConfirm.jsp"%>
<!-- End Popup Delete -->

<script type="text/javascript">
	var j = jQuery.noConflict();
	j(document).ready(function() {
		ajaxGetFoodList('','lastUpdate','desc');
		
		function ajaxGetFoodList(foodSn,orderField,sortValue){
			var url = '${pageContext.request.contextPath}/food/list/ajaxList';
			dialogWating.open();
			j.ajax({
				url : url,
				type : 'POST',
				data : {sort:sortValue,
					foodSn:foodSn},
				async : false,
				success : function(response) {
					if (response) {
						setTimeout(function(){
							dialogWating.close();
							j('#ajaxListData').html(response);
						}, 2000);
					}
				}
			});
		}
		
		j('#cf_cafe_clear_food').click(function(){
			j('#cf_food_price').val('');
			j('#cf_food_sn').val('');
			j('#cf_food_name').val('');
			j('#cf_food_sn').change();
		});
		
		j('#cf_food_sn').change(function(){
			 ajaxGetFoodList(j('#cf_food_sn').val(),'lastUpdate','desc');
		});
	});
</script>


<!-- Footer -->
<%@include file="../templates/footer.jsp"%>
<!-- /Footer -->