<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<input id="actionUrl2" name="actionUrl" value="${pageContext.request.contextPath}${pageUrl2}" type="hidden">
<input id="pagingUrlExt2" name ="pagingUrlExt2" type="hidden" value="${pagingUrlExt2}"></input>
<input id="dynamicUrl2" name ="dynamicUrl2" type="hidden" value="${dynamicUrl2}"></input>
<input id="currentPage2" name="currentPage" value="${currentPage2}" type="hidden"> 
<input id="totalPage2" name="totalPage" value="${totalPage2}" type="hidden"> 
<input id="totalRecord2" name="totalRecord" value="${totalRecord2}" type="hidden">
<input id="hWaittingData2" name="hWaittingData" value="" type="hidden">
<input id="hAjaxListData2" name="hAjaxListData" value="" type="hidden">
	
<div class="col-sm-3">
    <div class="dataTables_info" id="editabledatatable_info" role="status" aria-live="polite">
        <!-- Showing 1 to 5 of 6 entries -->
    </div>
</div>
<div class="col-sm-9" style="text-align:right">
    <div class="dataTables_paginate paging_bootstrap" id="editabledatatable_paginate">
        <ul class="pagination myPaging">
        	 <c:if test="${!empty totalPage2 && !empty currentPage2 && currentPage2 > 1}">
                <li class="prev">
                    <a id="firstPage2" href="#"><spring:message code="common.first" /></a>
                </li>
                <li class="prev">
                    <a id="previousPage2" href="#">«</a>
                </li>
            </c:if>
            <c:if test="${!empty totalPage2 && !empty currentPage2 && currentPage2 <= 1}">
                <li class="prev disabled">
                    <a href="#"><spring:message code="common.first" /></a>
                </li>
                <li class="prev disabled">
                    <a href="#">«</a>
                </li>
            </c:if>
    		<c:if test="${!empty totalPage2 && !empty currentPage2}">
                <li class="disabled" style="background-color: #8cc474">
                    <a href="#">${currentPage2}/${totalPage2} <spring:message code="common.page"/></a>
                </li>
            </c:if>
            <c:if test="${!empty totalRecord2}">
                <li class="active disabled">
                    <a href="">
                        <spring:message code="common.total"/>:${totalRecord2}
                    </a>
                </li>
            </c:if>
			<c:if test="${!empty totalPage2 && !empty currentPage2 && currentPage2 < totalPage2}">
                <li class="next">
                    <a id="nextPage2" href="#">»</a>
                </li>
                <li class="next">
                    <a id="lastPage2" href="#"><spring:message code="common.last" /></a>
                </li>
            </c:if>
            <c:if test="${!empty totalPage2 && !empty currentPage2 && currentPage2 >= totalPage2}">
                <li class="next disabled">
                    <a href="#">»</a>
                </li>
                <li class="next disabled">
                    <a href="#"><spring:message code="common.last" /></a>
                </li>
            </c:if>
        </ul>
    </div>
</div>
<!-- End Paging -->

<script type="text/javascript">
	/* var j = jQuery.noConflict(); */
	j(document).ready(function() {
		initPaging();
		function initPaging(){
			//setup position of load waitting icon and data list
			var setup=j('#hPagingSetup2').val();
			if(setup){
				var values = setup.split('|');
				var waittingId = null;
				var listDataId = null;
				if(values.length>1){
					waittingId = values[0];
					listDataId = values[1];
				}
				if(waittingId){
					j("#hWaittingData2").val(waittingId);	
				}
				if(listDataId){
					j("#hAjaxListData2").val(listDataId);	
				}
			}
		}
		//Paging action
		j('#firstPage2').click(function() {
			j('#currentPage2').val("1");
			doAjax();
		});
		j('#previousPage2').click(function() {
			j('#currentPage2').val("${currentPage2-1}");
			doAjax();
		});
		j('#nextPage2').click(function() {
			j('#currentPage2').val("${currentPage2+1}");
			doAjax();
		});
		j('#lastPage2').click(function() {
			j('#currentPage2').val("${totalPage2}");
			doAjax();
		});

		function doAjax() {
			var url = createUrl();
			var params = '';
			params += '?ajax=1';
			params += '&page=' + j('#currentPage2').val();
			var pagingUrlExt=j("#pagingUrlExt2").val();
			if(pagingUrlExt){ //if want to have more url param
				if(pagingUrlExt.indexOf('&')!=0){
					params += '&';
				}
				params += pagingUrlExt;
			}
			var dynamicUrl=j("#dynamicUrl2").val();
			if(dynamicUrl){
				if(dynamicUrl.indexOf('&')!=0){
					params += '&';
				}
				params += dynamicUrl;
			}
			var html = '<img style="width: 50px;" src="${pageContext.request.contextPath}/resources/image/ajaxLoaderBlue.gif" />';
			var waittingId=j("#hWaittingData2").val();
			if(waittingId){
				j(waittingId).html(htmlWatingTiny);
			}else{
				dialogWating.open();
			}
			j.ajaxSetup({
				async : false
			});
			j.ajax({
				url : url,
				type : 'POST',
				data : params,
				async : false,
				success : function(response) {
					if (!response) {
						alert('<spring:message code="common.noData" />');
					} else {
						console.log("Url: " + url);
						console.log("Params: " + params);
						setTimeout(function(){
							var waittingId=j("#hWaittingData2").val();
							if(waittingId){
								j(waittingId).html("");
							}else{
								dialogWating.close();
							}
							
							var listDataId=j("#hAjaxListData2").val();
							if(listDataId){
								j(listDataId).html(response);
							}else{
								j('#ajaxListData2').html(response);
							}
						}, 2000);
					}
				}
			});
			j.ajaxSetup({
				async : false
			});
		}

		function createUrl() {
			var url = j('#actionUrl2').val();
			return url;
		}
	});
</script>

