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
<title>Hospital Web</title>

<meta name="description" content="Dashboard" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />

<link href="${pageContext.request.contextPath}/resources/azweb/img/favicon.png" rel="shortcut icon" type="image/x-icon" />
<link href="${pageContext.request.contextPath}/resources/azweb/fonts/fontawesome-webfont.woff2"  rel="stylesheet"/>
<link href="${pageContext.request.contextPath}/resources/azweb/fonts/glyphicons-halflings-regular.woff2"  rel="stylesheet"/>
<link href="${pageContext.request.contextPath}/resources/azweb/fonts/weathericons-regular-webfont.woff"  rel="stylesheet"/>

<!--Basic Styles-->
<link id="bootstrap-rtl-link" href="" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/resources/azweb/css/customize.css"  rel="stylesheet"/>
<link href="${pageContext.request.contextPath}/resources/azweb/css/weather-icons.min.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/resources/azweb/css/bootstrap.min.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/resources/azweb/css/font-awesome.min.css" rel="stylesheet" />

<!--Fonts-->
<link href="${pageContext.request.contextPath}/resources/azweb/fonts/font-open-sans.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/resources/azweb/fonts/font-roboto.css" rel='stylesheet' type='text/css' />

<!--Beyond styles-->
<link href="${pageContext.request.contextPath}/resources/azweb/css/beyond.min.css" rel="stylesheet" type="text/css" /> 
<link href="${pageContext.request.contextPath}/resources/azweb/css/demo.min.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/resources/azweb/css/typicons.min.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/resources/azweb/css/animate.min.css" rel="stylesheet" />
</head>
<!--Head Ends-->
<!--Body-->
<body class="body-404" style="/* background-color: #4F5052; */">
    <div class="error-header"> </div>
    <div class="container ">
        <section class="error-container text-center">
            <h1 style="color: #980A1C">404</h1>
            <div class="error-divider">
                <h2>page not found</h2>
                <br/>
                <p class="description">We Couldn’t Find This Page</p>
                <br/><span><spring:message code="common.errorType" /></span>
            </div>
            <a href="${pageContext.request.contextPath}/home" class="return-btn">
            	<i class="fa fa-home"></i>Home
            </a>
        </section>
    </div>    
</body>
<!--Body Ends-->
</html>