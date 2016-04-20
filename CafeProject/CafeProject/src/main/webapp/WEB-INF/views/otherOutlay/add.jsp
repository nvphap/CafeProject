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
							<spring:message code="cafeTable.addTitle"/>
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
                            <div class="header bg-magenta"><spring:message code="otherOutlay.addTitle" /></div>
                            <div class="table-toolbar">
							    
							</div>
                            <div class="table-responsive" align="center" style="width: 100%; display: inline-block; border:1px;">
                            	<form:form method="POST" id="submitForm" 
									action="${pageContext.request.contextPath}/otherOutlay/add/request"
									class="form-horizontal form-bordered" role="form" modelAttribute="otherOutlayForm" >
								
									<spring:message code="otherOutlay.name" var="otherOutlay_name"/>
									<spring:message code="otherOutlay.price" var="otherOutlay_price"/>
									<spring:message code="otherOutlay.totalPrice" var="otherOutlay_totalPrice"/>
									<spring:message code="otherOutlay.numberOfItem" var="otherOutlay_numberOfItem"/>
									<spring:message code="otherOutlay.memo" var="otherOutlay_memo"/>
									<spring:message code="otherOutlay.timeTransaction" var="otherOutlay_timeTransaction"/>
									
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
										<form:input id="otherOutlaySn" path="otherOutlaySn" type="hidden"/>
										<div class="col-md-2"></div>
										<form:label path="" class="col-md-2 control-label">
											${otherOutlay_name}<span class="widget-caption danger"> (*)</span></form:label>
										<div class="col-md-6">
											<form:input type="text" path="otherOutlayName" class="form-control" 
												placeholder="${otherOutlay_name}" required="required" autofocus="autofocus" 
												maxlength="128"/>
											<input id="autoOtherOutlaySetup" class="form-control" type="hidden" value="otherOutlaySn|otherOutlayName|1|0">
											<%@include file="../suggestion/oneOtherOutlaySuggestion.jsp"%>
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-md-2"></div>
										<form:label path="" for="input-append"
											class="col-md-2 control-label">${otherOutlay_numberOfItem}<span class="widget-caption danger"> (*)</span></form:label>
										<div class="col-md-6">
											<form:input type="text" id="numOfOutlay" path="numOfOutlay"
												class="form-control" placeholder="${otherOutlay_numberOfItem}" required="required" 
												maxlength="3"/>
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-md-2"></div>
										<form:label path="" for="input-append"
											class="col-md-2 control-label">${otherOutlay_totalPrice}<span class="widget-caption danger"> (*)</span></form:label>
										<div class="col-md-6">
											<form:input type="text" id="totalPrice" path="totalPrice"
												class="form-control" placeholder="${otherOutlay_totalPrice}" required="required" 
												maxlength="11"/>
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-md-2"></div>
										<form:label path="" for="input-append"
											class="col-md-2 control-label">${otherOutlay_memo}</form:label>
										<div class="col-md-6">
											<form:textarea path="memo" id="memo" name="memo" class="textarea-full"></form:textarea>
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-md-2"></div>
										<form:label path="" for="input-append"
											class="col-md-2 control-label">${otherOutlay_timeTransaction}<span class="widget-caption danger"> (*)</span></form:label>
										<div class="col-md-6">
											<form:input path="timeTransactionStr" id="timeTransactionStr" value="${timeTransaction}" readonly="true" class="form-control"></form:input>
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
	var j = jQuery.noConflict();
	j(document).ready(function() {
		j("#totalPrice").keydown(function(e){
			isNumber(e);
		});
		
		j('#timeTransactionStr').click( function() {
    		j('#otherOutlaySn').val('');
    		j('#otherOutlayName').val('');
    		j('#otherOutlaySn').change();
    	});
		
		j("#totalPrice").change(function(e){
			if(j('#totalPrice').val().length==0){
				j('#totalPrice').val(0);
			}
		});
		
		j("#numOfOutlay").keydown(function(e){
			isNumber(e);
		});
		
		j("#numOfOutlay").change(function(e){
			if(j('#numOfOutlay').val().length==0){
				j('#numOfOutlay').val(1);
			}
		});
		
		function isNumber(event){
			var key = window.event ? event.keyCode : event.which;
		    if (event.keyCode == 8 || event.keyCode == 46
		     || event.keyCode == 37 || event.keyCode == 39){
		        return true;
		    }else if((key < 48) || (key > 57 && key < 96) || (key > 105 )){
		    	event.preventDefault();
		        return false;
		    }else{
		    	return true;
		    }
		    return true;
        }
		
		j('#timeTransactionStr').datetimepicker({
			inline:false,
			lang : '${languageCode}',
			format : 'Y-m-d H:i',
			step:30,
			defaultDate:new Date(),
			onChangeDateTime:function(dp,$input){
				j(this).hide();
			}
		});
	});
</script>

<!-- Footer -->
<%@include file="../templates/footer.jsp"%>
<!-- /Footer -->