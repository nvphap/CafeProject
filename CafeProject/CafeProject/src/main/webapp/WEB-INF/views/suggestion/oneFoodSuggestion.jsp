<input id="autoFoodPage" type="hidden" value="1">
<input id="autoFoodStop" type="hidden" value="0">
<input id="autoFoodKeyword" type="hidden" value="">
<script type="text/javascript">
	var j = jQuery.noConflict();
	j(document).ready(function() {
		var autoFoodTargetIdControl;
		var autoFoodTargetNameControl;
		var autoFoodTargetPriceControl;
		var foodContainer;
		var backupFoodResponse;
		var oldFoodItems;
		var foodClear = 1;
		var typeFoodName=0;
		var loadMoreFoodFlag=0;
		initFoodSetup();
		//0:id of tartget id control , 
		//1:Id of target name control
		//2:index of control
		
		function initFoodSetup(){
			var autoFoodSetup = j('#autoFoodSetup');
			if(autoFoodSetup){
				var setting=autoFoodSetup.val();
				var settingList = setting.split('|');
				if(settingList.length>0){
					autoFoodTargetIdControl= '#'+settingList[0];
				}
				if(settingList.length>1){//target name control
					autoFoodTargetNameControl='#'+settingList[1];
				}
				if(settingList.length>2){//target name control
					foodContainer='#ui-id-'+settingList[2];
				}else{
					foodContainer='#ui-id-1'
				}
				if(settingList.length>3){//when user do not choose clear term or not (1:clear, 0: not clear)
					if(settingList[3].length>0){
						foodClear = parseInt(settingList[3]);
					}else{
						foodClear=1;	
					}
				}
				if(settingList.length>4){//when user do not choose clear term or not (1:clear, 0: not clear)
					if(settingList[4].length>0){
						autoFoodTargetPriceControl = "#"+settingList[4];
					}
				}
				j('#autoFoodPage').val(1);
	        	j('#autoFoodStop').val(0);
	        	if(j(foodContainer).length>0){
	        		j(foodContainer).scrollTop(0);	
	        	}
			}
		}
		
		function loadMoreFoodData(){
			j.ajax({
	          url: "${pageContext.request.contextPath}/suggestion/view/ajaxGetAutoFoodList",
	          type: 'POST',
	          async :true,
	          /* dataType: "json", */
	          data: {
	            keySearch:j('#autoFoodKeyword').val(),
	            page:j('#autoFoodPage').val()
	          },
	          success: function(data){
	        	  var newPage= data[0];
	        	  var oldPage=j('#autoFoodPage').val();
	        	  if(newPage==oldPage){//have no more item to load
	        		  j('#autoFoodStop').val(1);  
	        	  }else{
	        		  j('#autoFoodStop').val(0);
	        		  j('#autoFoodPage').val(data[0]); 
	        		 //append old data with new data
	        		 loadMoreFoodFlag=1;
	        		 backupFoodResponse(JSON.parse(data[1]));
	        	  }
	          }
	        });
		}
		
	    j(autoFoodTargetNameControl).autocomplete({
	      minLength: 1,
	      /* source: projects, */
	      source: function(request,response){//user for first request
	    	  if(0==typeFoodName){//if the same value with textbox --> don't suggestion
	    		  j(autoFoodTargetNameControl).removeClass("ui-autocomplete-loading");
	    		 return;
	    	  }
	    	  oldFoodItems='';
	    	  backupFoodResponse = response;
	    	  j('#autoFoodPage').val(1);
	    	  j('#autoFoodStop').val(1);
	    	 j('#autoFoodKeyword').val(request.term);
	    	 j(autoFoodTargetIdControl).val('');
	        j.ajax({
	          url: "${pageContext.request.contextPath}/suggestion/view/ajaxGetAutoFoodList",
	          type: 'POST',
	          async :true,
	          /* dataType: "json", */
	          data: {
	            keySearch: request.term,
	            page:j('#autoFoodPage').val()
	          },
	          success: function(data){
	        	j('#autoFoodPage').val(data[0]);
	        	j('#autoFoodStop').val(0);
	        	j(autoFoodTargetNameControl).removeClass("ui-autocomplete-loading");
	        	loadMoreFoodFlag=0;
	        	response(JSON.parse(data[1]));
	          },
	          error:function(){
	        	  j('#autoFoodStop').val(0);
	          }
	        });
	      },
	      html: true, // optional (jquery.ui.autocomplete.html.js required)
	      search: function (event,ui) {
	    	  
	      },
	      focus: function(event,ui){
	    	  if(autoFoodTargetIdControl){
	    		  j(autoFoodTargetIdControl).val('');	  
	    	  }
	        return false;
	      },
	      select:function(event,ui){
	    	  typeFoodName=0;
	    	  if(j(autoFoodTargetPriceControl).length>0){
	    		  j(autoFoodTargetPriceControl).val(ui.item.value1);
	    	  }
	    	  if(j(autoFoodTargetNameControl).length>0){
	    		  j(autoFoodTargetNameControl).val(ui.item.display);	 
	    	  }
	    	  if(j(autoFoodTargetIdControl).length>0){
	    		  j(autoFoodTargetIdControl).val(ui.item.sn);
	    		  j(foodContainer).hide();
	    		  j(autoFoodTargetIdControl).change();
	    	  }
	    	  
	        return false;
	      },
	      open:function(event, ui){
	    	  if(autoFoodTargetIdControl){
	    		  j(autoFoodTargetIdControl).val('');
	    	  }
	    	  if(j(autoFoodTargetPriceControl).length>0){
	    		  j(autoFoodTargetPriceControl).val('');
	    	  }
	    	  if(j(foodContainer).length>0 && 0==loadMoreFoodFlag){
	    		  j(foodContainer).scrollTop(0);  
	    	  }
	    	  j('#autoFoodStop').val(0);
	       },
        close:function(event, ui){
        	j('#autoFoodPage').val(1);
        	j('#autoFoodStop').val(0);
        }
	    }).autocomplete("instance")._renderItem = function(ul,item){
	      return j( "<li class='autocomplete-content'>" ).data("item.autocomplete-item", item ).append(createFoodContentItem(item)).appendTo(ul);
	    };
	    
	    j(autoFoodTargetNameControl).keyup(function(event){
	    	typeFoodName=1;
	    });
	    
	    j(autoFoodTargetNameControl).autocomplete("instance")._renderMenu =  function(ul,items){
    		var ulId = foodContainer.replace('#','');
    		ul.attr('id',ulId);
    	    var that = this;
    	    if(oldFoodItems){
    	    	j.each(oldFoodItems, function(index, item) {
        	        that._renderItemData( ul, item );
        	    });
    	    } 
    	    j.each( items, function(index, item) {
    	        that._renderItemData( ul, item );
    	    });
    	    if(oldFoodItems.length>0){
    	    	j.merge(oldFoodItems,items);
	   	    }else{
    	    	oldFoodItems = items;
    	    }
    	}
	    
	    function createFoodContentItem(item){
	    	var keyword = j('#autoFoodKeyword').val();
	    	var title = item.title;
	    	var description = item.description;
	    	//var description2 = item.description2;
	    	var content = "<a class='autoFoodItem'><span class='autocomplete-title'>" + title+"</span>";
	    	if(description){
	    		content+="<br><span class='autocomplete-desc'>" + description + "</span>";	
	    	}
	    	//content+="<br><span class='autocomplete-desc'>" + description2 + "</span>";
	    	content+="</a>";
	    	content+="<input type='hidden' id='autoFoodItem_"+item.sn+"' value='"+item.display+"'/>";
	        return content;
	    }
	    
	    j(autoFoodTargetNameControl).blur(function() {
	    	var foodSn;
	    	if(j(autoFoodTargetIdControl).length>0){
	    		foodSn = j(autoFoodTargetIdControl).val();
	    	}
        	if(0==foodSn.length){
        		if(1==foodClear){
	        		if(j(autoFoodTargetIdControl).length>0){
	        			j(autoFoodTargetIdControl).val('');
	        			j(autoFoodTargetIdControl).change();
	        		}
	        		if(j(autoFoodTargetNameControl).length>0){
	        			j(autoFoodTargetNameControl).val('');
	        		}
        		}
        	}
	    });
	    
	     j(foodContainer).scroll(function() {//ul tag
	    	 var container=j(foodContainer);
            if(isFoodScrollbarBottom(container)){
            	if(0==j('#autoFoodStop').val()){
            		j('#autoFoodStop').val(1);
            		loadMoreFoodData();
            	}
            }
	    });
	     
	     
	    
	    function isFoodScrollbarBottom(container) {//object of ul
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

