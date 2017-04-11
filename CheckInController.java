package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.domain.CheckInDetails;
import com.entity.Employee;
import com.services.EmployeeService;


/**
 * @author 584292
 *
 */
@Controller
public class CheckInController {
	
    @Value("${msg}") 
    private String msg;     
    
    @Autowired
    private EmployeeService employeeService;
    
	/**
	 * @return
	 */
	@RequestMapping(value = "/CheckInStatus", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView passengerInfo() {	
		ModelAndView abc = new ModelAndView("CheckInStatus");		
		return abc;		 
	}

	/**
	 * @param model
	 * @param obj
	 * @return
	 */
	@RequestMapping(value = "/addPassenger", method = RequestMethod.POST)
	public String addPassenger(Model model, CheckInDetails obj) {
	    
		model.addAttribute("passengerName", obj.getPassengerName());
		model.addAttribute("passNameRecord", obj.getPassNameRecord());
		model.addAttribute("frequentFlyerEntry", obj.getFrequentFlyerEntry());
		model.addAttribute("msg", this.msg); 
		 
		return "CheckedDetails";
	} 
 
}
