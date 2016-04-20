<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<select name="position" id="position" class="form-control">
    <c:forEach items="${orderList}" var="position" >
    	<c:if test="${position==selectedPos}">
    		<option value="${position}" selected="selected">${position}</option>
    	</c:if>
    	<c:if test="${position!=selectedPos}">
    		<option value="${position}">${position}</option>
    	</c:if>
    </c:forEach>
</select>
										