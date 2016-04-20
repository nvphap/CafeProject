package com.cafe.utils;


/**
* The login action.
*
* @author  	phap.nguyen
* @version 	1.0
* @since   	2015-05-27 
* @update   
*/
public class AppNumUtils {
	
	public static Long toLong(Object intValue){
		if(null==intValue){
			return null;
		}
		try{
			return Long.valueOf(intValue.toString());
		}catch(Exception ex){
			return null;
		}
		
	}
	
	public static int toIntValue(Object intValue){
		if(null==intValue){
			return 0;
		}
		try{
			return Integer.parseInt(intValue.toString());
		}catch(Exception ex){
			return 0;
		}
	}
	
	public static long toLongValue(Object intValue){
		if(null==intValue){
			return 0;
		}
		try{
			return Long.valueOf(intValue.toString());
		}catch(Exception ex){
			return 0;
		}
		
	}
}
