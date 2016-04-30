
<spring:message code="cafeOrder.totalMoney" var="cafeOrder_totalMoney"/>
<spring:message code="cafeOrder.othersTotalMoney" var="cafeOrder_othersTotalMoney"/>
<spring:message code="cafeOrder.totalCafeOrder" var="cafeOrder_totalCafeOrder"/>
<spring:message code="statistic.totalProfit" var="statistic_totalProfit"/>
	<div style="margin-bottom:10px;">
		<div style="margin-bottom:5px;">
			<span class="cf-total-money-title">${cafeOrder_totalCafeOrder}:</span>&nbsp;<span>${statistic.numOfFoodStr}</span>
			&nbsp;&nbsp;
			<span class="cf-total-money-title">${cafeOrder_totalMoney}:</span>&nbsp;<span>${statistic.totalMoney}</span>
			&nbsp;&nbsp;
			<c:if test="${3!=loginUser.roleSn}">
				<span class="cf-total-money-title">${statistic_totalProfit}:</span>&nbsp;<span>${statistic.totalProfit}</span>
				&nbsp;&nbsp;
			</c:if>
			<span class="cf-total-money-title">${cafeOrder_othersTotalMoney}:</span>&nbsp;<span>${statistic.totalMoneyOtherOutlayStr}</span>
			&nbsp;<a id="cf_statistic_other_outlay_link" href="#"><spring:message code="common.detail"/></a>
		</div>
		<a id="cf_statistic_other_outlay_detail" style="display:none;" href="#" target="_blank"></a>
	</div>