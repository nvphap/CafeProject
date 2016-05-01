<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div>
	<form class="form-horizontal form-therapist-timeline" role="form">
		<div class="form-group">
			<div class="col-sm-12 no-padding-right"> 
				<!-- selectAll - END -->
				<c:set var="val" value="1" />
				<c:set var="group" value="0" />
				<table>
				<c:forEach items="${tableList}" var="table">
					<c:set var="tableGroupSn" value="${table.tableGroupSn}" />
					 <c:if test="${group!=tableGroupSn}">
					 	<c:if test="${val==1}">
					 		<tr><td><div class="cf-blue-bold" style="margin-bottom:5px;">${table.tableGroupName}&nbsp;</div></td>
					 		<td><div style="margin-bottom:5px;">
					 	</c:if>
					 	<c:if test="${val!=1}">
					 		</div></td></tr>
					 		<tr><td><div class="cf-blue-bold" style="margin-bottom:5px;">${table.tableGroupName}&nbsp;</div></td>
					 		<td><div style="margin-bottom:5px;">
					 	</c:if>
					</c:if>
					<c:if test="${table.sn==selectedTable}">
						<button id="table_${table.sn}" type="button" title="${table.location}"
							class="form-input-therapist-timeline btn blue-room my-btn-blue-2 room-btn blue">
							<div><span>${table.name}</span>&nbsp;<span id="cf_table_statistic_number_${table.sn}" class="cf-table-statistic-number">(0)</span></div>
						</button>
					</c:if>
					<c:if test="${table.sn!=selectedTable}">
						<button id="table_${table.sn}" type="button" title="${table.location}"
							class="form-input-therapist-timeline btn blue-room my-btn-blue-2 room-btn">
							<div><span>${table.name}</span>&nbsp;<span id="cf_table_statistic_number_${table.sn}" class="cf-table-statistic-number">(0)</span></div>
						</button>
					</c:if>
					 <c:if test="${group!=tableGroupSn}">
						<c:if test="${fn:length(tableList) eq val}">
						   </div></td></tr>
						</c:if>
					</c:if>
					<c:set var="group" value="${tableGroupSn}" />
					<c:set var="val" value="${val+1}" />
				</c:forEach>
				</table>
				<input id="currentTableSn" type="hidden" value="${selectedTable}" />
			</div>
		</div>
	</form>
</div>