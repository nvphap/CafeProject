<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Paging -->
<input id="actionUrl" name="actionUrl" value="${pageContext.request.contextPath}${pageUrl}" type="hidden">
<input id="pagingUrlExt" name ="hStaffSnList" type="hidden" value="${pagingUrlExt}"></input>
<input id="dynamicUrl" name ="dynamicUrl" type="hidden" value="${dynamicUrl}"></input>
<input id="currentPage" name="currentPage" value="${currentPage}" type="hidden"> 
<input id="totalPage" name="totalPage" value="${totalPage}" type="hidden"> 
<input id="totalRecord" name="totalRecord" value="${totalRecord}" type="hidden">
<input id="hWaittingData" name="hWaittingData" value="" type="hidden">
<input id="hAjaxListData" name="hAjaxListData" value="" type="hidden"> 
	
<div class="col-sm-3">
    <div class="dataTables_info" id="editabledatatable_info" role="status" aria-live="polite">
        <!-- Showing 1 to 5 of 6 entries -->
    </div>
</div>
<div class="col-sm-9" style="text-align:right">
    <div class="dataTables_paginate paging_bootstrap" id="editabledatatable_paginate">
        <ul class="pagination">
            <c:if test="${!empty totalPage && !empty currentPage && currentPage > 1}">
                <li class="prev">
                    <a id="firstPage" href="#"><spring:message code="common.first" /></a>
                </li>
                <li class="prev">
                    <a id="previousPage" href="#">«</a>
                </li>
            </c:if>
            <c:if test="${!empty totalPage && !empty currentPage && currentPage <= 1}">
                <li class="prev disabled">
                    <a href="#"><spring:message code="common.first" /></a>
                </li>
                <li class="prev disabled">
                    <a href="#">«</a>
                </li>
            </c:if>
            <c:if test="${!empty totalPage && !empty currentPage}">
                <li class="disabled" style="background-color: #8cc474">
                    <a href="#">${currentPage}/${totalPage} <spring:message code="common.page"/></a>
                </li>
            </c:if>
            <c:if test="${!empty totalRecord}">
                <li class="active disabled">
                    <a href="">
                        <spring:message code="common.total"/>:${totalRecord}
                    </a>
                </li>
            </c:if>
            <c:if test="${!empty totalPage && !empty currentPage && currentPage < totalPage}">
                <li class="next">
                    <a id="nextPage" href="#">»</a>
                </li>
                <li class="next">
                    <a id="lastPage" href="#"><spring:message code="common.last" /></a>
                </li>
            </c:if>
            <c:if test="${!empty totalPage && !empty currentPage && currentPage >= totalPage}">
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
	j(document).ready(function() {
		initPaging();
		function initPaging(){
			//setup position of load waitting icon and data list
			var setup=$('#hPagingSetup').val();
			if(setup){
				var values = setup.split('|');
				var waittingId = null;
				var listDataId = null;
				if(values.length>1){
					waittingId = values[0];
					listDataId = values[1];
				}
				if(waittingId){
					j("#hWaittingData").val(waittingId);	
				}
				if(listDataId){
					j("#hAjaxListData").val(listDataId);	
				}
			}
		}
		//Paging action
		j('#firstPage').click(function() {
			j('#currentPage').val("1");
			doAjax();
		});
		j('#previousPage').click(function() {
			j('#currentPage').val("${currentPage-1}");
			doAjax();
		});
		j('#nextPage').click(function() {
			j('#currentPage').val("${currentPage+1}");
			doAjax();
		});
		j('#lastPage').click(function() {
			j('#currentPage').val("${totalPage}");
			doAjax();
		});
		
		function doAjax() {
			var url = createUrl();
			var waittingId=j("#hWaittingData").val();
			var params = '';
			params += 'ajax=1';
			params += '&page=' + j('#currentPage').val();
			var pagingUrlExt = j("#pagingUrlExt").val();
			if(pagingUrlExt) { //if want to have more url param 
				if(pagingUrlExt.indexOf('&')!=0) {
					params += '&';
				}
				params += pagingUrlExt;
			}
			var dynamicUrl=j("#dynamicUrl").val();
			if(dynamicUrl){
				if(dynamicUrl.indexOf('&')!=0){
					params += '&';
				}
				params += dynamicUrl;
			}
			
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
						setTimeout(function(){
							var waittingId=j("#hWaittingData").val();
							if(waittingId){
								j(waittingId).html("");
							}else{
								dialogWating.close();
							}
							var listDataId=j("#hAjaxListData").val();
							if(listDataId){
								j(listDataId).html(response);
							}else{
								j('#ajaxListData').html(response);
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
			var url = j('#actionUrl').val();
			return url;
		}
	});
</script>

