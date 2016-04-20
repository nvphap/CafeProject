<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="myModal" class="modal fade" style="display: none; background-color: rgba(0, 0, 0, 0.24) !important;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">
				<div class="row">
					<div class="col-lg-12 col-sm-12 col-xs-12">
						<div class="well with-header with-footer custom-well" style="margin-bottom: 0px;">
							<div class="modal-header bg-danger" id="ajaxChangeBackground">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">×</button>
								<h4 class="modal-title" id="confirmDeletePopupTitle" style="color: white;">
									<spring:message code="common.confirmTitle"/>
								</h4>
							</div>
							<form:form>
								<input id="ajaxUrl" type="hidden" value=""></input>
								<input id="ajaxRerender" type="hidden" value=""></input>
								<input id="confirmCallback" type="hidden" value=""></input>
								<div id="confirmContent" align="center"></div>
								
								<div class="form-group" style="text-align: center; margin: 10px 0 10px 0;" id="hiddenMessage">
									<spring:message code="common.confirmMessage"/>
								</div>
								
								<div id="divInputContent" align="center" style="display: none; padding: 0 10px 10px 10px;" >
									<input id="inputContent" type="text" class="form-control form-input" style="max-width:100%;"
										placeholder="<spring:message code="common.reasonRemark"/>" autofocus="autofocus"/>
								</div>
								
								<div align="center">
									<button type="button" id="ajaxConfirmBtn" data-dismiss="modal" class="btn btn-danger" ><i class="fa fa-times"></i><spring:message code="common.delete"/></button>
									<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="common.cancel"/></button>
								</div>
							</form:form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
	
<script type="text/javascript">
	$(document).ready(function() {
		$("#myModal").on('show.bs.modal', function(event) {
			var button = $(event.relatedTarget); // Button that triggered the modal
			var action = button.data('action');
			
			//Set back ground header title and button color
			var dataBackground = button.data('background');
			var dataButtoncolor = button.data('buttoncolor');
			if (dataBackground != null && dataButtoncolor != null && 
					dataBackground != '' && dataButtoncolor != '') {
				$('#ajaxChangeBackground').removeClass('bg-danger');
				$('#ajaxChangeBackground').removeClass('bg-warning');
				$('#ajaxConfirmBtn').removeClass('bg-primary');
				$('#ajaxChangeBackground').addClass(dataBackground);
				$('#ajaxConfirmBtn').removeClass('btn-danger');
				$('#ajaxConfirmBtn').removeClass('btn-warning');
				$('#ajaxConfirmBtn').removeClass('btn-primary');
				$('#ajaxConfirmBtn').addClass(dataButtoncolor);
			}
			
			$("#ajaxUrl").val(action);
			var message = button.data('message');
			if(message){
				$("#hiddenMessage").text(message);
			}
			var title = button.data('title');
			if(title){
				$("#confirmDeletePopupTitle").text(title);
			}
			var content = button.data('content');
			if(content){
				$("#confirmContent").text(content);
			}
			var input = button.data('input')
			if(input){
				$("#divInputContent").show();
				$("#divInputContent").val('');
			}else{
				$("#divInputContent").hide();
			}
			
			var idRerender = button.data('render');
			$("#ajaxRerender").val(idRerender);
			//$(this).find('.modal-title').text(titleData + ' Form');
			
			var titleBtn = button.data('titlebtn');
			if(titleBtn){
				$('#ajaxConfirmBtn').text(titleBtn);	
			}
			
			var callback = button.data('callback');
			if(callback){
				$('#confirmCallback').val(callback);
			}
		});
	});
	
	var j = jQuery.noConflict();
	j(document).ready(function() {
		//Delete action
		j("#ajaxConfirmBtn").click(function(event) {
            event.preventDefault();
            var ajaxUrl = $("#ajaxUrl").val();
            if($("#divInputContent").val()){
            	ajaxUrl+='input='+$("#divInputContent").val();
            }
            dialogWating.open();
   	    	j.ajax({
				url:ajaxUrl,
				type: 'POST',
				data: '',
				async: false,
				success: function(response){
					dialogWating.close();
   	    			if(response){
   	    				if(j("#ajaxRerender").val()){
   	    					var id=j("#ajaxRerender").val();
   	    					j(id).html(response);
   	    				}else{
   	    					j("#ajaxData").html(response);
   	    				}
   	    				var callback=j('#confirmCallback').val();
   	    				if(callback){
   	    					if (j.isFunction(callback)) {
   	    					  j('selector')[callback]();
   	    					}
   	    				}
   	    			}
				}
   	    	});
        });
	});
</script>

