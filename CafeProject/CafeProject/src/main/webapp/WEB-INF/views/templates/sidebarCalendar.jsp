<!--START SIDEBAR CALENDAR-->
<div class="slimScrollDiv">
	 <div id="inputCalendar"></div>
	<input type="hidden" value="${selectDate}" id="pickerDisplayDate">
	<a id="openReservationViewDay" style="display:none;" href="${pageContext.request.contextPath}/reservation/list/therapistTimeline"></a>
</div>
<script type="text/javascript">
	
	var j = jQuery.noConflict();
	j(document).ready(function() {
		var date = getDateForCalendar();
		 j("#inputCalendar").datepicker({
		   numberOfMonths: [3,1],
		   /* showButtonPanel: true, */
		   defaultDate:date,
		   changeMonth: true,
		   changeYear: true,
		   autoSize:true,
		   dateFormat:'yy-m-d',
		   onSelect:chooseDay,
		   onChangeMonthYear :chooseMonthYear
		 });
		
		initStyleForCalendar();
		
		function getDateForCalendar(){
			var date = j('#pickerDisplayDate').val();
			var dateObject;
			var month;
			if(date.length<1){
				dateObject = new Date();
				j('#pickerDisplayDate').val(dateObject.getFullYear() + '-' + dateObject.getMonth() + '-' + dateObject.getDate());
				month = dateObject.getMonth();
			}else{
				var times = date.split('-');
				dateObject= new Date(times[0],times[1],times[2]);
				month = dateObject.getMonth()-1;
			}
			date = dateObject.getFullYear() + '-' + month + '-' + dateObject.getDate();
			return date;
		}
		
		function initStyleForCalendar(){//custom for select today button
			 j("#inputCalendar").datepicker("option",j.datepicker.regional['${languageCode}']);
			 j("#inputCalendar").datepicker( "option", "dateFormat",'yy-m-d');
			 /* j.datepicker._gotoToday = function(id) {
			    var today = new Date();
			    var dateRef = jQuery("<td><a>" + today.getDate() + "</a></td>");
			    this._selectDay(id, today.getMonth(), today.getFullYear(), dateRef);
			}; */
			updateHospitalHolidayList('#inputCalendar');
			updateDateSelected('#inputCalendar');
		}
		
		function updateDateSelected(calendarTagId){
			var removeStyleTag =j(calendarTagId).find('.ui-state-active').eq(0);
			removeStyleTag.removeClass('ui-state-active');
			removeStyleTag.addClass('ui-state-hover');
			removeStyleTag.removeClass('ui-state-hover');
			var dateStr = j('#pickerDisplayDate').val();
			var times = dateStr.split('-');
			if(3==times.length){
				var fullDate = new Date(times[0],times[1],times[2]);
				var month = fullDate.getMonth()-1;
				var date = fullDate.getDate();
				var dateTdList=j(calendarTagId).find("td[data-month='"+month+"']")
				dateTdList.each(function(idx, val){
					if(times[0]==j(this).attr('data-year')){
						var aTag = j(this).find('a').eq(0);
						if(parseInt(aTag.text())==date){//selected date
							aTag.addClass('ui-state-active');
						}
					}
				});
			}
		}
		
		function updateHospitalHolidayList(calendarTagId){
			j.ajax({
    		   url: '${pageContext.request.contextPath}/reservation/view/ajaxGetHospitalHolidayList',
    		   type: 'POST',
    		   data: {},
    		   async: true,
    		   success: function(response){
    			   if(response.length>0){
    				   var dateArray;
    				   var holidayList = JSON.parse(response);
    				   var date='';
    				   var month=-1;
    				   var year='';
    				   var oneDateTag;
    				   j.each(holidayList,function(index,holiday){
    					   dateArray = holiday.fullDate.split('-');
    					   if(3==dateArray.length){
    						   month = parseInt(dateArray[1])-1;
    						   date = dateArray[2];
    						   year = dateArray[0];
    						   var dateTdList=j(calendarTagId).find("td[data-month='"+month+"']")
    							dateTdList.each(function(idx, val){
    								if(year==j(this).attr('data-year')){
    									oneDateTag=j(this).find('a.ui-state-default').eq(0);
    									if(oneDateTag.text()==date){//correct date
    										if(false==oneDateTag.hasClass('ui-datepicker-date-holiday')){
    											oneDateTag.addClass('ui-datepicker-date-holiday');	
    										}
    										oneDateTag.attr('title',holiday.description);
    									}
    								}else{// not correct year
    									return false;
    								}
    							});
    					   }
        				}); 
    			   }
    		   }
	    	});
		}
		
		function updateStyleForATag(){
			var aTagList = j('#inputCalendar').find('a');
			aTagList.each(function(index, element){
				if(j(this).hasClass('ui-state-default')){
					j(this).css('line-height',1.7)
				}
			});
		}
		
		function onSelectUpdateCalendarUI(chooseDateTime,inst){
			//This is the important line.
		    //Setting this to false prevents the redraw.
		    inst.inline = false;
		    //The remainder of the function simply preserves the 
		    //highlighting functionality without completely redrawing.
		    //This removes any existing selection styling.
		    var currentDayTag = j("#inputCalendar").find('tbody tr td a.ui-state-active');
		    currentDayTag.removeClass('ui-state-active');
		    currentDayTag.parent().remove('ui-datepicker-current-day');
		    //This finds the selected link and styles it accordingly.
		    //You can probably change the selectors, depending on your layout.
		    var dateArray = chooseDateTime.split('-');
		    var year=dateArray[0];
		    var month = parseInt(dateArray[1])-1;
		    var date=dateArray[2];
		    
		    var dateTdList=j('#inputCalendar').find("td[data-month='"+month+"']")
			dateTdList.each(function(idx, val){
				if(year==j(this).attr('data-year')){
					oneDateTag=j(this).find('a.ui-state-default').eq(0);
					if(oneDateTag.text()==date){//correct date
						j(this).addClass("ui-datepicker-current-day");
						oneDateTag.addClass('ui-state-active');
					}
				}else{// not correct year
					return false;
				}
			});
		}
		 
		 function chooseDay(chooseDateTime,inst){
			var page=j('#tl_therapist_timeline_page').val();
			var dateStr = chooseDateTime;
			if('therapist_timeline'==page){// is therapist timeline page
				j('#pickerDisplayDate').val(dateStr);
				j('#tl_ajax_get_therapist_calendar').click();
				onSelectUpdateCalendarUI(chooseDateTime,inst);//update calendar UI, do not allow update automatic
			}else{// other pages
				var newHref=j('#openReservationViewDay').attr('href');
				newHref+="?date="+dateStr;
				j('#openReservationViewDay').attr('href',newHref);
				j('#openReservationViewDay')[0].click();
			}
		}
		 
		 function chooseMonthYear(year,month,inst){
			// updateStyleForATag();
			 updateHospitalHolidayList('#inputCalendar');
			 updateDateSelected('#inputCalendar');
		 }
	});
</script>

<!--END SIDEBAR CALENDAR-->