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
                                <!-- barRoom.jsp -->
                                <div >
                                    <form class="form-inline form-therapist-timeline" role="form">
                                    	<div class="input-group">
                                            <div class="input-group-btn">
                                                <select name="cf_statistic_day_select_type" id="cf_statistic_day_select_type" class="form-control-ext ipt-short" style="cursor: pointer;">
												    <c:forEach items="${typeList}" var="type" >
												    	<c:if test="${type.key==selectedType}">
												    		<option value="${type.key}" selected="selected">${type.name}</option>
												    	</c:if>
												    	<c:if test="${type.key!=selectedType}">
												    		<option value="${type.key}">${type.name}</option>
												    	</c:if>
												    </c:forEach>
												</select>
                                            </div>
                                        </div>
                                         <div class="form-group form-input-therapist-timeline">
                                        	<input id="cf_statistic_from_date" name="cf_statistic_from_date" value="${fromDate}" 
												readonly="readonly" class="form-control-ext ipt-short-btn" />
                                        	<span style="font-weight:bold">~</span>
                                        </div>
                                        <div class="form-group form-input-therapist-timeline">
                                        	<input id="cf_statistic_to_date" name="cf_statistic_to_date" value="${toDate}" 
												readonly="readonly" class="form-control-ext ipt-short-btn" />
										</div>
										 <div class="form-group form-input-therapist-timeline">
											<button id="cf_statistic_search" type="button" class="btn btn-primary">
												<i class="searchicon fa fa-search"></i>
												<spring:message code="common.search"/>
											</button>
                                        </div>
                                    </form>
                                 </div>
								<!-- barRoom.jsp - END -->
								
								<!--START AJAX-->
								<div id="orderDetailList" >
									
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
	
	j(document).ready(function() {
		findStatisticInformation();
    	
    	function searchCafeOrderModeList(){
    		dialogWating.open();
			j.ajax({
	   	        type: "POST",
	   	        url: "${pageContext.request.contextPath}/statistic/list/getCafeOrderList",
	   	         data:{
	   	        	 //cafeTableSn:j('#cf_statistic_day_select_table').val(),
	   	        	fromDate:j('#cf_statistic_from_date').val(),
	   	        	toDate:j('#cf_statistic_to_date').val()
	   	         },
	   	      	async: true,
	   	         success: function(response){
	    			j('#orderDetailList').html(response);
	   	        	dialogWating.close();
   	            }
   	          });
		}
    	
    	
    	function searchCafeOrderModeFood(){
    		dialogWating.open();
			j.ajax({
	   	        type: "POST",
	   	        url: "${pageContext.request.contextPath}/statistic/list/getCafeOrderGroupByFood",
	   	         data:{
	   	        	fromDate:j('#cf_statistic_from_date').val(),
	   	        	toDate:j('#cf_statistic_to_date').val()
	   	         },
	   	      	async: true,
	   	         success: function(response){
	    			j('#orderDetailList').html(response);
	   	        	dialogWating.close();
   	            }
   	          });
		}
    	
    	function searchCafeOrderModeDate(){
    		dialogWating.open();
			j.ajax({
	   	        type: "POST",
	   	        url: "${pageContext.request.contextPath}/statistic/list/getCafeOrderGroupByDate",
	   	         data:{
	   	        	fromDate:j('#cf_statistic_from_date').val(),
	   	        	toDate:j('#cf_statistic_to_date').val()
	   	         },
	   	      	async: true,
	   	         success: function(response){
	    			j('#orderDetailList').html(response);
	   	        	dialogWating.close();
   	            }
   	          });
		}
    	
    	function searchCafeOrderModeTable(){
    		dialogWating.open();
			j.ajax({
	   	        type: "POST",
	   	        url: "${pageContext.request.contextPath}/statistic/list/getCafeOrderGroupByTable",
	   	         data:{
	   	        	fromDate:j('#cf_statistic_from_date').val(),
	   	        	toDate:j('#cf_statistic_to_date').val()
	   	         },
	   	      	async: true,
	   	         success: function(response){
	    			j('#orderDetailList').html(response);
	   	        	dialogWating.close();
   	            }
   	          });
		}
    	
    	function searchCafeOrderModeDay(){
    		dialogWating.open();
			j.ajax({
	   	        type: "POST",
	   	        url: "${pageContext.request.contextPath}/statistic/list/getCafeOrderGroupByDay",
	   	         data:{
	   	        	fromDate:j('#cf_statistic_from_date').val(),
	   	        	toDate:j('#cf_statistic_to_date').val()
	   	         },
	   	      	async: true,
	   	         success: function(response){
	    			j('#orderDetailList').html(response);
	   	        	dialogWating.close();
   	            }
   	          });
		}
    	
    	function searchCafeOrderModeHour(){
    		dialogWating.open();
			j.ajax({
	   	        type: "POST",
	   	        url: "${pageContext.request.contextPath}/statistic/list/getCafeOrderGroupByHour",
	   	         data:{
	   	        	fromDate:j('#cf_statistic_from_date').val(),
	   	        	toDate:j('#cf_statistic_to_date').val()
	   	         },
	   	      	async: true,
	   	         success: function(response){
	    			j('#orderDetailList').html(response);
	   	        	dialogWating.close();
   	            }
   	          });
		}
    	
    	function searchCafeOrderModeTime(){
    		dialogWating.open();
			j.ajax({
	   	        type: "POST",
	   	        url: "${pageContext.request.contextPath}/statistic/list/getCafeOrderGroupByTime",
	   	         data:{
	   	        	fromDate:j('#cf_statistic_from_date').val(),
	   	        	toDate:j('#cf_statistic_to_date').val()
	   	         },
	   	      	async: true,
	   	         success: function(response){
	    			j('#orderDetailList').html(response);
	   	        	dialogWating.close();
   	            }
   	          });
		}
    	
    	
    	function findStatisticInformation(){
    		var type = j('#cf_statistic_day_select_type').val();
    		if(1==type){//list
    			searchCafeOrderModeList();
    		}else if(2==type){//in case statistic of food
    			searchCafeOrderModeFood();
    		}else if(3==type){//in case statistic of table
    			searchCafeOrderModeTable();
    		}else if(4==type){//in case statistic of time
    			searchCafeOrderModeTime();
    		}else if(5==type){//in case statistic of hour
    			searchCafeOrderModeHour();
    		}else if(6==type){//in case is date in month
    			searchCafeOrderModeDate();
    		}else if(7==type){//in case day of week
    			searchCafeOrderModeDay();
    		}
    	}
    	
    	j('#cf_statistic_search').click(function(){
    		findStatisticInformation();
    	});
    	
    	j('#cf_statistic_from_date').datetimepicker({
			inline:false,
			timepicker : false,
			lang : '${languageCode}',
			format : 'Y-m-d',
			defaultDate:'${fromDate}',
			onChangeDateTime:function(dp,$input){
				j(this).hide();
			}
		});
    	
    	j('#cf_statistic_to_date').datetimepicker({
			inline:false,
			timepicker : false,
			lang : '${languageCode}',
			format : 'Y-m-d',
			defaultDate:'${toDate}',
			onChangeDateTime:function(dp,$input){
				j(this).hide();
			}
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