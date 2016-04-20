<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Header -->
<%@include file="../templates/header.jsp"%>
<!-- /Header -->

<!-- Navbar -->
<%@include file="../templates/topNavbar.jsp"%>
<!-- /Navbar -->

<!-- Main Container -->
<div class="main-container container-fluid">
	<!-- Page Container -->
	<div class="page-container">

		<!-- Page Sidebar -->
		<%@include file="../templates/sideBarMenu.jsp"%>
		<!-- /Page Sidebar -->

		<!-- Page Content -->
		<div class="page-content">
			<!-- Page Breadcrumb -->
			<%@include file="../templates/breadCrumb.jsp"%>
			<!-- /Page Breadcrumb -->

			<!-- Page Body -->
			<div class="page-body">
				<div class="row">	
					<div class="col-lg-12 col-sm-12 col-xs-12">
						<%-- <h5 class="row-title before-blue">
							<i class="fa fa-tags blue"></i>
							<spring:message code="cafeOrder.title" />
						</h5> --%>
						<div class="widget">
							<div class="widget-body">
								<spring:message code="cafeOrder.foodName" var="cafeOrder_foodName" />
								<spring:message code="common.add" var="common_add" />
								<spring:message code="common.update" var="common_update" />
								<spring:message code="cafeOrder.memo" var="cafeOrder_memo" />
								<spring:message code="cafeOrder.noteWhenDiscountError" var="cafeOrder_noteWhenDiscountError" />
								
								<!-- Contain event in edit mode - END -->
								<div >
                                    <form class="form-inline form-therapist-timeline" role="form">
                                    <div>
                                        <div class="form-group form-input-therapist-timeline">
                                            <span class="input-icon icon-right inverted" data-title="">
                                            	<input id="cf_cafe_order_sn" type="hidden">
												<input id="cf_food_sn" type="hidden" class="form-control">
												<input id="cf_food_name" class="form-control-ext ipt-medium form-control" placeholder="${cafeOrder_foodName}">
												<input id="autoFoodSetup" type="hidden" value="cf_food_sn|cf_food_name|1|1|cf_food_price" class="form-control">
												<%@include file="../suggestion/oneFoodSuggestion.jsp"%>
												<i id="cf_cafe_clear_food" class="fa fa-times red clear-icon"></i>
                                            </span>
                                        </div>
                                        <div class="form-group form-input-therapist-timeline">
                                            <span class="input-icon icon-right inverted">
												<input id="cf_food_price" class="form-control form-control-ext ipt-tiny-short" value="0" readonly="readonly">
                                            </span>
                                        </div>
                                         <div class="form-group form-input-therapist-timeline">
	                                        <select name="cf_num_of_food" id="cf_num_of_food" class="form-control-ext ipt-short-50">
												<c:forEach var="i" begin="1" end="10">
													<c:if test="${i==1}">
														<option selected="selected" value="${i}">${i}</option>
													</c:if>
													<c:if test="${i!=1}">
														<option value="${i}">${i}</option>
													</c:if>
												</c:forEach>
											</select>
										</div>
										<div class="form-group form-input-therapist-timeline">
                                            <span class="input-icon icon-right inverted">
                                            	<%-- <c:if test="${3==loginUser.roleSn}">
													<input id="cf_total_money" class="form-control form-control-ext ipt-tiny-short" value="0" maxlength="7" readonly="readonly">
												</c:if>
												<c:if test="${3!=loginUser.roleSn}"> --%>
												<input id="cf_total_money" class="form-control form-control-ext ipt-tiny-short" value="0" maxlength="7">
												<%-- </c:if> --%>
                                            </span>
                                        </div>
                                        <div class="form-group form-input-therapist-timeline">
	                                        <select name="cf_order_percent_discount" id="cf_order_percent_discount" class="form-control-ext ipt-short-70">
												<c:forEach var="i" begin="0" end="6">
													<c:if test="${i==0}">
														<option selected="selected" value="${i*5}">${i*5}%</option>
													</c:if>
													<c:if test="${i!=0}">
														<option value="${i*5}">${i*5}%</option>
													</c:if>
												</c:forEach>
												<option value="${50}">50%</option>
											</select>
										</div>
                                       </div>
                                       <div>
	                                      	<div class="form-group form-input-therapist-timeline">
	                                           <span class="input-icon icon-right inverted">
												<input id="cf_memo" class="form-control form-control-ext ipt-sup-long" placeholder="${cafeOrder_memo}">
	                                           </span>
	                                        </div>
	                                       <div class="form-group form-input-therapist-timeline">
	                                       		<c:if test="${1==p.cafeOrder.create}">
	                                           		<input id="cf_add_new_cafe_order" class="btn btn-sky" type="button" value="${common_add}"/>
	                                           	</c:if>
	                                           	<c:if test="${1==p.cafeOrder.modify}">
	                                           		<input id="cf_update_cafe_order" style="display:none;" class="btn btn-sky" type="button" value="${common_update}"/>
	                                           	</c:if>
	                                        </div>
	                                        <div class="form-group form-input-therapist-timeline">
	                                           <span class="cf-recipes" id="cf_food_recipes_area" data-title=""><spring:message code="food.recipes"/></span>
	                                        </div>
	                                        
                                       </div>
                                    </form>
                                </div>
                                <!-- form contents - END -->
                                
                                <!-- barRoom.jsp -->
                                <div class="">
									<%@include file="barTable.jsp"%>
								</div>
								<!-- barRoom.jsp - END -->
								
								<!--START AJAX-->
								<div id="orderDetailList" >
									
								</div>
								<!--END AJAX-->
							</div>
						</div>
						<!--END MAIN CONTENT-->
					</div>
			</div>
			</div>
			<!-- /Page Body -->
		</div>
		<!-- /Page Content -->

	</div>
	<!-- /Page Container -->
	<!-- Main Container -->
