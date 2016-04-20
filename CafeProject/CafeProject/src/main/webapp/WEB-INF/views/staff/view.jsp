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
						<!-- Search -->
						<div class="widget">
							<div class="widget-header bordered-bottom bordered-palegreen">
								<span class="widget-caption"><spring:message code="common.search"/></span>
							</div>
							<!-- SEARCH-START -->
							<div class="widget-body">
								<div>
									
								</div>
								<!-- End Search -->
							</div>
						</div>
						<!-- SEARCH-END  -->
						<div class="well with-header  with-footer">
                            <div class="header bg-green"><spring:message code="staff.titleView"/><%-- ${viewStaff.fullName} --%></div>
                            <div class="table-toolbar">
							    <div class="add-new">
							        
							    </div>
							</div>
                            <div class="table-responsive" align="center" style="width: 100%; display: inline-block; border:1px;" id="staff_view_content">
								<table class="table table-bordered" style="width:100%;">
									<spring:message code="staff.fullName" var="staff_fullName"/>
									<spring:message code="staff.l.role" var="staff_l_role" />
									<spring:message code="staff.userId" var="staff_userId" />
									<spring:message code="staff.memo" var="staff_memo"/>
									<spring:message code="staff.status" var="staff_status"/>
									<spring:message code="staff.shopName" var="staff_shopName"/>
									<tbody>
										<%-- <tr>
							                <td class="td-title my-border" style="text-algin:left">${staff_shopName}</td>
							                <td>${viewStaff.cafeShopName}</td>
							             </tr>  --%> 
										<tr>
							                <td class="td-title my-border" style="text-algin:left">${staff_userId}</td>
							                <td>${viewStaff.userId}</td>
							             </tr>                          	
								            <tr>
								                <td class="td-title my-border" align="left">${staff_fullName}</td>
								                <td>${viewStaff.name}</td>
								            </tr>
								           
								            <tr>
								                <td class="td-title my-border" >${staff_l_role}</td>
								                <td>${viewStaff.roleTitle}</td>
								             </tr>
											<tr>
							                  <td class="td-title my-border" style="text-algin:left">${staff_memo}</td>
							                  <td>${viewStaff.memo}</td>
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

<script type="text/javascript">
	var j = jQuery.noConflict();

	j(document).ready(function() {
		initViewPage();
		getStaffBreakTime();
		
		function getStaffBreakTime(){
			//create url with keysearch and sn list if exist them
			var ajaxUrl = "${pageContext.request.contextPath}/profile/view/individual/ajaxGetStaffBreakTime";
	    	j.ajax({
			url: ajaxUrl,
			type: 'POST',
			data: {staffSn:j('#list_search_staff_sn').val()},
			async: true,
			success: function(response){
	    			if(response.length>0){
	    				updateTimeFromJsonIndividualPage(JSON.parse(response));
	    			}
			}
	    	});
		}
		
		function updateTimeFromJsonIndividualPage(timeJsonList){
			var container = j('#time_break_display');
			container.find('.time-start').text('----');
			container.find('.time-end').text('----');
			var timeEle;
			j.each(timeJsonList, function (index, item) {
			    if(1==item.day){//sunday
			    	timeEle = container.find('.time-sunday').eq(0);
			    }else if(2==item.day){
			    	timeEle = container.find('.time-monday').eq(0);
			    }else if(3==item.day){
			    	timeEle = container.find('.time-tuesday').eq(0);
			    }else if(4==item.day){
			    	timeEle = container.find('.time-wednesday').eq(0);
			    }else if(5==item.day){
			    	timeEle = container.find('.time-thursday').eq(0);
			    }else if(6==item.day){
			    	timeEle = container.find('.time-friday').eq(0);
			    }else if(7==item.day){
			    	timeEle = container.find('.time-saturday').eq(0);
			    }
			    if(timeEle.length>0){
			    	timeEle.find('.time-start').eq(0).text(item.startTime);
			    	timeEle.find('.time-end').eq(0).text(item.endTime);
			    }
			}); 
		}
		
		j('#backPage').click(function() {
			window.location.href ="${pageContext.request.contextPath}/staff/list";
		});
		
		j('#editStaff').click(function() {
			window.location.href ="${pageContext.request.contextPath}/staff/update/?sn=${viewStaff.sn}";
		});
		
		function initViewPage(){
			updateStatusSearch();
			updateRoleSearch();
		}
		
		function updateStatusSearch(){
			if(0==j('#selectStatus').val()){
				j('#list_search_status_list').val('1;3');//working and leave out
			}else{
				j('#list_search_status_list').val(j('#selectStatus').val());
			}
		}
		
		j('#selectStatus').change(function(){
			updateStatusSearch();
		});
		
		j('#selectRole').change(function(){
			updateRoleSearch();
		});
		
		function updateRoleSearch(){
			if(0==j('#selectRole').val()){
				j('#list_search_role_sn_list').val('');
			}else{
				j('#list_search_role_sn_list').val(j('#selectRole').val());
			}
		}
		
		function getStaffDetail(staffSn){
			//create url with keysearch and sn list if exist them
			dialogWating.open();
	    	j.ajax({
				url: "${pageContext.request.contextPath}/staff/view/ajaxGetDetailInformation",
				type: 'POST',
				data: {staffSn:staffSn},
				async: true,
				success: function(response){
					j("#staff_view_content").html(response);
					getStaffBreakTime();
					dialogWating.close();
		    	},
		    	error:function(){
		    		dialogWating.close();
		    	}
	    	});
		}
		
		j('#list_search_staff_sn').change(function(){
			if(j('#list_search_staff_sn').val().length>0){
				getStaffDetail(j('#list_search_staff_sn').val());	
			}
		});
		
		j('#list_staff_search_clear_btn').click(function(){
			j('#list_search_staff_sn').val('');
			j('#list_search_staff_key').val('');
		});
		
	});
</script>

<!-- Footer -->
<%@include file="../templates/footer.jsp"%>
<!-- /Footer -->