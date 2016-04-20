<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Header -->
<%@include file="templates/header.jsp"%>
<!-- /Header -->

<div>
	<spring:message code="login.userId" var="login_userId"/>
	<spring:message code="login.password" var="login_password"/>
	
   <div class="login-container animated fadeInDown" style="margin:5% auto !important;">
        <div class="loginbox bg-white">
        	<div class="loginbox-title" style="color:darkorange"><spring:message code="login.loginTitle"/></div>
            <form:form method="POST" action="${pageContext.request.contextPath}/login/request" name="loginForm" class="form-signin">
                <div align="center" style="box-shadow: none !important;">
                    <img src="${pageContext.request.contextPath}/resources/image/logo.jpg" 
                    	style="max-width:50% !important; padding-top: 15px"/>
                </div>
                <div class="loginbox-textbox">
                    <input id="userId" name="userId" type="text" type="text" class="form-control" placeholder="${login_userId}" autofocus />
                </div>
                <div class="loginbox-textbox">
                    <input type="password" id="password" class="form-control" placeholder="${login_password}"  />
                    <input name="password" type="hidden" id="passwordMd5" maxlength="64"/>
                </div>
                <div class="loginbox-forgot" style="text-align: center;">
                    <div class="checkbox">
                        <input id="rememberMe" value="true"	name="rememberMe" type="checkbox" 
                        	class="login-checkbox" style="left:30% !important; opacity:1; padding-left:0px !important"/> 
                        <label for="input-append" class="control-label no-padding-right">
                        	<spring:message code="common.rememberMe"/></label>
                    </div>
                </div>
                <div class="loginbox-submit" style="padding-top: 10% ! important;">
                    <button class="btn btn-blue btn-primary btn-block" type="submit" id="login_login_btn">
                    	<spring:message code="common.signin"/></button>
                </div>
            </form:form>
        </div>
        
        <div class="logobox" style="padding: 15px !important; margin-top: 10px !important; text-align: center !important;">
            <a href="${pageContext.request.contextPath}/login?lang=en"><spring:message code="common.english"/></a> &frasl;
            <a href="${pageContext.request.contextPath}/login?lang=vi"><spring:message code="common.korean"/></a>
        </div>
    </div>
</div>

<script type="text/javascript">
	var j = jQuery.noConflict();
	
	function passwordToMd5(){
		var passwordMd5=''
		if(j('#password').val().length>0){
			passwordMd5 = j.md5(j('#password').val());
			j('#passwordMd5').val(passwordMd5);
		}else{
			j('#passwordMd5').val('');
		}
	}
	j(document).ready(function() {
		j('#login_login_btn').click(function(){
			passwordToMd5();
			j('#login_login_form').submit();
		});
		
		j(document).keypress(function(event){
			var keycode = (event.keyCode ? event.keyCode : event.which);
			if(keycode=='13'){
				j('#login_login_btn').click();
			}
		});
	});
</script>
<!-- Footer -->
<%@include file="templates/footer.jsp"%>
<!-- /Footer -->