</div>
<!--END MAIN CONTENT-->

<!--START POPUP Modal HTML-->

<!--END POPUP Modal HTML-->
<!-- -->
<script type="text/javascript">
	var j = jQuery.noConflict();
	
	function displayCafeOrderOfCurrentTable(){
		j('.cf_order_record').hide();
		var currentTableSn = j("#currentTableSn").val();
		j('.cf_order_record_group_'+currentTableSn).show();
		displayTotalMoneyForCurrentTable();
		updateStatusOfTopPrintAndPayBtn(currentTableSn);
	}
	
	function updateStatusOfTopPrintAndPayBtn(currentTableSn){
		var tags=j('.cf_order_record_group_'+currentTableSn)
		if(tags.length>0){
			j('#cf_cafe_bill_print').attr('disabled',false);
			j('#cf_cafe_bill_pay').attr('disabled',false);
		}else{
			j('#cf_cafe_bill_print').attr('disabled',true);
			j('#cf_cafe_bill_pay').attr('disabled',true);
		}
	}
	
	function displayTotalMoneyForCurrentTable(){
		j('.cf-table-total-money').hide();
		var currentTableSn = j('#currentTableSn').val();
		if(j('#cf_table_total_money_'+currentTableSn).length>0){
			j('#cf_table_total_money_'+currentTableSn).show();	
		}else{
			j('#cf_table_total_money_0').show();	
		}
	}
	
	function updateStatusOfAddUpdateBtn(){
		if(j('#cf_cafe_order_sn').val().length==0){
			j('#cf_add_new_cafe_order').show();
			j('#cf_update_cafe_order').hide();
			if(j('#cf_food_sn').val().length>0){
    			j('#cf_add_new_cafe_order').removeAttr('disabled');
    		}else{
    			j('#cf_add_new_cafe_order').attr('disabled',true);
    		}
		}else{//update
			j('#cf_add_new_cafe_order').hide();
			j('#cf_update_cafe_order').show();
			if(j('#cf_food_sn').val().length>0){
    			j('#cf_update_cafe_order').removeAttr('disabled');
    		}else{
    			j('#cf_update_cafe_order').attr('disabled',true);
    		}
		}
	}
	
	function updateCafeOrderStatisticForOneTable(cafeTableSn){
		j.ajax({
  	        type: "POST",
  	        url: "${pageContext.request.contextPath}/cafeOrder/view/ajaxGetCafeOrderStatisticOneTable",
  	         data:{ cafeTableSn:cafeTableSn
  	         },
  	      	async: true,
  	         success: function(response){
  	        	 if(response.length>0){
  	        		 var cafeTableSn = j('#currentTableSn').val();
   				 	j('#cf_table_statistic_number_'+cafeTableSn).text('('+response+')');
  	        	}
            }
        });
	}
	
	function isCurrentDay(displayDateStr){
		var now = new Date();
		var month = now.getMonth();
		var day = now.getDay();
		var year = now.getYear();
		var date = new Date(year,month,day);
		var displayDate=new Date(displayDateStr);
		if(day==displayDate.getDay()){
			if(month==displayDate.getMonth()){
				if(year==displayDate.getYear()){
					return true;
				}
			}
		}
		return false;
	}
	
	function resetCafeOrder(){
		j('#cf_cafe_order_sn').val('');
		j('#cf_food_sn').val('');
		j('#cf_food_name').val('');
		j('#cf_food_price').val(0);
		j('#cf_num_of_food').val(1);
		j('#cf_total_money').val(0);
		j('#cf_memo').val('');
		j('#cf_add_new_cafe_order').attr('disabled',true);
		j('#cf_order_percent_discount').val(0);
	}

	function openPrintPreview(cafeTableSn,cafeOrderSn) {
		var printUrl='';
		if(cafeTableSn.length>0){
			printUrl = '${pageContext.request.contextPath}/cafeOrder/view/printOrder?cafeTableSn=';
			printUrl += cafeTableSn;
		}else if(cafeOrderSn.length>0){
			printUrl = '${pageContext.request.contextPath}/cafeOrder/view/printOrder?cafeOrderSn=';
			printUrl += cafeOrderSn;
		}
		var printWindow=window.open(printUrl,'orderPrint',',type=fullWindow,fullscreen,scrollbars=yes');
		if(window.focus){
			printWindow.focus();
		} 
		printWindow.print();
		return false;
	}
	
	j(document).ready(function() {
    	searchCafeOrderOneDay();
    	updateStatusOfAddUpdateBtn();
    	updateCafeOrderStatisticForAllTable();
    	
    	function updateCurrentTableBtn(tableTagId){
    		var cafeTableSn = j(tableTagId).attr('id').replace('table_','');
			j('#currentTableSn').val(cafeTableSn);
			j('.room-btn').removeClass('blue');
			j('#table_'+cafeTableSn).addClass('blue');
    	}
    	
    	function searchCafeOrderOneDay(){
    		dialogWating.open();
			j.ajax({
	   	        type: "POST",
	   	        url: "${pageContext.request.contextPath}/cafeOrder/list/ajaxList",
	   	         data:{
	   	         },
	   	      	async: true,
	   	         success: function(response){
	    			j('#orderDetailList').html(response);
	    			displayCafeOrderOfCurrentTable();
	   	        	dialogWating.close();
   	            }
   	          });
		}
    	
    	function updateCafeOrderStatisticForAllTable(){
			j.ajax({
	   	        type: "POST",
	   	        url: "${pageContext.request.contextPath}/cafeOrder/view/ajaxGetCafeOrderStatisticAllTable",
	   	         data:{
	   	         },
	   	      	async: true,
	   	         success: function(response){
	   	        	 if(response.length>0){
	   	        		 var jsonStatisticList= JSON.parse(response);
	   	        		j.each(jsonStatisticList, function (index, item) {
		   				    j('#cf_table_statistic_number_'+item.cafeTableSn).text('('+item.numOfOrder+')');
		   				});
	   	        	 }
   	            }
   	        });
		}
    	
    	j('#cf_order_percent_discount').change(function(){
    		countTotalMoneyWithDiscount();
    	});
    	
    	function countTotalMoneyWithDiscount(){
    		var percentPrice = 100 - j('#cf_order_percent_discount').val();
    		var price = parseInt(j("#cf_food_price").val());
    		var numOfFood= j("#cf_num_of_food").val();
    		var finalMoney = (price*numOfFood*percentPrice)/100;
    		j('#cf_total_money').val(finalMoney);
    	}
  		
    	j('.room-btn').click(function(){	
    		updateCurrentTableBtn(this);
			displayCafeOrderOfCurrentTable();
		});
    	
    	j("#cf_total_money").keydown(function(e){
			isNumber(e);
		});
    	
    	j('#cf_total_money').change(function(e){
    		if(j('#cf_total_money').val().length==0){
    			j('#cf_total_money').val(0);
    		}
    	});
    	
    	j('#cf_food_sn').change(function(e){
    		updateStatusOfAddUpdateBtn();
    		updateTotalMoney();
    		ajaxGetFoodRecipes();
    	});
    	
    	j('#cf_num_of_food').change(function(){
    		updateTotalMoney();
    	});  
    	
    	function ajaxGetFoodRecipes(){
    		if(0==j('#cf_food_sn').val().length){
    			j('#cf_food_recipes_area').attr('title','');
    		}else{
    			j.ajax({
    	   	        type: "POST",
    	   	        url: "${pageContext.request.contextPath}/cafeOrder/view/ajaxGetFoodRecipes",
    	   	         data:{
    	   	        	foodSn:j('#cf_food_sn').val()
    	   	         },
    	   	      	async: true,
    	   	         success: function(response){
    	    			j('#cf_food_recipes_area').attr('data-title',response.replace(new RegExp('\r?\n','g'),'<br/>'));
       	            }
       	        });
    		}
		}
    	
    	j('#cf_food_recipes_area').mouseenter(function() {
    		displayTooltip(this);
	    }).mouseleave(function(){
	    	closeAllTooltip(this);
	    });
    	
    	function closeAllTooltip(elementId){
	    	j(elementId).tooltip("close");
		}
    	
    	function displayTooltip(tagId){
			if(j(tagId).length>0){
				j(tagId).tooltip({
					items: "[data-title]",
					tooltipClass: "tooltip-display",
					position: { my: "left bottom-30", at: "right center"},
					content: function () {
						 var title = j(this).data("title");
						 return title;
			    	}
				});
				j(tagId).tooltip("option","content",j(tagId).attr('data-title'));
				 j(tagId).tooltip("open");
			}
		}
    	
    	function updateTotalMoney(){
    		if(j('#cf_food_sn').val().length>0){
    			countTotalMoneyWithDiscount();
    		}else{
    			j('#cf_food_price').val(0);
    			j('#cf_total_money').val(0);
    		}
    	}
    	
    	j('#cf_cafe_clear_food').click(function(){
    		j('#cf_food_sn').val('');
    		j('#cf_food_name').val('');
    		j('#cf_food_sn').change();
    	});
    	
    	j('#cf_add_new_cafe_order').click(function(){
    		if(j('#currentTableSn').val()>0 && j('#cf_food_sn').val().length>0){
    			var numOfFood=parseInt(j('#cf_num_of_food').val());
				var price = parseInt(j('#cf_food_price').val());
				var totalMoney = parseInt(j('#cf_total_money').val());
				var memo = j('#cf_memo').val();
				memo = j.trim(memo);
				j('#cf_memo').val(memo);
    			if(totalMoney!=(numOfFood*price) && memo.length==0){
					displayError('${cafeOrder_noteWhenDiscountError}');
				}else{
					addNewCafeOrder();	
				}
    		}
    	});
    	
    	function addNewCafeOrder(){
			j.ajax({
	   	        type: "POST",
	   	        url: "${pageContext.request.contextPath}/cafeOrder/add/ajaxAddNewCafeOrder",
	   	         data:{
	   	        	cafeTableSn:j('#currentTableSn').val(),
	   	        	foodSn:j('#cf_food_sn').val(),
	   	        	price:j('#cf_food_price').val(),	
	   				numOfFood:j('#cf_num_of_food').val(),
	   				totalMoney:j('#cf_total_money').val(),
	   				memo:j('#cf_memo').val(),
	   				discount:j('#cf_order_percent_discount').val()
	   	         },
	   	      	async: true,
	   	         success: function(response){
	    			j('#orderDetailList').html(response);
	    			displayCafeOrderOfCurrentTable();
	    			resetCafeOrder();
	    			updateStatusOfAddUpdateBtn();
	    			updateCafeOrderStatisticForOneTable(j('#currentTableSn').val());
   	            }
   	        });
    	}
    	
    	function updateCafeOrder(){
			j.ajax({
	   	        type: "POST",
	   	        url: "${pageContext.request.contextPath}/cafeOrder/update/ajaxUpdateCafeOrder",
	   	         data:{
	   	        	 cafeOrderSn:j("#cf_cafe_order_sn").val(),
	   	        	cafeTableSn:j('#currentTableSn').val(),
	   	        	foodSn:j('#cf_food_sn').val(),
	   	        	price:j('#cf_food_price').val(),	
	   				numOfFood:j('#cf_num_of_food').val(),
	   				totalMoney:j('#cf_total_money').val(),
	   				memo:j('#cf_memo').val(),
	   				discount:j('#cf_order_percent_discount').val()
	   	         },
	   	      	async: true,
	   	         success: function(response){
	    			j('#orderDetailList').html(response);
	    			displayCafeOrderOfCurrentTable();
	    			resetCafeOrder();
	    			updateStatusOfAddUpdateBtn();
   	            }
   	        });
    	}
    	
    	j('#cf_update_cafe_order').click(function(){
    		if(j('#cf_cafe_order_sn').val().length>0){
    			if(j('#currentTableSn').val()>0 && j('#cf_food_sn').val().length>0){
    				var numOfFood=parseInt(j('#cf_num_of_food').val());
    				var price = parseInt(j('#cf_food_price').val());
    				var totalMoney = parseInt(j('#cf_total_money').val());
    				var memo = j('#cf_memo').val();
    				memo = j.trim(memo);
    				j('#cf_memo').val(memo);
    				if(totalMoney!=(numOfFood*price) && memo.length==0){
    					displayError('${cafeOrder_noteWhenDiscountError}');
    				}else{
    					updateCafeOrder();	
    				}
    			}
    		}
    	});
	});
</script>
<!-- -->
<style type="text/css">
    .bs-example{
    	margin: 20px;
    }
</style>
<%@include file="../templates/footer.jsp"%>	