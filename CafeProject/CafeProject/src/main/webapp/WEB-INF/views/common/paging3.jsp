<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<input id="actionUrl3" name="actionUrl" value="${pageContext.request.contextPath}${pageUrl3}" type="hidden">
<input id="pagingUrlExt3" name ="hStaffSnList" type="hidden" value="${pagingUrlExt3}"></input>
<input id="dynamicUrl3" name ="dynamicUrl3" type="hidden" value="${dynamicUrl3}"></input>
<input id="currentPage3" name="currentPage" value="${currentPage3}" type="hidden"> 
<input id="totalPage3" name="totalPage" value="${totalPage3}" type="hidden"> 
<input id="totalRecord3" name="totalRecord" value="${totalRecord3}" type="hidden">
<input id="hWaittingData3" name="hWaittingData" value="" type="hidden">
<input id="hAjaxListData3" name="hAjaxListData" value="" type="hidden">
	
<style>
.myPaging {
	margin: 10px 0 10px -280px;
    display: inline-flex;
}
</style>

<!-- Paging -->
<div class="col-sm-6">
    <div class="dataTables_info" id="editabledatatable_info" role="status" aria-live="polite">
        <!-- Showing 1 to 5 of 6 entries -->
    </div>
</div>

<div class="col-sm-6" style="">
    <div class="dataTables_paginate paging_bootstrap" id="editabledatatable_paginate">
        <ul class="pagination myPaging">
        	 <c:if test="${!empty totalPage3 && !empty currentPage3 && currentPage3 > 1}">
                <li class="prev">
                    <a id="firstPage3" href="#"><spring:message code="common.first" /></a>
                </li>
                <li class="prev">
                    <a id="previousPage3" href="#">«</a>
                </li>
            </c:if>
            <c:if test="${!empty totalPage3 && !empty currentPage3 && currentPage3 <= 1}">
                <li class="prev disabled">
                    <a href="#"><spring:message code="common.first" /></a>
                </li>
                <li class="prev disabled">
                    <a href="#">«</a>
                </li>
            </c:if>
    		<c:if test="${!empty totalPage3 && !empty currentPage3}">
                <li class="disabled" style="background-color: #8cc474">
                    <a href="#">${currentPage3}/${totalPage3} <spring:message code="common.page"/></a>
                </li>
            </c:if>
            <c:if test="${!empty totalRecord3}">
                <li class="active disabled">
                    <a href="">
                        <spring:message code="common.total"/>:${totalRecord3}
                    </a>
                </li>
            </c:if>
			<c:if test="${!empty totalPage3 && !empty currentPage3 && currentPage3 < totalPage3}">
                <li class="next">
                    <a id="nextPage3" href="#">»</a>
                </li>
                <li class="next">
                    <a id="lastPage3" href="#"><spring:message code="common.last" /></a>
                </li>
            </c:if>
            <c:if test="${!empty totalPage3 && !empty currentPage3 && currentPage3 >= totalPage3}">
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
	var j = jQuery.noConflict();

	j(document).ready(function() {
		initPaging();
		function initPaging(){
			//setup position of load waitting icon and data list
			var setup=$('#hPagingSetup3').val();
			if(setup){
				var values = setup.split('|');
				var waittingId = null;
				var listDataId = null;
				if(values.length>1){
					waittingId = values[0];
					listDataId = values[1];
				}
				if(waittingId){
					j("#hWaittingData3").val(waittingId);	
				}
				if(listDataId){
					j("#hAjaxListData3").val(listDataId);	
				}
			}
		}
		//Paging action
		j('#firstPage3').click(function() {
			j('#currentPage3').val("1");
			doAjax();
		});
		j('#previousPage3').click(function() {
			j('#currentPage3').val("${currentPage3-1}");
			doAjax();
		});
		j('#nextPage3').click(function() {
			j('#currentPage3').val("${currentPage3+1}");
			doAjax();
		});
		j('#lastPage3').click(function() {
			j('#currentPage3').val("${totalPage3}");
			doAjax();
		});

		function doAjax() {
			var url = createUrl();
			var params = '';
			params += '?ajax=1';
			params += '&page=' + j('#currentPage3').val();
			var pagingUrlExt=j("#pagingUrlExt3").val();
			if(pagingUrlExt){ //if want to have more url param
				if(pagingUrlExt.indexOf('&')!=0){
					params += '&';
				}
				params += pagingUrlExt;
			}
			var dynamicUrl=j("#dynamicUrl3").val();
			if(dynamicUrl){
				if(dynamicUrl.indexOf('&')!=0){
					params += '&';
				}
				params += dynamicUrl;
			}
			var html = '<img style="width: 50px;" src="${pageContext.request.contextPath}/resources/image/ajaxLoaderBlue.gif" />';
			var waittingId=j("#hWaittingData3").val();
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
							var waittingId=j("#hWaittingData3").val();
							if(waittingId){
								j(waittingId).html("");
							}else{
								dialogWating.close();
							}
							
							var listDataId=j("#hAjaxListData3").val();
							if(listDataId){
								j(listDataId).html(response);
							}else{
								j('#ajaxListData3').html(response);
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
			var url = j('#actionUrl3').val();
			/* url += '?ajax=1';
			url += '&page=' + j('#currentPage3').val();
			var pagingUrlExt=j("#pagingUrlExt3").val()
			var pagingUrlExt=j("#pagingUrlExt3").val()
			if(pagingUrlExt){ //if want to have more url param
				if(pagingUrlExt.indexOf('&')!=0){
					url+='&';
				}
				url+=pagingUrlExt;
			} */
			return url;
		}
	});
</script>

