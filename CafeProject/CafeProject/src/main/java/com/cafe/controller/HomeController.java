/**
\* The LoginController is using for user login.
*
* @author  	DoanTT
* 			DatHQ
* @version 	1.0
* @since   	2015-05-04 
* @update   2015-05-06
*/
package com.cafe.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.webflow.engine.model.Model;

import com.cafe.base.controller.BaseController;


/**
* The login action.
*
* @author  	phap.nguyen
* @version 	1.0
* @since   	2015-06-30 
* @update   
*/
@Controller
public class HomeController extends BaseController {
	/**
	* The welcome action mapping home
	*
	* @author  	phap.nguyen
	* @date		2015-06-30
	* */
	private static Logger LOG;
	
	static {
		LOG = Logger.getLogger(HomeController.class);
	}
	
	@RequestMapping(value = "language", method = RequestMethod.GET)
	public String tranlate(HttpServletRequest request, Model model, HttpSession session, 
			@RequestParam(value = "lang", required = false) final String sLanguage) { 
		LOG.info("language");
		return "redirect:/login?lang=" + sLanguage;
	}	
	
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String errorLogin(HttpSession session) {
		LOG.info("/error");
		return "error";
	}
	
	@RequestMapping(value = "/noPermission", method = RequestMethod.GET)
	public String hasNoPermission(HttpSession session) {
		LOG.info("/noPermission");
		return "noPermission";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(HttpSession session) {
		LOG.info("/home");
		return "redirect: /reservation/list/therapistTimeline";
	}
}
