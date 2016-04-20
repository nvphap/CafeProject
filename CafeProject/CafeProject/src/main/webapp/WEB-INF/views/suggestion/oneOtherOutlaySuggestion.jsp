<input id="autoOtherOutlayPage" type="hidden" value="1">
<input id="autoOtherOutlayStop" type="hidden" value="0">
<input id="autoOtherOutlayKeyword" type="hidden" value="">
<script type="text/javascript">
	var j = jQuery.noConflict();
	j(document).ready(function() {
		var autoOtherOutlayTargetIdControl;
		var autoOtherOutlayTargetNameControl;
		var otherOutlayContainer;
		var backupOtherOutlayResponse;
		var oldOtherOutlayItems;
		var otherOutlayClear = 1;
		var typeOtherOutlayName=0;
		var loadMoreOtherOutlayFlag=0;
		initOtherOutlaySetup();
		//0:id of tartget id control , 
		//1:Id of target name control
		//2:index of control
		
		function initOtherOutlaySetup(){
			var autoOtherOutlaySetup = j('#autoOtherOutlaySetup');
			if(autoOtherOutlaySetup){
				var setting=autoOtherOutlaySetup.val();
				var settingList = setting.split('|');
				if(settingList.length>0){
					autoOtherOutlayTargetIdControl= '#'+settingList[0];
				}
				if(settingList.length>1){//target name control
					autoOtherOutlayTargetNameControl='#'+settingList[1];
				}
				if(settingList.length>2){//target name control
					otherOutlayContainer='#ui-id-'+settingList[2];
				}else{
					otherOutlayContainer='#ui-id-1'
				}
				if(settingList.length>3){//when user do not choose clear term or not (1:clear, 0: not clear)
					if(settingList[3].length>0){
						otherOutlayClear = parseInt(settingList[3]);
					}else{
						otherOutlayClear=1;	
					}
				}
				j('#autoOtherOutlayPage').val(1);
	        	j('#autoOtherOutlayStop').val(0);
	        	if(j(otherOutlayContainer).length>0){
	        		j(otherOutlayContainer).scrollTop(0);	
	        	}
			}
		}
		
		function loadMoreOtherOutlayData(){
			j.ajax({
	          url: "${pageContext.request.contextPath}/suggestion/view/ajaxGetAutoOtherOutlayList",
	          type: 'POST',
	          async :true,
	          /* dataType: "json", */
	          data: {
	            keySearch:j('#autoOtherOutlayKeyword').val(),
	            page:j('#autoOtherOutlayPage').val()
	          },
	          success: function(data){
	        	  var newPage= data[0];
	        	  var oldPage=j('#autoOtherOutlayPage').val();
	        	  if(newPage==oldPage){//have no more item to load
	        		  j('#autoOtherOutlayStop').val(1);  
	        	  }else{
	        		  j('#autoOtherOutlayStop').val(0);
	        		  j('#autoOtherOutlayPage').val(data[0]); 
	        		 //append old data with new data
	        		 loadMoreOtherOutlayFlag=1;
	        		 backupOtherOutlayResponse(JSON.parse(data[1]));
	        	  }
	          }
	        });
		}
		
	    j(autoOtherOutlayTargetNameControl).autocomplete({
	      minLength: 1,
	      /* source: projects, */
	      source: function(request,response){//user for first request
	    	  if(0==typeOtherOutlayName){//if the same value with textbox --> don't suggestion
	    		  j(autoOtherOutlayTargetNameControl).removeClass("ui-autocomplete-loading");
	    		 return;
	    	  }
	    	  oldOtherOutlayItems='';
	    	  backupOtherOutlayResponse = response;
	    	  j('#autoOtherOutlayPage').val(1);
	    	  j('#autoOtherOutlayStop').val(1);
	    	 j('#autoOtherOutlayKeyword').val(request.term);
	    	 j(autoOtherOutlayTargetIdControl).val('');
	        j.ajax({
	          url: "${pageContext.request.contextPath}/suggestion/view/ajaxGetAutoOtherOutlayList",
	          type: 'POST',
	          async :true,
	          /* dataType: "json", */
	          data: {
	            keySearch: request.term,
	            page:j('#autoOtherOutlayPage').val()
	          },
	          success: function(data){
	        	j('#autoOtherOutlayPage').val(data[0]);
	        	j('#autoOtherOutlayStop').val(0);
	        	j(autoOtherOutlayTargetNameControl).removeClass("ui-autocomplete-loading");
	        	loadMoreOtherOutlayFlag=0;
	        	response(JSON.parse(data[1]));
	          },
	          error:function(){
	        	  j('#autoOtherOutlayStop').val(0);
	          }
	        });
	      },
	      html: true, // optional (jquery.ui.autocomplete.html.js required)
	      search: function (event,ui) {
	    	  
	      },
	      focus: function(event,ui){
	    	  /* if(autoOtherOutlayTargetNameControl){
	    		  j(autoOtherOutlayTargetNameControl).val(ui.item.display);	  
	    	  } */
	    	  if(autoOtherOutlayTargetIdControl){
	    		  j(autoOtherOutlayTargetIdControl).val('');	  
	    	  }
	        return false;
	      },
	      select:function(event,ui){
	    	  typeOtherOutlayName=0;
	    	  if(j(autoOtherOutlayTargetNameControl).length>0){
	    		  j(autoOtherOutlayTargetNameControl).val(ui.item.display);	 
	    	  }
	    	  if(j(autoOtherOutlayTargetIdControl).length>0){
	    		  j(autoOtherOutlayTargetIdControl).val(ui.item.sn);
	    		  j(otherOutlayContainer).hide();
	    		  j(autoOtherOutlayTargetIdControl).change();
	    	  }
	    	  
	        return false;
	      },
	      open:function(event, ui){
	    	  if(autoOtherOutlayTargetIdControl){
	    		  /* j(autoOtherOutlayTargetIdControl).val(ui.item.sn); */
	    		  j(autoOtherOutlayTargetIdControl).val('');
	    	  }
	    	  if(j(otherOutlayContainer).length>0 && 0==loadMoreOtherOutlayFlag){
	    		  j(otherOutlayContainer).scrollTop(0);  
	    	  }
	    	  j('#autoOtherOutlayStop').val(0);
	       },
        close:function(event, ui){
        	j('#autoOtherOutlayPage').val(1);
        	j('#autoOtherOutlayStop').val(0);
        }
	    }).autocomplete("instance")._renderItem = function(ul,item){
	      return j( "<li class='autocomplete-content'>" ).data("item.autocomplete-item", item ).append(createOtherOutlayContentItem(item)).appendTo(ul);
	    };
	    
	    j(autoOtherOutlayTargetNameControl).keyup(function(event){
	    	typeOtherOutlayName=1;
	    });
	    
	    j(autoOtherOutlayTargetNameControl).autocomplete("instance")._renderMenu =  function(ul,items){
    		var ulId = otherOutlayContainer.replace('#','');
    		ul.attr('id',ulId);
    	    var that = this;
    	    if(oldOtherOutlayItems){
    	    	j.each(oldOtherOutlayItems, function(index, item) {
        	        that._renderItemData( ul, item );
        	    });
    	    } 
    	    j.each( items, function(index, item) {
    	        that._renderItemData( ul, item );
    	    });
    	    if(oldOtherOutlayItems.length>0){
    	    	j.merge(oldOtherOutlayItems,items);
	   	    }else{
    	    	oldOtherOutlayItems = items;
    	    }
    	}
	    
	    function createOtherOutlayContentItem(item){
	    	var keyword = j('#autoOtherOutlayKeyword').val();
	    	var title = item.title;
	    	//var description = item.description;
	    	//var description2 = item.description2;
	    	var content = "<a class='autoOtherOutlayItem'><span class='autocomplete-title'>" + title+"</span>";
	    	//content+="<br><span class='autocomplete-desc'>" + description + "</span>";
	    	//content+="<br><span class='autocomplete-desc'>" + description2 + "</span>";
	    	content+="</a>";
	    	content+="<input type='hidden' id='autoOtherOutlayItem_"+item.sn+"' value='"+item.display+"'/>";
	        return content;
	    }
	    
	    j(autoOtherOutlayTargetNameControl).blur(function() {
	    	var otherOutlaySn;
	    	if(j(autoOtherOutlayTargetIdControl).length>0){
	    		otherOutlaySn = j(autoOtherOutlayTargetIdControl).val();
	    	}
        	if(0==otherOutlaySn.length){
        		if(1==otherOutlayClear){
	        		if(j(autoOtherOutlayTargetIdControl).length>0){
	        			j(autoOtherOutlayTargetIdControl).val('');
	        			j(autoOtherOutlayTargetIdControl).change();
	        		}
	        		if(j(autoOtherOutlayTargetNameControl).length>0){
	        			j(autoOtherOutlayTargetNameControl).val('');
	        		}
        		}
        	}
	    });
	    
	     j(otherOutlayContainer).scroll(function() {//ul tag
	    	 var container=j(otherOutlayContainer);
            if(isOtherOutlayScrollbarBottom(container)){
            	if(0==j('#autoOtherOutlayStop').val()){
            		j('#autoOtherOutlayStop').val(1);
            		loadMoreOtherOutlayData();
            	}
            }
	    });
	     
	     
	    
	    function isOtherOutlayScrollbarBottom(container) {//object of ul
            var height = container.outerHeight();
            var scrollHeight = container[0].scrollHeight;
            var scrollTop = container.scrollTop();
            if (scrollTop >= scrollHeight - height-100) {
                return true;
            }
            return false;
        };
  	});
 </script>

