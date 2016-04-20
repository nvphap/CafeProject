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
</head>
<body>
	<div style="border:2px solid black">
		<div style="margin:5px 5px 5px 5px;">
			<div align="center"><h2><b><spring:message code="cafeOrder.printLabel1"/></b></h2></div>
			<div align="center">${printOrder.address}</div>
			<div align="center">${printOrder.address2}</div>
			<div align="center">
				<span><spring:message code="cafeOrder.phone"/>:${printOrder.phone}</span>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<span><spring:message code="cafeOrder.mobile"/>:${printOrder.mobile}</span>
			</div>
			<div align="center">
				<h2><b><spring:message code="cafeOrder.printLabel2"/></b></h2>
			</div>
			<div>${printOrder.datePrint}</div>
			<div style="font-weight:bold;"><spring:message code="cafeOrder.tableName"/>:${printOrder.tableName}</div>
			<div>
				<table class="table table-striped table-hover table-bordered dataTable no-footer">
			        <thead>
			            <tr>
			                <th class="sorting_asc" rowspan="1" colspan="1">
				            </th>
				            <th class="sorting" rowspan="1" colspan="1">
			                    <spring:message code="cafeOrder.foodName" />
			                </th>
			                <th class="sorting" rowspan="1" colspan="1">
									<spring:message code="cafeOrder.price" />
			                </th>
			                <th class="sorting" rowspan="1" colspan="1">
									<spring:message code="cafeOrder.numOfFood2" />
			                </th>
			                <th class="sorting" rowspan="1" colspan="1" >
			                    <spring:message code="cafeOrder.discount" />
			                </th>
			                <th class="sorting" rowspan="1" colspan="1">
			                    <spring:message code="cafeOrder.totalMoney2" />
			                </th>
			            </tr>
			        </thead>
			        <tbody>
			            <c:if test="${!empty printOrder.cafeOrderList}">
			                <c:set var="val" value="0" />
			                <c:forEach items="${printOrder.cafeOrderList}" var="item">
			                    <tr class="odd cf_order_record cf_order_record_group_${item.cafeTableSn}">
			                        <c:set var="val" value="${val+1}" />
			                        <td class="sorting_1">
			                        	<div>${val}</div>
			                        </td>
			                        <td>
			                        	<div>${item.foodName}(${item.foodUnitName})	</div>
			                        </td>
			                        <td>
			                        	<div>${item.priceStr}</div>
			                        </td>
			                        <td>
			                        	<div>${item.numOfFood}</div>
			                        </td>
			                        <td>
			          					<div>${item.discount}%</div>
			          				</td>
			          				<td>
			          					<div>${item.totalMoneyStr}</div>
			          				</td>
			                    </tr>
			                </c:forEach>
			            </c:if>
			        </tbody>
			    </table>
			</div>
			<div align="right"><spring:message code="cafeOrder.totalMoney"/>:<b>${printOrder.totalMoney}</b></div>
			<div align="center"><h4><b><spring:message code="cafeOrder.printLabel3"/></b></h4></div>
			<div align="left">${printOrder.email}</div>
			<div align="left">${printOrder.website}</div>
		</div>
	</div>
	<script type="text/javascript">
		var j = jQuery.noConflict();
		j(document).ready(function() {			
			var beforePrint = function() {
		        
		    };

		    var afterPrint = function() {
		        
		    };

		    if (window.matchMedia) {
		        var mediaQueryList = window.matchMedia('print');
		        mediaQueryList.addListener(function(mql) {
		            if (mql.matches) {
		                beforePrint();
		            } else {
		                afterPrint();
		            }
		        });
		    }
		    //window.onbeforeprint = beforePrint;
		   // window.onafterprint = afterPrint;
		});
	</script>
</body>
</html>