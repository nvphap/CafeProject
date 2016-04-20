s<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<div class="page-sidebar sidebar-fixed" id="sidebar">
    <!-- Page Sidebar Header-->
    <!-- <div class="sidebar-header-wrapper">
    </div> -->
    <!-- /Page Sidebar Header -->
    
    <!-- Sidebar Menu -->
    <div class="slimScrollDiv" style="position: relative; width:auto;">
        <ul class="nav sidebar-menu" style="overflow: inherit;">
            <!--Cafe order-->
           <c:if test="${1==p.cafeOrder.view}">
            <li data-link="/cafeOrder/list" data-id="#reservationMenu" class="noChildMenu" id="reservationMenu">
                <a href="${pageContext.request.contextPath}/cafeOrder/list" class="menu-dropdown">
                    <i class="menu-icon fa fa-bookmark"></i>
                    <span class="menu-text"> <spring:message code="menu.cafeOrderMgt" /></span>
                </a>
            </li>
            </c:if>
            <!--Cafe table management-->
            <c:if test="${1==p.table.view}">
            <li data-link="/table/add" data-id="#tableMenu" class="noChildMenu" style="display: none;"></li>
	            <li data-link="/table/update" data-id="#tableMenu" class="noChildMenu" style="display: none;"></li>
	            <li data-link="/table/list" data-id="#tableMenu" class="noChildMenu" id="tableMenu">
                <a href="${pageContext.request.contextPath}/table/list" class="menu-dropdown">
                    <i class="menu-icon fa fa-tasks"></i>
                    <span class="menu-text"> <spring:message code="menu.tableMgt" /></span>
                </a>
            </li>
            </c:if>
            <!--Food management-->
            <c:if test="${1==p.food.view}">
            <li data-link="/food/add" data-id="#foodMenu" class="noChildMenu" style="display: none;"></li>
	            <li data-link="/food/update" data-id="#foodMenu" class="noChildMenu" style="display: none;"></li>
	            <li data-link="/food/list" data-id="#foodMenu" class="noChildMenu" id="foodMenu">
                <a href="${pageContext.request.contextPath}/food/list" class="menu-dropdown">
                    <i class="menu-icon fa fa-tasks"></i>
                    <span class="menu-text"> <spring:message code="menu.foodMgt" /></span>
                </a>
            </li>
            </c:if>
            <c:if test="${1==p.foodUnit.view}"> 
            <!--Food unit management-->
            <li data-link="/foodUnit/add" data-id="#foodUnitMenu" class="noChildMenu" style="display: none;"></li>
	            <li data-link="/foodUnit/update" data-id="#foodUnitMenu" class="noChildMenu" style="display: none;"></li>
	            <li data-link="/foodUnit/list" data-id="#foodUnitMenu" class="noChildMenu" id="foodUnitMenu">
                <a href="${pageContext.request.contextPath}/foodUnit/list" class="menu-dropdown">
                    <i class="menu-icon fa fa-tasks"></i>
                    <span class="menu-text"> <spring:message code="menu.foodUnitMgt" /></span>
                </a>
            </li> 
            </c:if>
            
            <!--Food unit management-->
            <c:if test="${1==p.otherOutlay.view}">
            <li data-link="/otherOutlay/add" data-id="#otherOutlayMenu" class="noChildMenu" style="display: none;"></li>
	            <li data-link="/otherOutlay/update" data-id="#otherOutlayMenu" class="noChildMenu" style="display: none;"></li>
	            <li data-link="/otherOutlay/list" data-id="#otherOutlayMenu" class="noChildMenu" id="otherOutlayMenu">
                <a href="${pageContext.request.contextPath}/otherOutlay/list" class="menu-dropdown">
                    <i class="menu-icon fa fa-tasks"></i>
                    <span class="menu-text"> <spring:message code="menu.otherOutlayMgt" /></span>
                </a>
            </li> 
            </c:if>
            <!-- Statistic -->
            <c:if test="${1==p.statistic.view}">
            <li id="menu_statistics_group">
                <a href="#" class="menu-dropdown">
                   <i class="menu-icon fa fa-bar-chart-o"></i>
                    <span class="menu-text"> <spring:message code="statistic.title" /></span>
                    <i class="menu-expand"></i>
                </a>
                <ul class="submenu" id="menu_sub_statistics_group">
                    <li data-link="/statistic/list" data-class="">
                        <a href="${pageContext.request.contextPath}/statistic/list" id="menu_sub_statistics_list">
                            <span class="menu-text"><spring:message code="menu.statistic" /></span>
                        </a>
                    </li>
                     <c:if test="${3!=loginUser.roleSn}">
	                    <li data-link="/statistic/list/monthStatistic" data-class="">
	                        <a href="${pageContext.request.contextPath}/statistic/list/monthStatistic">
	                            <span class="menu-text"><spring:message code="statistic.monthly" /></span>
	                        </a>
	                    </li>
                    </c:if>
                </ul>
            </li> 
            </c:if>
             <!-- Permission -->
            <c:if test="${1==p.staff.view}">
            <li>
	            <li data-link="/staff/list" data-id="#staffMenu" class="noChildMenu" id="staffMenu">
                <a href="${pageContext.request.contextPath}/staff/list" class="menu-dropdown">
                    <i class="menu-icon fa fa-tasks"></i>
                    <span class="menu-text"> <spring:message code="menu.staffMgt" /></span>
                </a>
            </li> 
            </c:if>
            
            <!-- Permission -->
            <c:if test="${1==p.permission.view}">
            <li>
	            <li data-link="/permission/list" data-id="#permissionMenu" class="noChildMenu" id="permissionMenu">
                <a href="${pageContext.request.contextPath}/permission/list" class="menu-dropdown">
                    <i class="menu-icon fa fa-tasks"></i>
                    <span class="menu-text"> <spring:message code="menu.permission" /></span>
                </a>
            </li> 
            </c:if>
        
            <li data-link=""><div style="margin-bottom: 50px;"></div></li>
        </ul>
        <div class="slimScrollBar" style="width: 3px; position: absolute; top: 0px; opacity: 0.4; 
	        display: none; border-radius: 7px; z-index: 99; left: 1px; height: 558px; 
	        background: rgb(45, 195, 232);"></div>
        <div class="slimScrollRail" style="width: 3px; height: 100%; position: absolute; top: 0px; 
	        display: none; border-radius: 7px; opacity: 0.2; z-index: 90; left: 1px; 
	        background: rgb(51, 51, 51);"></div>
    </div>
    <!-- /Sidebar Menu -->
