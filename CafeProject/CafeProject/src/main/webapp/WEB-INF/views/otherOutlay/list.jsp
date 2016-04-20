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
<spring:message code="otherOutlay.name" var="otherOutlay_name" />
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
							<spring:message code="otherOutlay.addTitle"/>
						</h5>
						<!-- Search -->
						<div class="widget">
							<div class="widget-header bordered-bottom bordered-palegreen">
								<span class="widget-caption"><spring:message code="common.search"/></span>
							</div>
							<div class="widget-body">
                                 <form class="form-inline form-therapist-timeline" role="form">
                                 	<div class="form-group form-input-therapist-timeline">
                                        <select id="cf_other_outlay_mode" class="form-control-ext ipt-tiny-short">
											<c:forEach items="${modeList}" var="mode" >
										    	<c:if test="${mode.key==selectedMode}">
										    		<option value="${mode.key}" selected="selected">${mode.name}</option>
										    	</c:if>
										    	<c:if test="${mode.key!=selectedMode}">
										    		<option value="${mode.key}">${mode.name}</option>
										    	</c:if>
										    </c:forEach>
										</select>
									</div>
                                     <div class="form-group form-input-therapist-timeline">
                                     	<input id="dateFrom" name="dateFrom" value="${fromDate}" 
											readonly="readonly" class="form-control-ext ipt-short-btn" />
                                     	<span style="font-weight:bold">~</span>
                                     </div>
                                     <div class="form-group form-input-therapist-timeline">
                                     	<input id="dateTo" name="dateTo" value="${toDate}" 
											readonly="readonly" class="form-control-ext ipt-short-btn"/>
									</div>
									<div class="form-group form-input-therapist-timeline">
										<span class="input-icon icon-right inverted" data-title="">
											<input id="cf_other_outlay_sn" type="hidden"/>
											<input id="cf_other_outlay_name" class="form-control-ext ipt-medium form-control" placeholder="${otherOutlay_name}" 
												autofocus="autofocus" maxlength="128"/>
											<input id="autoOtherOutlaySetup" type="hidden" value="cf_other_outlay_sn|cf_other_outlay_name|1|0">
											<%@include file="../suggestion/oneOtherOutlaySuggestion.jsp"%>
											<i id="cf_other_outlay_clean" class="fa fa-times red clear-icon"></i>
										</span>
									</div>
									<div class="form-group form-input-therapist-timeline">
										<button id="cf_other_outlay_search" type="button" class="btn btn-primary">
											<i class="searchicon fa fa-search"></i>
											<spring:message code="common.search"/>
										</button>
                                     </div>
									
                                 </form>
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
		ajaxGetOtherOutlayTranList('timeTransaction','desc');
		
		function ajaxGetOtherOutlayTranList(orderFieldValue,sortValue){
			var url = '${pageContext.request.contextPath}/otherOutlay/list/ajaxList';
			dialogWating.open();
			j.ajax({
				url : url,
				type : 'POST',
				data : {orderField:orderFieldValue,
					sort:sortValue,
					startDate:j('#dateFrom').val(),
					endDate:j('#dateTo').val(),
					mode:j('#cf_other_outlay_mode').val(),
					otherOutlaySn:j('#cf_other_outlay_sn').val(),
					nameSearch:j('#cf_other_outlay_name').val()},
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
		j('#cf_other_outlay_clean').click(function(){
			j('#cf_other_outlay_sn').val('');
			j('#cf_other_outlay_name').val(''); 
		});
		
		j('#cf_other_outlay_search').click(function(){
			ajaxGetOtherOutlayTranList('timeTransaction','desc');
		});
		
		j('#dateFrom').datetimepicker({
			timepicker : false,
			lang : '${languageCode}',
			format : 'Y-m-d',
			defaultDate : '${fromDate}',
			onChangeDateTime:function(dp,$input){
				j(this).hide();
			}
		}); 
		
		j('#dateTo').datetimepicker({
			timepicker : false,
			lang : '${languageCode}',
			format : 'Y-m-d',
			defaultDate : '${toDate}',
			onChangeDateTime:function(dp,$input){
				j(this).hide();
			}
		}); 
	});
</script>


<!-- Footer -->
<%@include file="../templates/footer.jsp"%>
<!-- /Footer -->