<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><spring:message code="menu.cafeBernard"/></title>

<meta name="description" content="Dashboard" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link href="${pageContext.request.contextPath}/resources/css/timeline.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/resources/css/newstyle.css" rel="stylesheet" /> 
<link href="${pageContext.request.contextPath}/resources/azweb/img/favicon.png" rel="shortcut icon" type="image/x-icon" />

<link href="${pageContext.request.contextPath}/resources/azweb/fonts/fontawesome-webfont.woff2"  rel="stylesheet"/>
<link href="${pageContext.request.contextPath}/resources/azweb/fonts/glyphicons-halflings-regular.woff2"  rel="stylesheet"/>
<link href="${pageContext.request.contextPath}/resources/azweb/fonts/weathericons-regular-webfont.woff"  rel="stylesheet"/>

<!--Basic Styles-->
<link id="bootstrap-rtl-link" href="" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/resources/azweb/css/customize.css"  rel="stylesheet"/>
<link href="${pageContext.request.contextPath}/resources/azweb/css/weather-icons.min.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/resources/azweb/css/bootstrap.min.css" rel="stylesheet" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css"/>

<!--Fonts-->
<link href="${pageContext.request.contextPath}/resources/azweb/fonts/font-open-sans.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/resources/azweb/fonts/font-roboto.css" rel='stylesheet' type='text/css' />

<!--Beyond styles-->
<link href="${pageContext.request.contextPath}/resources/azweb/css/beyond.min.css" rel="stylesheet" type="text/css" /> 
<link href="${pageContext.request.contextPath}/resources/azweb/css/demo.min.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/resources/azweb/css/typicons.min.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/resources/azweb/css/animate.min.css" rel="stylesheet" />
<link href="" id="skin-link" rel="stylesheet" type="text/css" />

<!-- Latest compiled and minified JavaScript -->
<script src="${pageContext.request.contextPath}/resources/js/jquery/jquery.min.js"></script>

<!-- Bootstrap multiselect -->
<link href="${pageContext.request.contextPath}/resources/bootstrap-multiselect-2.0/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/resources/bootstrap-multiselect-2.0/bootstrap-multiselect.js"></script>
	
<!-- Date time picker -->
<link href="${pageContext.request.contextPath}/resources/datetimepicker/jquery.datetimepicker.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/resources/datetimepicker/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/datetimepicker/jquery.datetimepicker.js"></script>
    
<!-- CKEDITOR -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/ckeditor/ckeditor.js"></script>
        	
<!--Skin Script: Place this script in head to load scripts for skins and rtl support-->
<script src="${pageContext.request.contextPath}/resources/azweb/js/skins.min.js"></script>
	
<!-- Auto complete -->
<script src="${pageContext.request.contextPath}/resources/jquery-ui-1.11.4/jquery-ui.min.js" type="text/javascript" ></script>
<script src="${pageContext.request.contextPath}/resources/jquery-ui-1.11.4/jquery-ui-i18n.min.js" type="text/javascript" ></script>
<link href="${pageContext.request.contextPath}/resources/jquery-ui-themes-1.11.4/themes/ui-lightness/jquery-ui.min.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/resources/css/autocomplete.css" rel="stylesheet" />

<!-- Declare full calendar lib -->
<!-- sweet alert -->
<script src="${pageContext.request.contextPath}/resources/sweetalert-master/dist/sweetalert.min.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/resources/sweetalert-master/dist/sweetalert.css" rel="stylesheet" type="text/css" media="screen" />
<link href="${pageContext.request.contextPath}/resources/sweetalert-master/themes/facebook/facebook.css" rel="stylesheet" type="text/css"/>
 <!-- jquery md5 -->
 <script src="${pageContext.request.contextPath}/resources/jquery-md5-master/jquery.md5.js" type="text/javascript"></script>    
</head>

<body>
	<spring:message code="common.ok" var="common_ok"/>
	<spring:message code="common.error" var="common_error"/>
	<!-- Popup Waiting -->
	<style>
	.waiting-dialog .modal-dialog {
		width: 300px;
		padding-top: 40px;
	}
	.waiting-dialog .modal-dialog .modal-header{
		padding: 10px;
	    border-radius: 0px;
	}
	</style>
	
	<script type="text/javascript">
		var dialogWating = null;
		var htmlWating = '<div style="text-align: center;">';
		htmlWating += '<img style="width: 50px;" src="${pageContext.request.contextPath}'+
					  '/resources/image/ajaxLoaderBlue.gif" /></div>';
	
		var htmlWatingTiny = '<div style="position: fixed; left: 50%; top:30%">';
		htmlWatingTiny += '<img style="width: 20px;" src="${pageContext.request.contextPath}'+
					  '/resources/image/ajaxLoaderBlue.gif" /></div>';
	
		$(document).ready(function() {
			dialogWating = new BootstrapDialog({
				message : function(dialogRef) {
					var $message = $(htmlWating);
					return $message;
				},
				title : '<spring:message code="common.watingDialog" />',
				cssClass : 'waiting-dialog',
				closable : false
			});
		});
		
		function displayError(error){
	   		swal({title:"<span class='error-title'>${common_error}</span>",
	   			text:error,
	   			html: true,
	   			confirmButtonText:'${common_ok}'});
	   	}
		
		function isNumber(event){
			var key = window.event ? event.keyCode : event.which;
		    if (event.keyCode == 8 || event.keyCode == 46
		     || event.keyCode == 37 || event.keyCode == 39){
		        return true;
		    }else if(key < 48 || (key > 57 && key < 96) || (key > 105 )){
		    	event.preventDefault();
		        return false;
		    }else{
		    	return true;
		    }
		    return true;
        }
		
		
	</script>
	<!-- End Popup Waiting -->

	<!-- Loading Container -->
    <div class="loading-container loading-inactive">
        <div class="loader"></div>
    </div>
    <!--  /Loading Container -->