</div>
<script type="text/javascript">
	$(document).ready(function() {
		
		//Set current selected menu
		var host = window.location.host; 
		var url = window.location.href.toString(); 
		var web = '${pageContext.request.contextPath}'; 
		
		url = url.replace('http://', '');
		url = url.replace('https://', '');
		url = url.replace(host, '');
		url = url.replace(web, '');
		
		if (url.indexOf("#") != -1) {
			var temp = url.split('#');
			url = arr[0];
		}
		var arr = url.split('?');
		var currentPage = arr[0]; 
		
		$("div.slimScrollDiv ul li.noChildMenu").each(function() {
			var link = $(this).data('link');
			var id = $(this).data('id');
			if (link != null && link == currentPage) {
				$(id).addClass('active');
			}	
		});
		$("div.slimScrollDiv ul li ul.submenu").each(function() {
			$(this).removeClass('open');
		});
		$("div.slimScrollDiv ul li ul.submenu li").each(function() {
			var link = $(this).data('link');
			if (link == currentPage) {
				var classId = $(this).data('class'); 
				if (classId != '') {
					$(classId).addClass('active');
				} else {
					$(this).addClass('active');
				}
				$(this).parent().css('display', 'block');
				$(this).parent().parent().addClass('open');
			}	
		});
		
		if (currentPage == '/setting/view/system') {
			$('#cbServerSetting').attr("checked", true);
		}
		if (currentPage == '/setting/view/hospital') {
			$('#cbHospitalSetting').attr("checked", true);
		}
	});
</script>

<!-- Function for button slide up Reservations sidebar menu -->
<script>
$(function(){
    $(".btnCollapse").click(function(){
        $("#myMenu").slideUp();
        $("#shrank-menu").removeClass('open');
    });
    
    $('#menu_statistics_group').click(function(){
		if($('#menu_sub_statistics_group').css('display')=='none'){//in collapse state
			$('#menu_statistics_group').slideUp();
			$('#menu_sub_statistics_list')[0].click();
		}
	});
});
</script>