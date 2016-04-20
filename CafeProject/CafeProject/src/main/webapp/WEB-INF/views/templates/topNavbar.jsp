<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <div class="navbar-container">
            <!-- Navbar Barnd -->
            <div class="navbar-header pull-left">
                <a href="${pageContext.request.contextPath}" class="navbar-brand">
                	<span style="font-weight:bold;"><spring:message code="menu.cafeBernard"/></span>
                    <%-- <small>
                        <img src="${pageContext.request.contextPath}/resources/azweb/img/LOGO1.png" alt="" />
                    </small> --%>
                </a>
            </div>
            <!-- /Navbar Barnd -->
            <!-- Sidebar Collapse -->
            <div class="sidebar-collapse" id="sidebar-collapse">
                <i class="collapse-icon fa fa-bars"></i>
            </div>
            <!-- /Sidebar Collapse -->
            <!-- Account Area and Settings --->
            <div class="navbar-header pull-right">
                <div class="navbar-account">
                    <ul class="account-area">
                        <li>
                            <!--/Messages Dropdown-->
                        </li>
                        <li>
                            <a class="login-area dropdown-toggle" data-toggle="dropdown">
                                <div class="avatar" title="View your public profile">
                                    <img src="${pageContext.request.contextPath}/resources/azweb/img/avatars/unknown-person.png" />
                                </div>
                                <section>
                                    <h2>
                                        <span class="profile">
                                            <span>${loginUser.name}</span>
                                        </span>
                                    </h2>
                                </section>
                            </a>
                            <!--Login Area Dropdown-->
                            <ul class="pull-right dropdown-menu dropdown-arrow dropdown-login-area">
                                <li class="username">
                                    <a>${loginUser.name}</a>
                                </li>
                                <li class="email">
                                    <a style="color: #2DC3E8;">${loginUser.name}</a>
                                </li>
                                <!--Avatar Area-->
                                <li>
                                    <div class="avatar-area">
                                        <img src="${pageContext.request.contextPath}/resources/azweb/img/avatars/unknown-person.png" class="avatar" />
                                        <span class="caption"><spring:message code="common.changePhoto"/></span>
                                    </div>
                                </li>
                                <!--Avatar Area-->
                                <li class="edit">
                                    <a href="${pageContext.request.contextPath}/profile/view/individual" class="pull-left">
                                    	<spring:message code="common.profile"/></a>
                                    <a href="javascript:doLogout();" class="pull-right"><spring:message code="common.logout"/></a>
                                    <script>
										function doLogout() {
										    window.location = '${pageContext.request.contextPath}/logout/request';
										}
									</script>
                                </li>
                                <!--Theme Selector Area-->
                                <li class="theme-area">
                                    <ul style="padding: 0px;" id="skin-changer"></ul>
                                </li>
                                <!--/Theme Selector Area-->
                                <li class="dropdown-footer">
                                    <!-- <a href=""></a> -->
                                </li>
                            </ul>
                            <!--/Login Area Dropdown-->
                        </li>
                        <!-- /Account Area -->
                    </ul>
 					
 					<!-- Settings -->
                    <!-- Settings -->
                </div>
            </div>
            <!-- /Account Area and Settings -->
        </div>
    </div>
</div>

<style>
.navbar .navbar-inner .navbar-header .navbar-account.setting-open .setting {
	right: 300px;
}
.navbar .navbar-inner .navbar-header .navbar-account .setting-container {
	width: 300px;
}
</style>

<style type="text/css">
.class-compact-menu{
	margin-top:10px !important;
}
</style>
<script type="text/javascript">
	/* check menu-compact */ 
	$( "#sidebar-collapse" ).click(function() {
		$("div.scrollingDiv ul li a i.menu-icon").each(function() {
			if ( $("#sidebar").hasClass( "menu-compact" )) {
		    	$(this).removeClass("class-compact-menu");
		    } else {
		        $(this).addClass("class-compact-menu");
		    }
		});
	});
	/* check menu-compact --- End --- */
</script>

<script type="text/javascript">
var j = jQuery.noConflict();
j(document).ready(function() {
	collapseLeftMenu();
	function collapseLeftMenu(){
		j('#sidebar-collapse').click();
	}
	
	j("#cbHospitalSetting").click(function(){
		window.location = '${pageContext.request.contextPath}/setting/view/hospital';
	});
	j("#cbServerSetting").click(function(){
		window.location = '${pageContext.request.contextPath}/setting/view/system';
	});
	
});
</script>
                    