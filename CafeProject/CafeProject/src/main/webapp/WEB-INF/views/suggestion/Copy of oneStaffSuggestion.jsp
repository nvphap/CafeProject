<input id="autoStaffPage" type="hidden" value="1">
<input id="autoStaffStop" type="hidden" value="0">
<input id="autoStaffKeyword" type="hidden" value="">
<script type="text/javascript">
	var j = jQuery.noConflict();
	j(document).ready(function() {
		var autoStaffTargetIdControl;
		var autoStaffTargetNameControl;
		var staffContainer;
		var backupStaffResponse;
		var oldStaffItems;
		var extStaffTargetIdControl;
		var staffRoleSnListIdControl;
		var staffStsListIdControl;
		var staffClear = 1;
		var typeStaffName=0;
		var loadMoreStaffFlag=0;
		initStaffSetup();
		//0:id of tartget id control , 
		//1:Id of target name control
		//2:index of control
		
		function initStaffSetup(){
			var autoStaffSetup = j('#autoStaffSetup');
			if(autoStaffSetup){
				var setting=autoStaffSetup.val();
				var settingList = setting.split('|');
				if(settingList.length>0){
					autoStaffTargetIdControl= '#'+settingList[0];
				}
				if(settingList.length>1){//target name control
					autoStaffTargetNameControl='#'+settingList[1];
				}
				if(settingList.length>2){//target name control
					staffContainer='#ui-id-'+settingList[2];
				}else{
					staffContainer='#ui-id-1'
				}
				if(settingList.length>3){//add more information about room and room type
					extStaffTargetIdControl ='#'+ settingList[3];
				}
				if(settingList.length>4){//information about filter role sn list
					staffRoleSnListIdControl ='#'+ settingList[4];
				}
				if(settingList.length>5){//information about filter status list
					staffStsListIdControl ='#'+ settingList[5];
				}
				if(settingList.length>6){//when user do not choose clear term or not (1:clear, 0: not clear)
					if(settingList[6].length>0){
						staffClear = parseInt(settingList[6]);
					}else{
						staffClear=1;	
					}
				}
				j('#autoStaffPage').val(1);
	        	j('#autoStaffStop').val(0);
	        	if(j(staffContainer).length>0){
	        		j(staffContainer).scrollTop(0);	
	        	}
			}
		}
		
		function loadMoreStaffData(){
			var roleSnList='';
	    	 if(j(staffRoleSnListIdControl).length>0){
	    		 roleSnList = j(staffRoleSnListIdControl).val();
	    	 }
	    	 var statusList='';
	    	 if(j(staffStsListIdControl).length>0){
	    		 statusList = j(staffStsListIdControl).val();
	    	 }
			j.ajax({
	          url: "${pageContext.request.contextPath}/suggestion/view/ajaxGetAutoStaffList",
	          type: 'POST',
	          async :true,
	          /* dataType: "json", */
	          data: {
	            keySearch:j('#autoStaffKeyword').val(),
	            page:j('#autoStaffPage').val(),
	            roleSnList:roleSnList,
	            statusList:statusList
	          },
	          success: function(data){
	        	  var newPage= data[0];
	        	  var oldPage=j('#autoStaffPage').val();
	        	  if(newPage==oldPage){//have no more item to load
	        		  j('#autoStaffStop').val(1);  
	        	  }else{
	        		  j('#autoStaffStop').val(0);
	        		  j('#autoStaffPage').val(data[0]); 
	        		 //append old data with new data
	        		 loadMoreStaffFlag=1;
	        		 backupStaffResponse(JSON.parse(data[1]));
	        	  }
	          }
	        });
		}
		
	    j(autoStaffTargetNameControl).autocomplete({
	      minLength: 1,
	      /* source: projects, */
	      source: function(request,response){//user for first request
	    	  if(0==typeStaffName){//if the same value with textbox --> don't suggestion
	    		  j(autoStaffTargetNameControl).removeClass("ui-autocomplete-loading");
	    		 return;
	    	  }
	    	  oldStaffItems='';
	    	  backupStaffResponse = response;
	    	  j('#autoStaffPage').val(1);
	    	  j('#autoStaffStop').val(1);
	    	 j('#autoStaffKeyword').val(request.term);
	    	 j(autoStaffTargetIdControl).val('');
	    	 var roleSnList='';
	    	 if(j(staffRoleSnListIdControl).length>0){
	    		 roleSnList = j(staffRoleSnListIdControl).val();
	    	 }
	    	 var statusList='';
	    	 if(j(staffStsListIdControl).length>0){
	    		 statusList = j(staffStsListIdControl).val();
	    	 }
	        j.ajax({
	          url: "${pageContext.request.contextPath}/suggestion/view/ajaxGetAutoStaffList",
	          type: 'POST',
	          async :true,
	          /* dataType: "json", */
	          data: {
	            keySearch: request.term,
	            page:j('#autoStaffPage').val(),
	            roleSnList:roleSnList,
	            statusList:statusList
	          },
	          success: function(data){
	        	j('#autoStaffPage').val(data[0]);
	        	j('#autoStaffStop').val(0);
	        	j(autoStaffTargetNameControl).removeClass("ui-autocomplete-loading");
	        	loadMoreStaffFlag=0;
	        	response(JSON.parse(data[1]));
	          },
	          error:function(){
	        	  j('#autoStaffStop').val(0);
	          }
	        });
	      },
	      html: true, // optional (jquery.ui.autocomplete.html.js required)
	      search: function (event,ui) {
	    	  
	      },
	      focus: function(event,ui){
	    	  /* if(autoStaffTargetNameControl){
	    		  j(autoStaffTargetNameControl).val(ui.item.display);	  
	    	  } */
	    	  if(autoStaffTargetIdControl){
	    		  j(autoStaffTargetIdControl).val('');	  
	    	  }
	        return false;
	      },
	      select:function(event,ui){
	    	  typeStaffName=0;
	    	  if(j(extStaffTargetIdControl).length>0){
	    		  //roomSn, room name, type of room sn, type name of room, interval time,roleSn.
	    		  var extValue = ui.item.value1 +';'+ui.item.strValue1+';'+ui.item.value2 +';'+ui.item.strValue2+';'+ui.item.value3+';'+ui.item.value4;
	    		  j(extStaffTargetIdControl).val(extValue);
	    	  }
	    	  if(j(autoStaffTargetNameControl).length>0){
	    		  j(autoStaffTargetNameControl).val(ui.item.display);	 
	    	  }
	    	  if(j(autoStaffTargetIdControl).length>0){
	    		  j(autoStaffTargetIdControl).val(ui.item.sn);
	    		  j(staffContainer).hide();
	    		  j(autoStaffTargetIdControl).change();
	    	  }
	    	  
	        return false;
	      },
	      open:function(event, ui){
	    	  if(autoStaffTargetIdControl){
	    		  /* j(autoStaffTargetIdControl).val(ui.item.sn); */
	    		  j(autoStaffTargetIdControl).val('');
	    	  }
	    	  if(j(staffContainer).length>0 && 0==loadMoreStaffFlag){
	    		  j(staffContainer).scrollTop(0);  
	    	  }
	    	  j('#autoStaffStop').val(0);
	       },
        close:function(event, ui){
        	j('#autoStaffPage').val(1);
        	j('#autoStaffStop').val(0);
        }
	    }).autocomplete("instance")._renderItem = function(ul,item){
	      return j( "<li class='autocomplete-content'>" ).data("item.autocomplete-item", item ).append(createStaffContentItem(item)).appendTo(ul);
	    };
	    
	    j(autoStaffTargetNameControl).keyup(function(event){
	    	typeStaffName=1;
	    });
	    
	    j(autoStaffTargetNameControl).autocomplete("instance")._renderMenu =  function(ul,items){
    		var ulId = staffContainer.replace('#','');
    		ul.attr('id',ulId);
    	    var that = this;
    	    if(oldStaffItems){
    	    	j.each(oldStaffItems, function(index, item) {
        	        that._renderItemData( ul, item );
        	    });
    	    } 
    	    j.each( items, function(index, item) {
    	        that._renderItemData( ul, item );
    	    });
    	    if(oldStaffItems.length>0){
    	    	j.merge(oldStaffItems,items);
	   	    }else{
    	    	oldStaffItems = items;
    	    }
    	}
    	
	    
	    /* String.prototype.replaceAll = function(strReplace, strWith) {
	        var reg = new RegExp(strReplace, 'ig');
	        return this.replace(reg, strWith);
	    }; */
	    
	    function createStaffContentItem(item){
	    	var keyword = j('#autoStaffKeyword').val();
	    	var title = item.title;
	    	var description = item.description;
	    	var description2 = item.description2;
	    	/* if(keyword){
	    		var targetKeyword = "<b class='autocomplete-highlight'>"+keyword+"</b>";
	    		if(title){
	    			//title = title.replace(keyword,targetKeyword);
	    			title=title.replace(new RegExp(keyword, 'g'), targetKeyword);
		    	}
	    		if(description){
	    			//description = description.replace(keyword,targetKeyword);
	    			description=description.replace(new RegExp(keyword, 'g'), targetKeyword);
	    		}
	    		if(description2.length>0){
	    			description2=description2.replace(new RegExp(keyword, 'g'), targetKeyword);
	    		}
	    	} */
	    	var content = "<a class='autoStaffItem'><span class='autocomplete-title'>" + title+"</span>";
	    	content+="<br><span class='autocomplete-desc'>" + description + "</span>";
	    	content+="<br><span class='autocomplete-desc'>" + description2 + "</span>";
	    	content+="</a>";
	    	content+="<input type='hidden' id='autoStaffItem_"+item.sn+"' value='"+item.display+"'/>";
	        return content;
	    }
	    
	    j(autoStaffTargetNameControl).blur(function() {
	    	var staffSn;
	    	if(j(autoStaffTargetIdControl).length>0){
	    		staffSn = j(autoStaffTargetIdControl).val();
	    	}
        	if(0==staffSn.length){
        		if(1==staffClear){
	        		if(j(autoStaffTargetIdControl).length>0){
	        			j(autoStaffTargetIdControl).val('');
	        			j(autoStaffTargetIdControl).change();
	        		}
	        		if(j(autoStaffTargetNameControl).length>0){
	        			j(autoStaffTargetNameControl).val('');
	        		}
        		}
        	}
	    });
	    
	     j(staffContainer).scroll(function() {//ul tag
	    	 var container=j(staffContainer);
            if(isStaffScrollbarBottom(container)){
            	if(0==j('#autoStaffStop').val()){
            		j('#autoStaffStop').val(1);
            		loadMoreStaffData();
            	}
            }
	    });
	     
	     
	    
	    function isStaffScrollbarBottom(container) {//object of ul
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

